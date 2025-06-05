package model;

import utils.DateUtils;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

/**
 * @author Luong Thi Tra My
 * @version 1.0
 * *
 * Represents an academic staff member.
 * An academic staff member is a type of staff who has an area of expertise and supervises students.
 */
public class Academic extends Staff {
    private String expertise; // Field of expertise of the academic
    private List<Student> studentsBorrowed; // List of students supervised for equipment borrowing

    // Default constructor
    public Academic() {
        super();
        this.expertise = ""; // Default value to prevent null issues
        this.studentsBorrowed = new ArrayList<>();
    }

    /**
     * Parameterized constructor to initialize an academic staff member.
     *
     * @param staffID     The unique staff ID.
     * @param id          The general ID of the person.
     * @param fullName    The full name of the academic.
     * @param birthDate   The birth date of the academic.
     * @param contactInfo The contact information of the academic.
     * @param expertise   The area of expertise of the academic.
     */
    public Academic(String staffID, String id, String fullName, Date birthDate, String contactInfo, String expertise) {
        super(staffID, id, fullName, birthDate, contactInfo);
        this.expertise = expertise;
        this.studentsBorrowed = new ArrayList<>();
    }

    /**
     * Retrieves the expertise of the academic.
     *
     * @return The expertise field.
     */
    public String getExpertise() {
        return expertise;
    }

    /**
     * /**
     * Retrieves the list of students supervised by this academic.
     *
     * @return A list of students.
     */
    public List<Student> getStudentsBorrowed() {
        return studentsBorrowed;
    }

    /**
     * Adds a student to the supervision list.
     *
     * @param student The student to be added.
     */
    public void addStudentBorrowed(Student student) {
        if (student != null && !studentsBorrowed.contains(student)) {
            studentsBorrowed.add(student);
        }
    }

    /**
     * Updates the supervised students list based on lending records.
     *
     * @param lendingRecords List of all lending records.
     */
    public void updateSupervisedStudents(List<LendingRecord> lendingRecords) {
        for (LendingRecord record : lendingRecords) {
            if (record.getSupervisor() != null && record.getSupervisor().getStaffID().equals(this.getStaffID())) {
                Student student = (Student) record.getBorrower();
                addStudentBorrowed(student);
            }
        }
    }

    /**
     * Checks if two Academic objects are equal based on their staffID.
     *
     * @param obj The object to compare with.
     * @return true if both objects have the same staffID, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Academic)) return false;
        Academic other = (Academic) obj;
        return Objects.equals(this.getStaffID(), other.getStaffID());
    }

    /**
     * Generates a hash code for Academic objects based on staffID.
     *
     * @return A hash code value.
     */
    @Override
    public int hashCode() {
        return Objects.hash(getStaffID());
    }

    /**
     * Displays academic staff member's information.
     * Includes expertise and supervised students.
     * Birth date is formatted as MM/dd/yyyy.
     */
    @Override
    public void displayInfo() {
        System.out.println("Staff ID: " + getStaffID());
        System.out.println("Full Name: " + getFullName());
        System.out.println("Birth Date: " + DateUtils.formatDate(getBirthDate()));
        System.out.println("Expertise: " + expertise);
        System.out.println("Contact Info: " + getContactInfo());

        //Get number of students supervising
        System.out.println("Number of students supervised: " + studentsBorrowed.size());

        Student student = new Student();
        if (studentsBorrowed.isEmpty()) {
            System.out.println(" - No supervised student");
        } else {
            for (int i = 0; i < studentsBorrowed.size(); i++) {
                System.out.println(i + 1 + ". " + studentsBorrowed.get(i).getFullName() + " (Student ID: " + studentsBorrowed.get(i).getStudentID() + ")");
            }
        }
    }
}
