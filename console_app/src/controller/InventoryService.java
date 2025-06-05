package controller;

import helper.*;
import model.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: Luong Thi Tra My - s3987023
 * @Version: 1.0
 * <p>
 * Controller class for manageing equipment operations
 */
public class InventoryService implements InventoryManager {
    private static final Scanner scanner = new Scanner(System.in);
    private List<Equipment> equipments; //List of equipment
    private Map<String, Equipment> equipmentMap; // Map to store equipment by ID
    private InputValidation inputValidation = new InputValidation();

    //Default constructor
    public InventoryService() {
        this.equipments = EquipmentDataProcessor.getEquipments();
        this.equipmentMap = new HashMap<>();

        // Initialize the equipment map with existing equipment
        for (Equipment equipment : equipments) {
            equipmentMap.put(equipment.getId(), equipment);
        }
    }

    /**
     * Adds a new equipment item to the inventory.
     *
     * @param equipment The equipment object to be added.
     * @return true if the equipment was successfully added, false otherwise.
     */
    @Override
    public boolean addEquipment(Equipment equipment) {
        if (equipment != null) {
            equipments.add(equipment);
            // Save the new equipment to the file
            EquipmentDataWriter.saveEquipment(equipment);

            //update the equipment map
            equipmentMap.put(equipment.getId(), equipment);
            return true;
        }
        return false;
    }

    /**
     * Updates an existing equipment's status or condition.
     * Ensures the user selects only valid updates based on predefined rules.
     *
     * @param equipmentID The ID of the equipment to be updated.
     * @return true if the update is successful, false otherwise.
     */
    @Override
    public boolean updateEquipment(String equipmentID) {
        if (equipmentID == null || equipmentID.isEmpty() || !equipmentMap.containsKey(equipmentID)) {
            System.out.println("Error: Equipment not found.");
            return false;
        }

        Equipment existingEquipment = equipmentMap.get(equipmentID);
        System.out.println("\nUpdating Equipment: " + equipmentID);
        System.out.println("Equipment Name: " + existingEquipment.getName());
        System.out.println("Current Status: " + existingEquipment.getStatus());
        System.out.println("Current Condition: " + existingEquipment.getCondition());

        // Update Status (Only between Available and Unavailable)
        System.out.println("Do you want to update the status? (yes/no): ");
        String currentStatus = existingEquipment.getStatus();  // Store the current status
        String newStatus = currentStatus;  // Initialize newStatus with current status

        if (scanner.nextLine().trim().equalsIgnoreCase("yes")) {
            if (currentStatus.equalsIgnoreCase("Borrowed")) {
                System.out.println("Error: Equipment is currently borrowed and its status cannot be changed.");
            } else {
                // Get new status
                newStatus = inputValidation.getValidEquipmentStatus();
                //Debug
                existingEquipment.setStatus(newStatus);

                // If the status changes from Available to Unavailable or vice versa, force condition update
                if ((currentStatus.equalsIgnoreCase("Available") && newStatus.equalsIgnoreCase("Unavailable")) ||
                        (currentStatus.equalsIgnoreCase("Unavailable") && newStatus.equalsIgnoreCase("Available"))) {
                    System.out.println("Status changed. You must update the condition.");
                    // Force the user to update condition
                    String newCondition = inputValidation.getValidCondition(newStatus);
                    existingEquipment.setCondition(newCondition);
                }
            }
        } else {
            // If status doesn't change to Available/Unavailable, user can choose to update condition
            System.out.print("Do you want to update the condition? (yes/no): ");
            if (scanner.nextLine().trim().equalsIgnoreCase("yes")) {
                String newCondition = inputValidation.getValidCondition(currentStatus);  // Get condition based on current status
                existingEquipment.setCondition(newCondition);
            }
        }

        // Persist changes
        EquipmentDataWriter.updateEquipmentInFile(existingEquipment);

        //Update the existing equipment in equipment map
        equipmentMap.put(equipmentID, existingEquipment);
        return true;
    }

    /**
     * Removes an equipment item from the inventory.
     * Displays available equipment IDs, prompts the user for input, confirms removal,
     * and updates the file with the new equipment list.
     *
     * @return true if the equipment was successfully removed, false otherwise.
     */
    @Override
    public boolean removeEquipment(String equipmentID) {
        if (equipmentID == null || equipmentID.isEmpty()) {
            System.out.println("Error: Invalid equipment ID.");
            return false; // Invalid equipment ID
        }

        Iterator<Equipment> iterator = equipments.iterator();
        while (iterator.hasNext()) {
            Equipment equipment = iterator.next();
            if (equipment.getId().equals(equipmentID)) {
                // Display equipment details before deletion
                System.out.println("\nEquipment Details:");
                System.out.println("ID: " + equipment.getId());
                System.out.println("Name: " + equipment.getName());
                System.out.println("Status: " + equipment.getStatus());
                System.out.println("Condition: " + equipment.getCondition());

                // Prevent removal if equipment is borrowed
                if (equipment.getStatus().equalsIgnoreCase("Borrowed")) {
                    System.out.println("Error: Equipment is currently borrowed and cannot be removed.");
                    return false;
                }

                // Confirm deletion
                System.out.print("\nAre you sure you want to remove this equipment? (yes/no): ");
                String confirmation = scanner.nextLine().trim();
                if (!confirmation.equalsIgnoreCase("yes")) {
                    System.out.println("Operation cancelled. Equipment was not removed.");
                    return false;
                }

                // Remove from list safely using iterator
                iterator.remove();

                // Remove from map
                equipmentMap.remove(equipmentID);

                // Save updated equipment list (overwrite the file)
                EquipmentDataWriter.saveAllEquipment(equipments);

                return true;
            }
        }

        System.out.println("Error: Equipment ID not found.");
        return false; // Equipment not found
    }

    //Define the method to view all equipment
    @Override
    public List<Equipment> getAllEquipment() {
        EquipmentDataLoader.loadEquipment();
        equipments = EquipmentDataProcessor.getEquipments();
        return equipments;
    }

    /**
     * Define method to get available equipment
     * Available equipment are sorted by name and purchase date in ascending order
     *
     * @return List of sorted available equipment
     */
    @Override
    public List<Equipment> getAvailableEquipment() {
        //Update list of equipments before sorting
        EquipmentDataLoader.loadEquipment();
        equipments = EquipmentDataProcessor.getEquipments();
        return equipments.stream()
                .filter(equipment ->
                        equipment.getStatus() != null &&
                                equipment.getStatus().trim().equalsIgnoreCase("Available"))
                .sorted(Comparator.comparing(Equipment::getName, Comparator.nullsLast(String::compareTo))
                        .thenComparing(Equipment::getPurchasedDate, Comparator.nullsLast(Comparator.naturalOrder())))
                .collect(Collectors.toList());
    }


}