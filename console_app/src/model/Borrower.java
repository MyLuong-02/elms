package model;

/**
 * @author Luong Thi Tra My - s3987023
 * @version 1.0
 * <p>
 * Interface representing a Borrower.
 * Student, Academic and Professional  that can borrow equipment must implement this interface.
 */
public interface Borrower {
    String getBorrowerID(); //Get borrower ID

    String getFullName(); // Ensures each borrower  has a full name
}
