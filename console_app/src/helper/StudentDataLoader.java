package helper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Luong Thi Tra My - s3987023
 * @version 1.0
 * <p>
 * Handles loading student  data from a file.
 */
public class StudentDataLoader {
    private static final String FILE_PATH = "console_app/database/students.txt"; // Path to staff data file

    /**
     * Reads the student  data file and processes each line.
     */
    public static void loadStudents() {
        StudentDataProcessor.getStudents().clear(); // Clear existing data
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                StudentDataProcessor.processStudentRecord(line); // Process each line
            }
        } catch (IOException e) {
            System.err.println("Error reading staff data file: " + e.getMessage());
        }
    }
}
