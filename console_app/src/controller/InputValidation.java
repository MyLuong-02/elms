package controller;

import model.*;
import helper.*;
import utils.DateUtils;

import java.util.*;
import java.text.ParseException;

public class InputValidation {
    private static final Scanner scanner = new Scanner(System.in);
    private List<Student> students;
    private List<Academic> academics;
    private List<Professional> professionals;
    private List<Equipment> equipmentList;

    // Default constructor to load required data
    public InputValidation() {
        StudentDataLoader.loadStudents();
        StaffDataLoader.loadStaff();
        EquipmentDataLoader.loadEquipment();

        this.students = StudentDataProcessor.getStudents();
        this.academics = StaffDataProcessor.getAcademics();
        this.professionals = StaffDataProcessor.getProfessionals();
        this.equipmentList = EquipmentDataProcessor.getEquipments();
    }

    public Borrower getValidBorrower() {
        if (students.isEmpty() && academics.isEmpty() && professionals.isEmpty()) {
            System.out.println("Error: No borrowers available.");
            return null;
        }
        //Display all people along with their ID and name
        System.out.println("\nRefer to the list below for entering borrower:");
        students.forEach(s -> System.out.println(s.getBorrowerID() + ": " + s.getFullName()));
        academics.forEach(a -> System.out.println(a.getBorrowerID() + ": " + a.getFullName()));
        professionals.forEach(p -> System.out.println(p.getBorrowerID() + ": " + p.getFullName()));
        while (true) {
            System.out.print("Enter borrower ID: ");
            String borrowerID = scanner.nextLine().trim();

            for (Student s : students) {
                if (s.getBorrowerID().equals(borrowerID)) {
                    System.out.println("Borrower found: " + s.getFullName() + " (Student)");
                    return s;
                }
            }
            for (Academic a : academics) {
                if (a.getBorrowerID().equals(borrowerID)) {
                    System.out.println("Borrower found: " + a.getFullName() + " (Academic)");
                    return a;
                }
            }
            for (Professional p : professionals) {
                if (p.getBorrowerID().equals(borrowerID)) {
                    System.out.println("Borrower found: " + p.getFullName() + " (Professional)");
                    return p;
                }
            }

            System.out.println("Error: Borrower ID not found. Try again.");
        }
    }

    public List<Equipment> getValidEquipment() {
        if (equipmentList.isEmpty()) {
            System.out.println("Error: No equipment available.");
            return Collections.emptyList();
        }
        System.out.println("\nList of equipment in our system:");
        equipmentList.forEach(eq -> System.out.println(eq.getId() + ": " + eq.getName()));

        // Create a map for faster lookup
        Map<String, Equipment> availableEquipmentMap = new HashMap<>();
        for (Equipment eq : equipmentList) {
            if (eq.getStatus().trim().equalsIgnoreCase("Available")) {
                availableEquipmentMap.put(eq.getId(), eq);
            }
        }

        if (availableEquipmentMap.isEmpty()) {
            System.out.println("Error: No available equipment at the moment.");
            return Collections.emptyList();
        }

        while (true) {
            System.out.print("Enter equipment IDs (separated by semicolons): ");
            String input = scanner.nextLine().trim();

            // Check if the input contains a comma, if so, reject it
            if (input.contains(",")) {
                System.out.println("Error: Comma (',') is not allowed. Please separate IDs with semicolons only.");
                continue;  // Skip the current iteration and prompt again
            }

            String[] equipmentIDs = input.split(";");
            Set<Equipment> selectedEquipment = new HashSet<>();

            for (String id : equipmentIDs) {
                id = id.trim();  // Trim spaces around IDs
                if (availableEquipmentMap.containsKey(id)) {
                    selectedEquipment.add(availableEquipmentMap.get(id));
                }
            }

            if (!selectedEquipment.isEmpty()) {
                return new ArrayList<>(selectedEquipment);
            }

            System.out.println("Error: The equipment is invalid. Please try again!");
        }
    }

