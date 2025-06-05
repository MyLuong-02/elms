package controller;

import helper.*;
import model.*;
import utils.DateUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Luong Thi Tra My - s3987023
 * @version 1.0
 * <p>
 * LendingService class that implements the LendingManager interface.
 * Handles lending transactions including lending record addition, modification, removal and retrieval.
 */
public class LendingService implements LendingManager {
    private static final Scanner scanner = new Scanner(System.in);
    private List<LendingRecord> lendingRecords; // Stores all lending records
    private Map<String, LendingRecord> lendingRecordsByID; // Fast lookup by record ID
    private Map<String, List<LendingRecord>> lendingRecordsByBorrower; // Fast lookup by borrowerID
    private Map<String, List<LendingRecord>> lendingRecordsByEquipment; // Fast lookup by equipmentID
    private InputValidation inputValidation = new InputValidation();

    /**
     * Default constructor for LendingService.
     */
    public LendingService() {
        // Load lending record data
        LendingRecordDataLoader.loadLendingRecords();

        // Retrieve lists of lending records
        this.lendingRecords = LendingRecordProcessor.getLendingRecords();

        // Initialize HashMaps
        this.lendingRecordsByID = new HashMap<>();
        this.lendingRecordsByBorrower = new HashMap<>();
        this.lendingRecordsByEquipment = new HashMap<>();

        // Populate HashMap from loaded lending records
        for (LendingRecord record : lendingRecords) {
            lendingRecordsByID.put(record.getRecordID(), record);

            lendingRecordsByBorrower
                    .computeIfAbsent(record.getBorrower().getBorrowerID(), k -> new ArrayList<>())
                    .add(record);

            for (Equipment eq : record.getEquipmentList()) {
                lendingRecordsByEquipment
                        .computeIfAbsent(eq.getId(), k -> new ArrayList<>())
                        .add(record);
            }
        }
    }

    /**
     * Adds a lending record to the system.
     * Ensures students have academic supervision before borrowing.
     * saves the record and updates the equipment status addedif  successfully.
     *
     * @param record The lending record to be added.
     * @return true if the record was successfully added, false otherwise.
     */
    @Override
    public boolean addLendingRecord(LendingRecord record) {
        if (record == null || record.getBorrower() == null || record.getEquipmentList() == null || record.getEquipmentList().isEmpty()) {
            return false; // Invalid lending record
        }

        // Check if borrower is a student and has academic supervision
        if (record.getBorrower() instanceof Student) {
            Student student = (Student) record.getBorrower();
            if (student.getSupervisor() == null) {
                System.out.println("Student must have academic supervision to borrow equipment.");
                return false;
            }
        }

        // Update equipment status to "Borrowed"
        for (Equipment eq : record.getEquipmentList()) {
            if (!eq.getStatus().equalsIgnoreCase("Available")) {
                System.out.println("Equipment " + eq.getId() + " is not available for lending.");
                return false;
            }
            eq.setStatus("Borrowed");
        }

        // Add lending record to list
        lendingRecords.add(record);

        // Update 3 HashMaps
        lendingRecordsByID.put(record.getRecordID(), record);
        lendingRecordsByBorrower
                .computeIfAbsent(record.getBorrower().getBorrowerID(), k -> new ArrayList<>())
                .add(record);

        for (Equipment eq : record.getEquipmentList()) {
            lendingRecordsByEquipment
                    .computeIfAbsent(eq.getId(), k -> new ArrayList<>())
                    .add(record);
        }

        //Save lending record to the existing file in the database
        LendingRecordDataWriter.saveLendingRecord(record);

        //Update equipment status in the equipment file accordingly
        for (Equipment eq : record.getEquipmentList()) {
            EquipmentDataWriter.updateEquipmentStatus(eq.getId(), eq.getStatus());
        }
        return true;
    }

