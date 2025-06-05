package view;

import controller.*;

/**
 * @Author: Luong Thi Tra My - s3987023
 * @Version: 1.0
 * <p>
 * View  class for viewing  operation related to human.
 */
public class PersonView {
    private final StaffController staffController = new StaffController(); // Staff controller instance
    private final StudentController studentController = new StudentController(); // Student controller instance

    //Method to view all staff
    public void viewAllStaff() {
        staffController.getAllStaff();
    }

    //Method to view all students
    public void viewAllStudents() {
        studentController.getAllStudents();
    }
}
