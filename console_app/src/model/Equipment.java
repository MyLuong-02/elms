package model;

import utils.DateUtils;

import java.util.Date;

/**
 * @author Luong Thi Tra My - s3987023
 * @version 1.0
 * <p>
 * Represents an equipment item in the university inventory.
 * Equipment has an ID, name, status (Available, Borrowed, Unavailable),
 * purchase date, and condition (e.g., Brand New, Good, Needs Maintenance, Out of Service).
 * @since 2025-03-20
 */
public class Equipment {
    private String id;          // Unique equipment ID
    private String name;        // Equipment name
    private String status;      // Status: Available, Borrowed, Unavailable
    private Date purchasedDate; // Date when equipment was purchased
    private String condition;   // Condition: Brand New, Good, Needs Maintenance, Out of Service

    // Default constructor
    public Equipment() {
    }

    // Parameterized constructor
    public Equipment(String id, String name, String status, Date purchasedDate, String condition) {
        this.id = id;
        this.name = name;
        this.condition = condition;
        this.status = status;
        this.purchasedDate = purchasedDate;
    }

    // Getter and Setter methods
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getPurchasedDate() {
        return purchasedDate;
    }

    public String getCondition() {
        return condition;
    }

    /**
     * Set a new condition
     *
     * @param condition The new condition of the equipment.
     */
    public void setCondition(String condition) {
        this.condition = condition;
    }

    /**
     * Displays equipment details in a readable format.
     */
    public void displayInfo() {
        System.out.println(toString());
    }

    @Override
    public String toString() {
        return "Equipment ID: " + id + "\n" +
                "Name: " + name + "\n" +
                "Status: " + status + "\n" +
                "Purchased Date: " + DateUtils.formatDate(purchasedDate) + "\n" +
                "Condition: " + condition + "\n" +
                "-------------------------";
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}