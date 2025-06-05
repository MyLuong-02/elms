package helper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Luong Thi Tra My - s3987023
 * @version 1.0
 * <p>
 * Handles loading equipment data from a file.
 */
public class EquipmentDataLoader {
    private static final String FILE_PATH = "console_app/database/equipment.txt"; // Path to equipment data file

    /**
     * Reads the equipment data file and processes each line.
     */
    public static void loadEquipment() {
        EquipmentDataProcessor.getEquipments().clear();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                EquipmentDataProcessor.processEquipmentRecord(line); // Delegates processing
            }
        } catch (IOException e) {
            System.err.println("Error reading staff data file: " + e.getMessage());
        }
    }
}
