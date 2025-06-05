package model;

import utils.DateUtils;

import java.util.Date;

/**
 * @author Luong Thi Tra My
 * @version 1.0
 * <p>
 * Represents a professional staff member.
 * A professional staff member is a type of staff who belongs to a specific department.
 * @since 2025-03-20
 */
public class Professional extends Staff {
    private String department; // Department where the professional staff works

    // Default constructor
    public Professional() {
        super();
        this.department = "";
    }

    /**
     * Parameterized constructor to initialize a professional staff member.
     *
     * @param staffID     The unique staff ID.
     * @param id          The general ID of the person.
     * @param fullName    The full name of the professional.
     * @param birthDate   The birth date of the professional.
     * @param contactInfo The contact information of the professional.
     * @param department  The department where the professional works.
     */
    public Professional(String staffID, String id, String fullName, Date birthDate, String contactInfo, String department) {
        super(staffID, id, fullName, birthDate, contactInfo);
        this.department = department;
    }

    /**
     * Retrieves the department of the professional staff.
     *
     * @return The department name.
     */
    public String getDepartment() {
        return department;
    }

    /**
     * Displays professional staff member's information.
     * Birth date is formatted as MM/dd/yyyy.
     */
    @Override
    public void displayInfo() {
        System.out.println("Staff ID: " + getStaffID());
        System.out.println("Full Name: " + getFullName());
        System.out.println("Birth Date: " + DateUtils.formatDate(getBirthDate()));
        System.out.println("Contact Info: " + getContactInfo());
        System.out.println("Department: " + department);
    }
}
