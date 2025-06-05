package helper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Luong Thi Tra My - s3987023
 * @version 1.0
 * <p>
 * Handles loading staff data from a file.
 */
public class StaffDataLoader {
    private static final String FILE_PATH = "console_app/database/staff.txt"; // Path to staff data file

    /**
     * Reads the staff data file and processes each line.
     */
    public static void loadStaff() {
        StaffDataProcessor.getAcademics().clear(); // Clear existing data
        StaffDataProcessor.getProfessionals().clear(); // Clear existing data

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                StaffDataProcessor.processStaffRecord(line); // Delegates processing
            }
        } catch (IOException e) {
            System.err.println("Error reading staff data file: " + e.getMessage());
        }
    }
}