    public Academic getValidSupervisor() {
        if (academics.isEmpty()) {
            System.out.println("Error: No academic supervisors available.");
            return null;
        }
        System.out.println("\nRefer to the List of Available Supervisors below:");
        academics.forEach(a -> System.out.println(a.getStaffID() + ": " + a.getFullName()));
        while (true) {
            System.out.print("Enter supervisor ID: ");
            String supervisorID = scanner.nextLine().trim();

            for (Academic a : academics) {
                if (a.getStaffID().equals(supervisorID)) {
                    return a;
                }
            }

            System.out.println("Error: Supervisor ID not found. Try again.");
        }
    }

    public Date getValidDate(String prompt) {
        while (true) {
            System.out.print(prompt);
            String dateStr = scanner.nextLine().trim();
            Date parsedDate = DateUtils.parseDate(dateStr); // Will return null if invalid

            if (parsedDate != null) {
                return parsedDate;
            }

            System.out.println("Invalid date format. Please enter in yyyy-MM-dd.");
        }
    }

    public String getValidStatus() {
        List<String> validStatuses = Arrays.asList("Borrowed", "Returned", "Overdue");
        System.out.println("\nSelect a new status:");
        for (int i = 0; i < validStatuses.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, validStatuses.get(i));
        }
        System.out.print("Enter your choice (1-3): ");

        String input = scanner.nextLine().trim();

        // Check if input is a digit and within range
        if (input.matches("[1-3]")) {
            int index = Integer.parseInt(input) - 1;
            return validStatuses.get(index);
        } else {
            System.out.println("❌ Invalid input. Please enter a number from 1 to 3.");
            return getValidStatus(); // Retry
        }
    }

    /**
     * Prompts the user to enter a purpose and ensures it doesn't contain a comma.
     *
     * @return A validated purpose string without commas
     */
    public String getValidPurpose() {
        while (true) {
            System.out.print("Enter purpose (commas are not allowed): ");
            String purpose = scanner.nextLine().trim();

            if (purpose.isEmpty()) {
                System.out.println("❌ Purpose cannot be empty. Please try again.");
            } else if (purpose.contains(",")) {
                System.out.println("❌ Purpose cannot contain commas. Please remove any commas and try again.");
            } else {
                return purpose;
            }
        }
    }

/**
 * Gets a valid equipment status from the user.
 * Ensures status can only switch between Available/Unavailable.
 */
    /**
     * Prompts the user to select a valid equipment status.
     * The user can choose by entering 1, 2, or 0 for 'Available', 'Unavailable', or cancel respectively.
     */
    public String getValidEquipmentStatus() {
        while (true) {
            System.out.println("Select equipment status:");
            System.out.println("1. Available");
            System.out.println("2. Unavailable");
            System.out.println("0. Cancel");

            System.out.print("Enter your choice (1-2, 0 to cancel): ");
            String input = scanner.nextLine().trim();
            int choice;
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid input. Please enter 1, 2, or 0.");
                continue; // Retry
            }

            switch (choice) {
                case 1:
                    return "Available";
                case 2:
                    return "Unavailable";
                case 0:
                    System.out.println("Action canceled.");
                    return "";  // Return empty string or handle as needed for cancellation
                default:
                    System.out.println("Invalid input. Please enter 1, 2, or 0.");
            }
        }
    }

    /**
     * Gets a valid equipment condition from the user based on the current status.
     */
    public String getValidCondition(String currentStatus) {
        List<String> allowedConditions = currentStatus.equalsIgnoreCase("Available")
                ? Arrays.asList("Brand New", "Good")
                : Arrays.asList("Damaged", "Needs Maintenance", "Out of Service");
        while (true) {
            System.out.println("\nSelect the new condition:");
            for (int i = 0; i < allowedConditions.size(); i++) {
                System.out.printf("%d. %s%n", i + 1, allowedConditions.get(i));
            }
            System.out.print("Enter your choice (1-" + allowedConditions.size() + "): ");
            String input = scanner.nextLine().trim();
            int choice;
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid input. Please enter a number between 1 and " + allowedConditions.size() + ".");
                continue; // Retry
            }
            // Check if choice is within the valid range
            if (choice >= 1 && choice <= allowedConditions.size()) {
                return allowedConditions.get(choice - 1);
            } else {
                System.out.println("❌ Invalid input. Please enter a number between 1 and " + allowedConditions.size() + ".");
            }
        }
    }
}
