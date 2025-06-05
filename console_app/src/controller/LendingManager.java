package controller;

import model.*;

import java.util.List;

/**
 * @author Luong Thi Tra My
 * @version 1.0
 * *
 * LendingManager interface defines essential methods for handling the lending process,
 * <p>
 * It includes methods for adding, updating, deleting, and retrieving lending records.
 * @since 2025-03-23
 */
public interface LendingManager {

    /**
     * Adds a new lending record.
     *
     * @param record The lending record to be added.
     * @return true if the record was successfully added, false otherwise.
     */
    public boolean addLendingRecord(LendingRecord record);

    /**
     * Updates an existing lending record.
     *
     * @param record The lending record to be updated.
     * @return true if the record was successfully updated, false otherwise.
     */
    public boolean updateLendingRecord(String recordID);

    /**
     * Deletes a lending record.
     *
     * @param recordID The ID of the lending record to be deleted.
     * @return true if the record was successfully deleted, false otherwise.
     */
    public boolean deleteLendingRecord(String recordID);

    /**
     * Retrieves all lending records.
     *
     * @return A list of all lending records.
     */
    public List<LendingRecord> getAllLendingRecords();

    /**
     * Retrieves lending records associated with a specific borrower.
     *
     * @param borrowerID The ID of the borrower.
     * @return A list of lending records for the specified borrower.
     */
    public List<LendingRecord> getLendingRecordsByBorrower(String borrowerID);

    /**
     * Retrieves lending records for a specific equipment ID.
     *
     * @param equipmentID The ID of the equipment.
     * @return A list of lending records for the specified equipment.
     */
    public List<LendingRecord> getLendingRecordsByEquipment(String equipmentID);

    /**
     * Retrieves all overdue lending records.
     *
     * @return A list of lending records that are overdue.
     */
    public List<LendingRecord> getOverdueLendingRecords();
}
