package controller;

import helper.*;
import model.*;

import java.util.List;

/**
 * @Author: Luong Thi Tra My - s3987023
 * @Version: 1.0
 * <p>
 * Controller class for managing student data.
 */
public class StudentController {
    //Define the method to view students
    public void getAllStudents() {
        // Retrieve list of students
        List<Student> students = StudentDataProcessor.getStudents();

        // Retrieve all lending records
        List<LendingRecord> lendingRecords = LendingRecordProcessor.getLendingRecords();

        // Update each student with their lending records
        for (Student student : students) {
            student.updateLendingRecords(lendingRecords);
        }

        // Display students with their lending records
        System.out.println("Here is a list of students at our university: ");
        if (students.isEmpty()) {
            System.out.println("No student found.");
        } else {
            for (Student student : students) {
                student.displayInfo(); // This now includes lending records
                System.out.println("----------------------");
            }
        }
    }
}
