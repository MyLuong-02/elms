package model;

import java.util.Date;

import utils.DateUtils;

import java.util.List;

/**
 * @author Luong Thi Tra My - s3987023
 * @version 1.0
 * *
 * Represents a confirmed lending transaction.
 * A lending record is created only when a borrowing request is approved.
 */
public class LendingRecord {
    private String recordID;
    private Borrower borrower;
    private List<Equipment> equipmentList;
    private Academic supervisor; // Only for students
    private Date borrowDate;
    private Date returnDate;
    private String status;
    private String purpose;

    /**
     * Constructor for LendingRecord.
     */
    public LendingRecord(String recordID, Borrower borrower, List<Equipment> equipmentList, Academic supervisor,
                         Date borrowDate, Date returnDate, String status, String purpose) {
        this.recordID = recordID;
        this.borrower = borrower;
        this.equipmentList = equipmentList;
        this.supervisor = supervisor;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.status = status;
        this.purpose = purpose;
    }

    // Getter and setter methods
    public String getRecordID() {
        return recordID;
    }

    public void setRecordID(String recordID) {
        this.recordID = recordID;
    }

    public Borrower getBorrower() {
        return borrower;
    }

    public void setBorrower(Borrower borrower) {
        this.borrower = borrower;
    }

    public List<Equipment> getEquipmentList() {
        return equipmentList;
    }

    public void setEquipmentList(List<Equipment> equipmentList) {
        this.equipmentList = equipmentList;
    }

    public Academic getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Academic supervisor) {
        this.supervisor = supervisor;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPurpose() {
        return purpose;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    /**
     * Displays the lending record details.
     */
    public void displayRecord() {
        System.out.println(toString());
    }

    /**
     * Converts the LendingRecord object to a formatted string.
     *
     * @return A string representation of the LendingRecord.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Lending Record ID: ").append(recordID).append("\n");
        sb.append("Borrower: ").append(borrower.getFullName()).append("\n");
        sb.append("Equipment(s): \n");
        for (Equipment equipment : equipmentList) {
            sb.append("   - ").append(equipment.getName())
                    .append(" (Condition: ").append(equipment.getCondition()).append(")\n");
        }

        sb.append("Status: ").append(status).append("\n");
        sb.append("Purpose: ").append(purpose).append("\n");
        sb.append("Borrow Date: ").append(DateUtils.formatDate(borrowDate)).append("\n");
        sb.append("Return Date: ").append(DateUtils.formatDate(returnDate)).append("\n");

        if (borrower instanceof Student) {
            sb.append("Supervisor: ").append(supervisor != null ? supervisor.getFullName() : "None").append("\n");
        }
        sb.append("-------------------------");
        return sb.toString();
    }
}
