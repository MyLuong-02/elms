package model;

import java.util.Date;

import utils.DateUtils;

/**
 * @author Luong Thi Tra My - s3987023
 * @version 1.0
 * <p>
 * Represents a person with basic personal details.
 * This class serves as an abstract base for different types of people in the system.
 * @since 2025-03-20
 */
public abstract class Person {
    // Unique identifier for the person
    private String id;

    // Full name of the person
    private String fullName;

    // Birth date of the person
    private Date birthDate;

    // Contact information of the person
    private String contactInfo;

    //Default constructor
    public Person() {
        this.id = ""; // Default value to prevent null issues
        this.fullName = ""; // Default value to prevent null issues
        this.birthDate = new Date(); // Default value to prevent null issues
        this.contactInfo = ""; // Default value to prevent null issues
    }

    // Parameterized constructor to initialize a person with specific details
    public Person(String id, String fullName, Date birthDate, String contactInfo) {
        this.id = id;
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.contactInfo = contactInfo;
    }

    // Retrieves the ID of the person
    public String getId() {
        return id;
    }

    // Retrieves the full name of the person
    public String getFullName() {
        return fullName;
    }

    // Retrieves the raw Date object (
    public Date getBirthDate() {
        return birthDate;
    }

    // Retrieves the contact information of the person
    public String getContactInfo() {
        return contactInfo;
    }

    /**
     * Define the displayInfo() method to displays all the details of a person.
     */
    public abstract void displayInfo();
}