    /**
     * Updates an existing lending record in the system.
     * Allows modifications to the borrower, borrowing date, return date, equipment list, and status.
     *
     * @param recordID      The ID of the lending record to be updated.
     * @param updatedRecord The updated lending record object.
     * @return true if the update is successful, false otherwise.
     */
    @Override
    public boolean updateLendingRecord(String recordID) {
        if (recordID == null || recordID.isEmpty() || !lendingRecordsByID.containsKey(recordID)) {
            System.out.println("Error: Lending record not found.");
            return false;
        }

        LendingRecord existingRecord = lendingRecordsByID.get(recordID);
        System.out.println("\nUpdating lending record: " + recordID);
        System.out.println("Current information: ");
        System.out.println(existingRecord);

        // Get updates via view
        Borrower newBorrower = getUpdatedBorrower(existingRecord.getBorrower());
        Date newBorrowDate = getUpdatedBorrowDate(existingRecord.getBorrowDate());
        Date newReturnDate = getUpdatedReturnDate(existingRecord.getReturnDate(), newBorrowDate);
        List<Equipment> newEquipmentList = getUpdatedEquipmentList(existingRecord.getEquipmentList());
        String newStatus = getUpdatedStatus(existingRecord.getStatus());

        // If the borrower has changed, check if supervisor needs to be updated
        Academic newSupervisor = null;

        // Check if the borrower change is from Academic/Professional to Student
        if (!(existingRecord.getBorrower() instanceof Student) && newBorrower instanceof Student) {
            System.out.println("Since the borrower is now a Student, please enter the supervisor.");
            newSupervisor = getUpdatedSupervisor(existingRecord.getSupervisor());
        }
        // If the borrower is being changed between students, give the option to update the supervisor
        else if (existingRecord.getBorrower() instanceof Student && newBorrower instanceof Student) {
            System.out.print("Do you want to update the supervisor? (yes/no): ");
            if (scanner.nextLine().trim().equalsIgnoreCase("yes")) {
                newSupervisor = getUpdatedSupervisor(existingRecord.getSupervisor());
            } else {
                newSupervisor = existingRecord.getSupervisor(); // Keep the existing supervisor if not updated
            }
        }

        // Apply updates
        existingRecord.setBorrower(newBorrower);
        existingRecord.setBorrowDate(newBorrowDate);
        existingRecord.setReturnDate(newReturnDate);
        existingRecord.setEquipmentList(newEquipmentList);
        existingRecord.setStatus(newStatus);

        // Update supervisor if borrower is a Student
        if (newBorrower instanceof Student) {
            existingRecord.setSupervisor(newSupervisor);
        }

        // Mark new equipment as "Borrowed" if status is not "Returned"
        if (newStatus.equalsIgnoreCase("Returned")) {
            for (Equipment eq : newEquipmentList) {
                eq.setStatus("Available");
                EquipmentDataWriter.updateEquipmentStatus(eq.getId(), "Available");
            }
        } else {
            for (Equipment eq : newEquipmentList) {
                eq.setStatus("Borrowed");
                EquipmentDataWriter.updateEquipmentStatus(eq.getId(), "Borrowed");
            }
        }

        // Persist changes
        LendingRecordDataWriter.updateLendingRecord(existingRecord);

        //update borrower map
        lendingRecordsByBorrower.getOrDefault(newBorrower.getBorrowerID(), new ArrayList<>()).add(existingRecord);

        //Update lending records by equipment ID map
        for (Equipment eq : newEquipmentList) {
            lendingRecordsByEquipment
                    .computeIfAbsent(eq.getId(), k -> new ArrayList<>())
                    .add(existingRecord);
        }
        return true;
    }

    /* *
     * Delete a lending record by record ID
     * and reupdate the file
     * @param recordID The ID of the lending record to be deleted
     */
    @Override
    public boolean deleteLendingRecord(String recordID) {
        if (recordID == null || recordID.isEmpty()) {
            return false; // Invalid record ID
        }
        //Display current information of the lending record with that ID
        LendingRecord recordToDelete = lendingRecordsByID.get(recordID);
        if (recordToDelete == null) {
            System.out.println("Record not found.");
            return false; // Record not found
        }

        System.out.println("Current information of the lending record with ID " + recordID + ":");
        System.out.println(recordToDelete);

        // Ask for user confirmation before deleting
        System.out.print("Are you sure you want to delete this record? (yes/no): ");
        String confirmation = scanner.nextLine().trim().toLowerCase();

        if (!confirmation.equals("yes")) {
            System.out.println("Deletion cancelled.");
            return false; // User chose not to delete
        }

        // Proceed with deletion if confirmed
        Iterator<LendingRecord> iterator = lendingRecords.iterator();
        while (iterator.hasNext()) {
            LendingRecord record = iterator.next();
            if (record.getRecordID().equals(recordID)) {
                // Restore equipment status to "Available"
                for (Equipment eq : record.getEquipmentList()) {
                    eq.setStatus("Available");
                    EquipmentDataWriter.updateEquipmentStatus(eq.getId(), "Available");
                }

                // Remove from main list
                iterator.remove();

                // Remove from borrower map
                lendingRecordsByBorrower.getOrDefault(record.getBorrower().getBorrowerID(), new ArrayList<>()).remove(record);

                // Remove from equipment map
                for (Equipment eq : record.getEquipmentList()) {
                    lendingRecordsByEquipment.getOrDefault(eq.getId(), new ArrayList<>()).remove(record);
                }

                //Remove from lending record by ID
                lendingRecordsByID.remove(recordID);

                // Save updated records (overwrite the file)
                LendingRecordDataWriter.saveAllLendingRecords(lendingRecords);
                return true;
            }
        }
        return false; // Record not found
    }

