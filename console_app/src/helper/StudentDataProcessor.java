package helper;

import model.Student;
import utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Luong Thi Tra My - s3987023
 * @version 1.0
 * <p>
 * Processes student data and stores them in a list
 */
public class StudentDataProcessor {
    private static final List<Student> students = new ArrayList<>();

    /**
     * Processes a single line of student data and adds it to the list.
     *
     * @param line A comma-separated string containing student details.
     */
    public static void processStudentRecord(String line) {
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
        String studentID = data[0].trim();
        String id = data[1].trim();
        String fullName = data[2].trim();
        String contactInfo = data[4].trim();

        try {
            // Parse date safely
            Date birthDate = DateUtils.parseDate(data[3].trim());

            // If parsing succeeds, add the student
            addStudentMember(studentID, id, fullName, birthDate, contactInfo);
        } catch (Exception e) {
            System.err.println("Error parsing date in record: " + line);
            e.printStackTrace(); // Print error details for debugging
        }
    }

    /**
     * Adds a student member to the list.
     */
    private static void addStudentMember(String studentID, String id, String fullName, Date birthDate, String contactInfo) {
        students.add(new Student(studentID, id, fullName, birthDate, contactInfo));
    }

    // Get a list of students
    public static List<Student> getStudents() {
        return students;
    }
}
