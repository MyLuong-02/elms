package view;

import helper.*;
import controller.*;
import model.*;
import utils.*;

import java.util.*;

/* * View class for lending service operations. */
public class LendingServiceView {
    private static final Scanner scanner = new Scanner(System.in);
    private final LendingService lendingService = new LendingService(); // Lending service instance
    private final InputValidation inputValidation = new InputValidation(); // Input validation instance
    private List<LendingRecord> lendingRecords = LendingRecordProcessor.getLendingRecords();

    //Get list of student, academic, professional and equipment
    private List<Student> students = StudentDataProcessor.getStudents();
    private List<Academic> academics = StaffDataProcessor.getAcademics();
    private List<Professional> professionals = StaffDataProcessor.getProfessionals();
    private List<Equipment> equipmentList = EquipmentDataProcessor.getEquipments();

    //Method to add lending record
    public void addLendingRecord() {
        System.out.println("\nAdding a new lending record...");

        // Get borrower
        Borrower borrower = inputValidation.getValidBorrower();
        if (borrower == null) {
            System.out.println("Failed: Borrower not found.");
            return;
        }

        // Get equipment list
        List<Equipment> equipmentList = inputValidation.getValidEquipment();

        // Get supervisor only if the borrower is a student
        Academic supervisor = null;
        if (borrower instanceof Student) {
            supervisor = inputValidation.getValidSupervisor();
            if (supervisor == null) {
                System.out.println("Failed: Supervisor is required for students.");
                return;
            }

            //Assign supervisor to the student
            ((Student) borrower).setSupervisor(supervisor);
        }

        // Get borrow date
        Date borrowDate = inputValidation.getValidDate("Enter borrow date (yyyy-MM-dd): ");
        Date today = new Date();

        // Get return date with validation loop
        Date returnDate;
        do {
            returnDate = inputValidation.getValidDate("Enter return date (yyyy-MM-dd): ");
            if (returnDate.before(borrowDate)) {
                System.out.println("Return date must be after borrow date. Please try again.");
            } else if (returnDate.before(today)) {
                System.out.println("Return date must be after today's date. Please try again.");
            }
        } while (returnDate.before(borrowDate) || returnDate.before(today));

        // Get purpose
        String purpose = inputValidation.getValidPurpose();

        // Generate a sequential record ID
        String recordID = generateNextRecordID();

        // Create lending record
        LendingRecord record = new LendingRecord(recordID, borrower, equipmentList, supervisor, borrowDate, returnDate, "Borrowed", purpose);

        // Add lending record
        boolean added = lendingService.addLendingRecord(record);

        if (added) {
            System.out.println("Lending record added successfully.");
        } else {
            System.out.println("Failed to add lending record.");
        }
    }

    /**
     * Updates an existing lending record.
     */
    public void updateLendingRecord() {
        System.out.println("\nUpdating a lending record...");
        if (lendingRecords.isEmpty()) {
            System.out.println("No lending records found.");
            return;
        }
        System.out.println("Look at the list below to find the record ID you want to update:");
        for (LendingRecord record : lendingRecords) {
            System.out.println("Record ID: " + record.getRecordID() + ", Borrower: " + record.getBorrower().getFullName());
        }

        System.out.print("\nEnter lending record ID to update : ");
        String recordID = scanner.nextLine().trim();

        boolean updated = lendingService.updateLendingRecord(recordID);
        if (updated) {
            System.out.println("Lending record updated successfully.");
        } else {
            System.out.println("Failed to update lending record.");
        }
    }

    /**
     * Deletion of a lending record by ID.
     */
    public void deleteLendingRecord() {
        System.out.println("\nDeleting a lending record...");
        // Display all lending records
        lendingRecords = lendingService.getAllLendingRecords();
        if (lendingRecords.isEmpty()) {
            System.out.println("No lending records found.");
            return;
        }
        System.out.println("Look at the list below to find the record ID you want to delete:");
        for (LendingRecord record : lendingRecords) {
            System.out.println("ID: " + record.getRecordID() + ", Borrower: " + record.getBorrower().getFullName());
        }

        System.out.print("\nEnter the record ID to delete: ");
        String recordID = scanner.nextLine().trim();

        // Call the method to delete the lending record
        boolean deleted = lendingService.deleteLendingRecord(recordID);
        if (deleted) {
            System.out.println("Success: Lending record " + recordID + " has been deleted.");
        } else {
            System.out.println("Error: Lending record " + recordID + " could not be deleted.");
        }
    }

