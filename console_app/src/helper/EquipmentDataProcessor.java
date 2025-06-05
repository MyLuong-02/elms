package helper;

import model.Equipment;
import utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: Luong Thi Tra My - s3987023
 * @Version: 1.0
 * <p>
 * Processes equipment data and stores them in a list
 */
public class EquipmentDataProcessor {
    private static final List<Equipment> equipments = new ArrayList<>(); // List to store equipment data

    /**
     * Processes a single line of equipment data and adds it to the list.
     *
     * @param line A comma-separated string containing equipment details.
     */
    public static void processEquipmentRecord(String line) {
        line = line.trim(); // Remove leading/trailing spaces

        // Skip blank lines
        if (line.isEmpty()) {
            return;
        }

        String[] data = line.split(",");

        // Check if the line has the expected number of fields
        if (data.length != 5) {
            System.err.println("Invalid data format: " + line);
            return;
        }

        // Extract and trim values
        String id = data[0].trim();
        String name = data[1].trim();
        String status = data[2].trim();
        String condition = data[4].trim();

        try {
            // Parse date safely
            Date purchasedDate = DateUtils.parseDate(data[3].trim());

            // If parsing succeeds, add the equipment
            addEquipment(id, name, status, purchasedDate, condition);
        } catch (Exception e) {
            System.err.println("Error parsing date in record: " + line);
            e.printStackTrace(); // Print error details for debugging
        }
    }

    /**
     * Adds an equipment to the list.
     */
    private static void addEquipment(String id, String name, String status, Date purchasedDate, String condition) {
        equipments.add(new Equipment(id, name, status, purchasedDate, condition));
    }

    // Get a list of equipment
    public static List<Equipment> getEquipments() {
        return equipments;
    }
}
