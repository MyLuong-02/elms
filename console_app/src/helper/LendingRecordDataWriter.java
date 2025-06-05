package helper;

import model.*;
import utils.DateUtils;

import java.io.*;
import java.util.*;

/**
 * @author Luong Thi Tra My - s3987023
 * @version 1.0
 * <p>
 * A helper class to save and update lending records.
 */
public class LendingRecordDataWriter {
    private static final String FILE_PATH = "console_app/database/lending_records.txt"; // Path to lending records

    /**
     * Saves a single lending record to the file.
     * Appends the record without removing existing data.
     *
     * @param record The lending record to be saved.
     */
    public static void saveLendingRecord(LendingRecord record) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            StringBuilder recordLine = new StringBuilder();
            recordLine.append(record.getRecordID()).append(", ")
                    .append(record.getBorrower().getBorrowerID()).append(", ");

            // Save equipment as semicolon-separated list
            List<Equipment> equipmentList = record.getEquipmentList();
            for (int i = 0; i < equipmentList.size(); i++) {
                recordLine.append(equipmentList.get(i).getId());
                if (i < equipmentList.size() - 1) {
                    recordLine.append(";");
                }
            }
            recordLine.append(", ");

            // Supervisor ID (if student, otherwise "-")
            if (record.getBorrower() instanceof Student && record.getSupervisor() != null) {
                recordLine.append(record.getSupervisor().getBorrowerID());
            } else {
                recordLine.append("-");
            }
            recordLine.append(", ");

            // Borrow date, return date, status, purpose
            recordLine.append(DateUtils.formatDateForFile(record.getBorrowDate())).append(", ")
                    .append(DateUtils.formatDateForFile(record.getReturnDate())).append(", ")
                    .append(record.getStatus()).append(", ")
                    .append(record.getPurpose());

            // Write to file
            writer.write(recordLine.toString());
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error saving lending record: " + e.getMessage());
        }
    }

    /* *
     * Save all lending records to the file after removing a specific lending record.
     * Overwrites existing data.
     */
    public static void saveAllLendingRecords(List<LendingRecord> lendingRecords) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, false))) { // Overwrite file
            for (LendingRecord record : lendingRecords) {
                saveLendingRecord(record);
            }
        } catch (IOException e) {
            System.err.println("Error saving lending records: " + e.getMessage());
        }
    }

    /**
     * Updates an existing lending record in the file.
     *
     * @param updatedRecord
     */
    public static void updateLendingRecord(LendingRecord updatedRecord) {
        List<String> updatedLines = new ArrayList<>();
        boolean recordFound = false;

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(", ");
                if (data.length >= 8 && data[0].trim().equals(updatedRecord.getRecordID())) {
                    // Found the record, update it
                    recordFound = true;

                    StringBuilder recordLine = new StringBuilder();
                    recordLine.append(updatedRecord.getRecordID()).append(", ")
                            .append(updatedRecord.getBorrower().getBorrowerID()).append(", ");

                    // Save updated equipment list
                    List<Equipment> equipmentList = updatedRecord.getEquipmentList();
                    for (int i = 0; i < equipmentList.size(); i++) {
                        recordLine.append(equipmentList.get(i).getId());
                        if (i < equipmentList.size() - 1) {
                            recordLine.append(";");
                        }
                    }
                    recordLine.append(", ");

                    // Update supervisor ID (if applicable)
                    if (updatedRecord.getBorrower() instanceof Student && updatedRecord.getSupervisor() != null) {
                        recordLine.append(updatedRecord.getSupervisor().getBorrowerID());
                    } else {
                        recordLine.append("-");
                    }
                    recordLine.append(", ");

                    // Update borrow date, return date, status, purpose
                    recordLine.append(DateUtils.formatDateForFile(updatedRecord.getBorrowDate())).append(", ")
                            .append(DateUtils.formatDateForFile(updatedRecord.getReturnDate())).append(", ")
                            .append(updatedRecord.getStatus()).append(", ")
                            .append(updatedRecord.getPurpose());

                    updatedLines.add(recordLine.toString());
                } else {
                    updatedLines.add(line); // Keep other records unchanged
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading lending records file: " + e.getMessage());
            return;
        }

        // If the record was found and updated, rewrite the file
        if (recordFound) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
                for (String updatedLine : updatedLines) {
                    writer.write(updatedLine);
                    writer.newLine();
                }
            } catch (IOException e) {
                System.err.println("Error writing updated lending records: " + e.getMessage());
            }
        } else {
            System.out.println("Lending record not found. No update performed.");
        }
    }

}