    //Method to view all lending records
    public void viewAllLendingRecords() {
        // Display lending records
        System.out.println("Here is all lending records: ");
        if (lendingRecords.isEmpty()) {
            System.out.println("No lending record found.");
        } else {
            for (LendingRecord lendingRecord : lendingRecords) {
                lendingRecord.displayRecord();
            }
        }
    }

    //Method to view lending records by borrower
    public void viewLendingRecordsByBorrower() {
        //Display all borrower ID along with their name. BorrowerID is taken from lending records
        System.out.println("\nRefer to the list below for entering borrower:");
        lendingRecords = lendingService.getAllLendingRecords();
        for (LendingRecord record : lendingRecords) {
            System.out.println("Borrower ID: " + record.getBorrower().getBorrowerID() + ", Name: " + record.getBorrower().getFullName());
        }

        System.out.print("Enter borrower ID: ");
        String borrowerID = scanner.nextLine().trim();

        // Call the method to view lending records by borrower
        lendingRecords = lendingService.getLendingRecordsByBorrower(borrowerID);

        if (lendingRecords.isEmpty()) {
            System.out.println("No lending record found.");
        } else {
            // Display lending records
            System.out.println("Here is all lending records for borrower " + borrowerID + ": ");
            for (LendingRecord lendingRecord : lendingRecords) {
                lendingRecord.displayRecord();
            }
        }
    }

    //Method to view lending records by equipment ID
    public void viewLendingRecordsByEquipmentId() {
// Create a Set to store unique equipment IDs
        Set<String> equipmentIDs = new HashSet<>();

// Loop through all lending records and collect unique equipment IDs
        for (LendingRecord record : lendingRecords) {
            for (Equipment equipment : record.getEquipmentList()) {
                // Add each equipment ID to the Set (duplicates will be ignored)
                equipmentIDs.add(equipment.getId());
            }
        }

        System.out.println("\nRefer to the list below for entering equipment ID you want to view its lending records:");
// Display each unique equipment ID along with its name. Each ID is printed once
        for (String equipmentID : equipmentIDs) {
            for (Equipment equipment : equipmentList) {
                if (equipment.getId().equals(equipmentID)) {
                    System.out.println("ID: " + equipment.getId() + ": " + equipment.getName());
                }
            }
        }
        // Get equipment ID from user
        System.out.print("\nEnter equipment ID to search: ");
        String equipmentID = scanner.nextLine().trim();

        // Call the method to view lending records by equipment ID
        lendingRecords = lendingService.getLendingRecordsByEquipment(equipmentID);

        if (lendingRecords.isEmpty()) {
            System.out.println("No lending records found for equipment ID: " + equipmentID);
        } else {
            System.out.println("\nLending Records for Equipment ID: " + equipmentID + ": ");
            for (LendingRecord record : lendingRecords) {
                record.displayRecord();
            }
        }
    }

    /**
     * Method to generate overdue report and export it to a text file
     */
    public void generateOverdueReport() {
        String filePath = "console_app/overdue_lending_report.txt"; // File path for saving the report

        try {
            List<LendingRecord> overdueRecords = lendingService.getOverdueLendingRecords();
            if (overdueRecords.isEmpty()) {
                System.out.println("No overdue lending records to generate a report.");
                return;
            }

            ReportGenerator.exportLendingRecordsToFile(overdueRecords, filePath);
            System.out.println("Report for all overdue lending records successfully generated and saved to: " + filePath);
        } catch (Exception e) {
            System.err.println("Error generating overdue lending record  report: " + e.getMessage());
        }
    }

    private String generateNextRecordID() {
        lendingRecords = lendingService.getAllLendingRecords();
        int maxNumber = 0;

        for (LendingRecord record : lendingRecords) {
            String id = record.getRecordID();
            if (id.matches("L0\\d{2}")) { // Matches format L0XX
                int num = Integer.parseInt(id.substring(2)); // Extract XX
                if (num > maxNumber) {
                    maxNumber = num;
                }
            }
        }

        // Increment by 1
        int nextNumber = maxNumber + 1;
        return String.format("L0%02d", nextNumber);
    }


}
