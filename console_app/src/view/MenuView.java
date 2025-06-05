package view;

import java.util.Scanner;

/**
 * @Author: Luong Thi Tra My - s3987023
 * @Version: 1.0
 * <p>
 * Console-based menu for admin interactions.
 */

public class MenuView {
    private static final Scanner scanner = new Scanner(System.in);
    private static final PersonView personView = new PersonView();
    private static final LendingServiceView lendingServiceView = new LendingServiceView();
    private static final InventoryServiceView inventoryServiceView = new InventoryServiceView();

    public static void main(String[] args) {
        runMainMenu();
    }

    /**
     * Main menu for the console application.
     * Provides options to view records, manage lending records, manage equipment, and generate reports.
     */
    private static void runMainMenu() {
        while (true) {
            System.out.println("\nWelcome to our University-Owned Equipment System!\n\nPlease refer to the menu below for further actions.");
            System.out.println("1. View Records");
            System.out.println("2. Manage Lending Records");
            System.out.println("3. Manage Equipment");
            System.out.println("4. Generate Reports");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            String input = scanner.nextLine().trim();

            int choice;
            // Validate input to ensure it's a number
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 0 and 4.");
                continue; // Ask the user again
            }

            switch (choice) {
                case 1 -> viewRecordsMenu();
                case 2 -> manageLendingRecordsMenu();
                case 3 -> manageEquipmentMenu();
                case 4 -> generateReportsMenu();
                case 0 -> {
                    System.out.println("Exit the pprogram. Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    /**
     * Submenu for viewing records.
     * Provides options to view all equipment, lending records, staff, and students.
     */
    private static void viewRecordsMenu() {
        while (true) {
            System.out.println("\nView Records");
            System.out.println("Please enter a number corresponding to your expected action!");
            System.out.println("1. View all equipment");
            System.out.println("2. View lending records");
            System.out.println("3. View all staff");
            System.out.println("4. View all students");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");

            String input = scanner.nextLine().trim();
            int choice;
            // Validate input to ensure it's a number
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 0 and 4.");
                continue; // Ask the user again
            }

            switch (choice) {
                case 1 -> inventoryServiceView.viewAllEquipments();
                case 2 -> viewLendingRecordsMenu(); // Calls new lending records submenu
                case 3 -> personView.viewAllStaff();
                case 4 -> personView.viewAllStudents();
                case 0 -> {
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    /**
     * Submenu for viewing lending records.
     * Provides options to view all lending records or filter by borrower or equipment ID.
     */
    private static void viewLendingRecordsMenu() {
        while (true) {
            System.out.println("\nView Lending Records");
            System.out.println("Please enter a number corresponding to your expected action!");
            System.out.println("1. View all lending records");
            System.out.println("2. View lending records by..."); // Submenu
            System.out.println("0. Back to View Records Menu");
            System.out.print("Enter your choice: ");

            String input = scanner.nextLine().trim();
            int choice;
            // Validate input to ensure it's a number
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 0 and 2.");
                continue; // Ask the user again
            }

            switch (choice) {
                case 1 -> lendingServiceView.viewAllLendingRecords(); // Call method to view all records
                case 2 -> viewLendingRecordsByMenu(); // Call submenu to filter records
                case 0 -> {
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    /**
     * Submenu for viewing lending records by borrower or equipment ID.
     * Provides options to filter records based on borrower ID or equipment ID.
     */
    private static void viewLendingRecordsByMenu() {
        while (true) {
            System.out.println("\nView Lending Records By");
            System.out.println("Please enter a number corresponding to your expected action!");
            System.out.println("1. By Borrower ID");
            System.out.println("2. By Equipment ID");
            System.out.println("0. Back to View Lending Records Menu");
            System.out.print("Enter your choice: ");

            String input = scanner.nextLine().trim();
            int choice;
            // Validate input to ensure it's a number
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 0 and 2.");
                continue; // Ask the user again
            }

            switch (choice) {
                case 1 -> lendingServiceView.viewLendingRecordsByBorrower();
                case 2 -> lendingServiceView.viewLendingRecordsByEquipmentId();
                case 0 -> {
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    /**
     * Submenu for managing lending records.
     * Provides options to add, update, or delete lending records.
     */
    private static void manageLendingRecordsMenu() {
        while (true) {
            System.out.println("\nManage Lending Records");
            System.out.println("Please enter a number corresponding to your expected action!");
            System.out.println("1. Add a new lending record");
            System.out.println("2. Update a lending record");
            System.out.println("3. Delete a lending record");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");

            String input = scanner.nextLine().trim();
            int choice;
            // Validate input to ensure it's a number
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 0 and 3.");
                continue; // Ask the user again
            }

            switch (choice) {
                case 1 -> lendingServiceView.addLendingRecord();
                case 2 -> lendingServiceView.updateLendingRecord();
                case 3 -> lendingServiceView.deleteLendingRecord();
                case 0 -> {
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    /**
     * Submenu for managing equipment.
     * Provides options to add, update, or remove equipment.
     */
    private static void manageEquipmentMenu() {
        while (true) {
            System.out.println("\nManage Equipment");
            System.out.println("Please enter a number corresponding to your expected action!");
            System.out.println("1. Add new equipment");
            System.out.println("2. Update equipment details");
            System.out.println("3. Remove equipment");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");

            String input = scanner.nextLine().trim();
            int choice;
            // Validate input to ensure it's a number
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 0 and 3.");
                continue; // Ask the user again
            }

            switch (choice) {
                case 1 -> inventoryServiceView.addEquipment();
                case 2 -> inventoryServiceView.updateEquipment();
                case 3 -> inventoryServiceView.removeEquipment();
                case 0 -> {
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    /**
     * Submenu for generating reports.
     * Provides options to generate overdue records report or available equipment report.
     */
    private static void generateReportsMenu() {
        while (true) {
            System.out.println("\nGenerate Reports");
            System.out.println("Please enter a number corresponding to your expected action!");
            System.out.println("1. Generate overdue records report");
            System.out.println("2. Generate available equipment report");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");
            String input = scanner.nextLine().trim();
            int choice;
            // Validate input to ensure it's a number
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 0 and 2.");
                continue; // Ask the user again
            }

            switch (choice) {
                case 1 -> lendingServiceView.generateOverdueReport();
                case 2 -> inventoryServiceView.generateAvailableEquipmentReport();
                case 0 -> {
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
