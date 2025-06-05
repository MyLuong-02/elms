package controller;

import helper.*;
import model.*;

import java.util.List;

/**
 * @Author: Luong Thi Tra My - s3987023
 * @Version: 1.0
 * <p>
 * Controller class for managing staff data.
 */
public class StaffController {
    //Define the method to view all staff members
    public void getAllStaff() {

        // Retrieve lists of Academic and Professional staff
        List<Academic> academics = StaffDataProcessor.getAcademics();
        List<Professional> professionals = StaffDataProcessor.getProfessionals();

// Retrieve all lending records
        List<LendingRecord> lendingRecords = LendingRecordProcessor.getLendingRecords();

        // Update each academic with their students
        for (Academic academic : academics) {
            academic.updateSupervisedStudents(lendingRecords);
        }

        // Display Academic staff
        System.out.println("Academic Staff:");
        if (academics.isEmpty()) {
            System.out.println("No academic staff found.");
        } else {
            for (Academic academic : academics) {
                academic.displayInfo();
                System.out.println("----------------------");
            }
        }

        // Display Professional staff
        System.out.println("Professional Staff:");
        if (professionals.isEmpty()) {
            System.out.println("No professional staff found.");
        } else {
            for (Professional professional : professionals) {
                professional.displayInfo();
                System.out.println("----------------------");
            }
        }

    }
}
