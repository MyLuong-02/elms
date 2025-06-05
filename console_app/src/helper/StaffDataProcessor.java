package helper;

import model.Academic;
import model.Professional;
import utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author Luong Thi Tra My - s3987023
 * @Version 1.0
 * <p>
 * Processes staff data and stores Academic and Professional staff in separate lists.
 */
public class StaffDataProcessor {
    private static final List<Academic> academics = new ArrayList<>();
    private static final List<Professional> professionals = new ArrayList<>();

    /**
     * Processes a single line of staff data and adds it to the corresponding list.
     *
     * @param line A comma-separated string containing staff details.
     */
    public static void processStaffRecord(String line) {
        line = line.trim(); // Remove leading/trailing spaces

        // Skip blank lines
        if (line.isEmpty()) {
            return;
        }

        String[] data = line.split(",");

        // Ensure correct number of fields before processing
        if (data.length != 7) {
            System.err.println("Invalid data format: " + line);
            return;
        }

        // Extract and trim values
        String staffID = data[0].trim();
        String id = data[1].trim();
        String fullName = data[2].trim();
        String contactInfo = data[4].trim();
        String type = data[5].trim().toLowerCase();
        String extraInfo = data[6].trim(); // Expertise (Academic) or Department (Professional)

        try {
            // Parse birth date safely
            Date birthDate = DateUtils.parseDate(data[3].trim());

            // If parsing succeeds, add staff member
            addStaffMember(staffID, id, fullName, birthDate, contactInfo, type, extraInfo);
        } catch (Exception e) {
            System.err.println("Error parsing date in record: " + line);
            e.printStackTrace(); // Print error details for debugging
        }
    }

    /**
     * Adds a staff member to the appropriate list based on their type.
     *
     * @param staffID     Staff ID.
     * @param id          Person ID.
     * @param fullName    Full name.
     * @param birthDate   Birth date.
     * @param contactInfo Contact details.
     * @param type        Staff type (academic/professional).
     * @param extraInfo   Expertise (Academic) or Department (Professional).
     */
    private static void addStaffMember(String staffID, String id, String fullName, Date birthDate,
                                       String contactInfo, String type, String extraInfo) {
        switch (type) {
            case "academic":
                academics.add(new Academic(staffID, id, fullName, birthDate, contactInfo, extraInfo));
                break;
            case "professional":
                professionals.add(new Professional(staffID, id, fullName, birthDate, contactInfo, extraInfo));
                break;
            default:
                System.err.println("Invalid staff type: " + type);
        }
    }

    /**
     * Gets the list of Academic staff.
     *
     * @return List of Academic staff.
     */
    public static List<Academic> getAcademics() {
        return academics;
    }

    /**
     * Gets the list of Professional staff.
     *
     * @return List of Professional staff.
     */
    public static List<Professional> getProfessionals() {
        return professionals;
    }
}
