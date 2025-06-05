package view;

import helper.EquipmentDataProcessor;
import model.Equipment;
import model.LendingRecord;
import controller.*;
import utils.*;

import java.util.ArrayList;
import java.util.*;
import java.util.Scanner;

/**
 * @author Luong Thi Tra My - s3987023
 * @version 1.0
 * <p>
 * View class for Inventory's operations
 */
public class InventoryServiceView {
    private static final Scanner scanner = new Scanner(System.in);
    private final InventoryService inventoryService = new InventoryService(); // Inventory service instance
    private final InputValidation inputValidation = new InputValidation(); // Input validation instance
    private List<Equipment> equipments = inventoryService.getAllEquipment();

    /**
     * Method to add a new equipment
     */
    public void addEquipment() {
        System.out.println("\nAdding a new equipment...");

        // Get equipment name with validation
        String name;
        while (true) {
            System.out.print("Enter equipment name: ");
            name = scanner.nextLine().trim();

            if (name.isEmpty()) {
                System.out.println("❌ Failed: Equipment name cannot be empty.");
                continue;
            }

            // Check for special characters (only allow letters, digits, spaces, and hyphens)
            if (!name.matches("[a-zA-Z0-9\\s\\-]+")) {
                System.out.println("❌ Failed: Equipment name cannot contain special characters.");
                continue;
            }

            // Check for duplicate name
            boolean duplicate = false;
            for (Equipment eq : equipments) {
                if (eq.getName().equalsIgnoreCase(name)) {
                    duplicate = true;
                    break;
                }
            }
            if (duplicate) {
                System.out.println("❌ Failed: Equipment name already exists in the system.");
            } else {
                break; // Name is valid and unique
            }
        }

        // Assign status automatically
        String status = "Available";

        // Get purchase date
        Date purchaseDate = inputValidation.getValidDate("Enter purchase date (yyyy-MM-dd): ");
        if (purchaseDate == null) {
            System.out.println("Failed: Invalid purchase date.");
            return;
        }

        // Get condition with proper validation
        String condition = null;
        while (condition == null) {
            System.out.println("Select equipment condition:");
            System.out.println("1. Brand New");
            System.out.println("2. Good");
            System.out.print("Enter your choice (1 or 2): ");

            String input = scanner.nextLine().trim();
            switch (input) {
                case "1" -> condition = "Brand New";
                case "2" -> condition = "Good";
                default -> System.out.println("❌ Invalid choice. Please enter 1 or 2.");
            }
        }

        // Generate a unique equipment ID
        String equipmentID = generateNextEquipmentID();

        // Create new Equipment object
        Equipment equipment = new Equipment(equipmentID, name, status, purchaseDate, condition);

        // Add equipment to inventory
        boolean added = inventoryService.addEquipment(equipment);

        if (added) {
            System.out.println("Equipment added successfully.");
        } else {
            System.out.println("Failed to add equipment.");
        }
    }

    /**
     * Method to update an existing equipment
     */
    public void updateEquipment() {
        //Display all equipment ID along with its name
        System.out.println("\nUpdating an equipment...");
        // Get all equipment from the inventory
        if (equipments.isEmpty()) {
            System.out.println("No equipment available to update.");
            return;
        }
        // Display available equipment IDs
        System.out.println("\nAll Equipment in the System:");
        for (Equipment eq : equipments) {
            System.out.println("- " + eq.getId() + ": " + eq.getName());
        }
        System.out.print("\nEnter an equipment ID to update: ");
        String equipmentID = scanner.nextLine().trim();

        boolean updated = inventoryService.updateEquipment(equipmentID);
        if (updated) {
            System.out.println("Equipment updated successfully.");
        } else {
            System.out.println("Failed to update equipment.");
        }
    }

    /**
     * Method to delete equipment
     */
    public void removeEquipment() {
        System.out.println("\nRemoving an equipment...");
        // Get all equipment from the inventory
        if (equipments.isEmpty()) {
            System.out.println("No equipment available to remove.");
        }

        // Display available equipment IDs
        System.out.println("\nAvailable Equipment in the System:");
        for (Equipment eq : equipments) {
            System.out.println("- " + eq.getId() + " (" + eq.getName() + ")");
        }

        // Prompt the user to enter an Equipment ID
        System.out.print("\nEnter the Equipment ID to remove: ");
        String equipmentID = scanner.nextLine().trim();

        boolean deleted = inventoryService.removeEquipment(equipmentID);
        if (deleted) {
            System.out.println("Success: Equipment " + equipmentID + " has been remove from the database.");
        } else {
            System.out.println("Failed: Equipment not found or cannot be deleted.");
        }
    }

    //Method to view all equipments
    public void viewAllEquipments() {
        System.out.println("\nHere is all equipment that exist in our system: ");
        if (equipments.isEmpty()) {
            System.out.println("No equipment found.");
        } else {
            for (Equipment equipment : equipments) {
                equipment.displayInfo();
            }
        }
    }

    //Method to generate next equipment ID
    private String generateNextEquipmentID() {
        equipments = inventoryService.getAllEquipment();
        int maxNumber = 0;

        for (Equipment equipment : equipments) {
            String id = equipment.getId();
            if (id.matches("EQ0\\d{2}")) { // Matches format EQ0XX
                int num = Integer.parseInt(id.substring(2)); // Extract XX
                if (num > maxNumber) {
                    maxNumber = num;
                }
            }
        }

        // Increment by 1
        int nextNumber = maxNumber + 1;
        return String.format("EQ0%02d", nextNumber);
    }

    /**
     * Generate report for all equipment
     */
    public void generateAvailableEquipmentReport() {
        String filePath = "console_app/all_available_equipment_report.txt";

        try {
            List<Equipment> availableEquipments = inventoryService.getAvailableEquipment();

            if (availableEquipments.isEmpty()) {
                System.out.println("No available equipment to generate a report.");
                return;
            }

            ReportGenerator.exportAvailableEquipmentToFile(availableEquipments, filePath);
            System.out.println("Available Equipment Report successfully generated and saved to: " + filePath);
        } catch (Exception e) {
            System.err.println("Error generating available equipment report: " + e.getMessage());
        }
    }

}
