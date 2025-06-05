package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import utils.DateUtils;

/**
 * @author Luong Thi Tra My - s3987023
 * @version 1.0
 * *
 * Represents a student who can borrow equipment.
 * Inherits personal details from the Person class.
 * A student has lending records and requires supervision by an academic staff member for borrowing.
 */
public class Student extends Person implements Borrower {
    private String studentID; //Initialize student  ID
    private List<LendingRecord> lendingRecords;  // List of lending records associated with the student
    private Academic supervisor; // Supervisor assigned to the student

    /**
     * Default constructor for the Student class.
     * Initializes an empty list of lending records.
     */
    public Student() {
        super();
        this.studentID = "";
        this.lendingRecords = new ArrayList<>();
        this.supervisor = null;
    }

    /**
     * Parameterized constructor to initialize a student with specific details.
     *
     * @param id          Unique ID of the student (inherited from Person)
     * @param fullName    Full name of the student (inherited from Person)
     * @param birthDate   Birth date of the student (inherited from Person)
     * @param contactInfo Contact information of the student (inherited from Person)
     * @param studentID   Unique student ID
     */
    public Student(String studentID, String id, String fullName, Date birthDate, String contactInfo) {
        super(id, fullName, birthDate, contactInfo);
        this.studentID = studentID;
        this.lendingRecords = new ArrayList<>();
        this.supervisor = null;
    }

    /**
     * Retrieves the student ID.
     *
     * @return Student ID as a string
     */
    public String getStudentID() {
        return studentID;
    }

    /**
     * Set the student ID.
     */
    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    /**
     * Adds a lending record to the student's lending history.
     *
     * @param record The lending record to be added
     */
    public void addLendingRecord(LendingRecord record) {
        if (record != null && !lendingRecords.contains(record)) {
            lendingRecords.add(record);
        }
    }

    /**
     * Retrieves the academic supervisor for the student.
     *
     * @return The assigned academic supervisor
     */
    public Academic getSupervisor() {
        return supervisor;
    }

    /**
     * Sets the academic supervisor for the student.
     *
     * @param supervisor The academic staff supervising this student
     */
    public void setSupervisor(Academic supervisor) {
        this.supervisor = supervisor;
    }

    /**
     * Updates the student's lending records and assigns a supervisor
     * based on the provided list of lending records.
     *
     * @param allLendingRecords List of all lending records
     */
    public void updateLendingRecords(List<LendingRecord> allLendingRecords) {
        for (LendingRecord record : allLendingRecords) {
            if (record.getBorrower() instanceof Student &&
                    record.getBorrower().getBorrowerID().equals(this.studentID)) {
                // Check if the record is already in the list
                if (!lendingRecords.contains(record)) {
                    addLendingRecord(record);
                }

                if (record.getSupervisor() != null) {
                    setSupervisor(record.getSupervisor());
                }
            }
        }
    }

    /**
     * Retrieves the borrower's ID (required by the Borrower interface).
     *
     * @return The student ID as the borrower's unique identifier.
     */
    @Override
    public String getBorrowerID() {
        return studentID;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Student)) return false;
        Student other = (Student) obj;
        return this.studentID.equals(other.studentID) && super.equals(obj);
    }

    @Override
    public int hashCode() {
        return studentID.hashCode() * 31 + super.hashCode();
    }

    /**
     * Displays student details including lending history.
     */
    @Override
    public void displayInfo() {
        System.out.println("Student ID: " + studentID);
        System.out.println("Name: " + getFullName());
        System.out.println("Birth Date: " + DateUtils.formatDate(getBirthDate()));
        System.out.println("Supervised by: " + (supervisor != null ? supervisor.getFullName() : "None"));
        System.out.println("Contact Info: " + getContactInfo());
        if (lendingRecords.isEmpty()) {
            System.out.println("No records found.");
        } else {
            System.out.println("Lending Records:");
            for (LendingRecord record : lendingRecords) {
                //Get all information excluding borrower name and supervisor
                System.out.println("Lending record ID: " + record.getRecordID());
                System.out.println("Equipment(s): ");
                for (Equipment equipment : record.getEquipmentList()) {
                    System.out.println("   - " + equipment.getName() + " (Condition: " + equipment.getCondition() + ")");
                }
                System.out.println("Status: " + record.getStatus());
                System.out.println("Purpose: " + record.getPurpose());
                System.out.println("Borrow Date: " + DateUtils.formatDate(record.getBorrowDate()));
                System.out.println("Return Date: " + DateUtils.formatDate(record.getReturnDate()));
            }
        }
    }

    /**
     * Returns a string representation of the student.
     *
     * @return A string containing student details.
     */
    @Override
    public String toString() {
        return String.format("Student[ID=%s, Name=%s, Supervisor=%s, Total Records=%d]",
                studentID, getFullName(),
                (supervisor != null ? supervisor.getFullName() : "None"),
                lendingRecords.size());
    }
}