    //Define the method to view all lending records
    @Override
    public List<LendingRecord> getAllLendingRecords() {
        return lendingRecords;
    }

    /**
     * Get lending records by borrower ID.
     * Now uses HashMap for faster lookup.
     *
     * @param borrowerID The ID of the borrower.
     * @return A list of lending records for the specified borrower.
     */
    @Override
    public List<LendingRecord> getLendingRecordsByBorrower(String borrowerID) {
        return lendingRecordsByBorrower.getOrDefault(borrowerID, new ArrayList<>());
    }

    /**
     * Get lending records by equipment ID.
     * Now uses HashMap for faster lookup.
     *
     * @param equipmentID The ID of the equipment.
     * @return A list of lending records for the specified equipment.
     */
    @Override
    public List<LendingRecord> getLendingRecordsByEquipment(String equipmentID) {
        return lendingRecordsByEquipment.getOrDefault(equipmentID, new ArrayList<>());
    }

    /**
     * Retrieves sorted overdue lending records.
     *
     * @return List of overdue lending records.
     */
    @Override
    public List<LendingRecord> getOverdueLendingRecords() {
        //Update all lending records before sorting
        LendingRecordDataLoader.loadLendingRecords();
        lendingRecords = LendingRecordProcessor.getLendingRecords();
        // Filter the lending records to get only those with "Overdue" status
        return lendingRecords.stream()
                .filter(record -> "Overdue".equals(record.getStatus())) // Filter by status "Overdue"
                .sorted(Comparator.comparing(LendingRecord::getBorrowDate)) // Always sort by borrowed date
                .collect(Collectors.toList()); // Collect the sorted list
    }

    private Borrower getUpdatedBorrower(Borrower currentBorrower) {
        System.out.println("Current borrower: " + currentBorrower.getFullName());
        System.out.print("Do you want to change the borrower? (yes/no): ");
        return scanner.nextLine().trim().equalsIgnoreCase("yes") ? inputValidation.getValidBorrower() : currentBorrower;
    }

    private Date getUpdatedBorrowDate(Date currentBorrowDate) {
        System.out.print("Current borrowing date: " + DateUtils.formatDate(currentBorrowDate) + "\n");
        System.out.print("Do you want to change the borrowing date? (yes/no): ");
        return scanner.nextLine().trim().equalsIgnoreCase("yes") ? inputValidation.getValidDate("Enter new borrowing date: ") : currentBorrowDate;
    }

    private Date getUpdatedReturnDate(Date currentReturnDate, Date borrowDate) {
        System.out.print("Current return date: " + DateUtils.formatDate(currentReturnDate) + "\n");
        Date newReturnDate;
        do {
            System.out.print("Do you want to change the return date? (yes/no): ");
            if (!scanner.nextLine().trim().equalsIgnoreCase("yes")) {
                return currentReturnDate; // Keep the existing return date if no change
            }

            newReturnDate = inputValidation.getValidDate("Enter new return date: ");

            if (newReturnDate.before(borrowDate)) {
                System.out.println("Error: Return date must be after the borrowing date. Please enter a valid return date.");
            }

        } while (newReturnDate.before(borrowDate));

        return newReturnDate;
    }

    private List<Equipment> getUpdatedEquipmentList(List<Equipment> currentEquipmentList) {
        System.out.println("Current equipment list: " + currentEquipmentList.stream().map(Equipment::getId).collect(Collectors.joining(", ")));
        System.out.print("Do you want to change the equipment list? (yes/no): ");
        return scanner.nextLine().trim().equalsIgnoreCase("yes") ? inputValidation.getValidEquipment() : currentEquipmentList;
    }

    private String getUpdatedStatus(String currentStatus) {
        System.out.println("Current status: " + currentStatus);
        // Prompt user for status change
        System.out.println("Do you want to change the status? (yes/no): ");
        return scanner.nextLine().trim().equalsIgnoreCase("yes") ? inputValidation.getValidStatus() : currentStatus;
    }

    //Get updated supervisor
    private Academic getUpdatedSupervisor(Academic currentSupervisor) {
        System.out.println("Current supervisor: " + (currentSupervisor != null ? currentSupervisor.getFullName() : "None"));
        return inputValidation.getValidSupervisor(); // Get new supervisor
    }


}