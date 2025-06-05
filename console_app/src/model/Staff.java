package model;

import java.util.Date;

/**
 * @author Luong Thi Tra My
 * @version 1.0
 * <p>
 * Represents a staff member in the system.
 * A staff member is a type of person with an additional staff ID.
 */
public abstract class Staff extends Person implements Borrower {
    private String staffID; // Unique ID for the staff member

    //Default constructor for the staff
    public Staff() {
        super();
        this.staffID = "Unknown"; // Default value to prevent null issues
    }

    /**
     * Parameterized constructor to initialize a staff member.
     *
     * @param staffID     The unique ID of the staff member.
     * @param id          The general ID of the person.
     * @param fullName    The full name of the staff member.
     * @param birthDate   The birth date of the staff member.
     * @param contactInfo The contact information of the staff member.
     */
    public Staff(String staffID, String id, String fullName, Date birthDate, String contactInfo) {
        super(id, fullName, birthDate, contactInfo);
        this.staffID = staffID;
    }

    /**
     * Retrieves the staff ID.
     *
     * @return The staff ID.
     */
    public String getStaffID() {
        return staffID;
    }

    /**
     * Set the staff ID.
     */
    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }

    //Get borrower ID which is a staff
    @Override
    public String getBorrowerID() {
        return staffID;
    }
}
