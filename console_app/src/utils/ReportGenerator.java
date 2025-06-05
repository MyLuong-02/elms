package utils;

import model.Equipment;
import model.LendingRecord;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


/**
 * @author Luong Thi Tra My - s3987023
 * @Version 1.0
 * <p>
 * Utility class to handle exporting reports to text files.
 */
public class ReportGenerator {
    /**
     * Export a list of overdue lending records to text file.
     *
     * @param records  List of overdue lending records to export.
     * @param filePath File path for saving the report.
     */
    public static void exportLendingRecordsToFile(List<LendingRecord> records, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("Overdue Lending Records Report\n");
            writer.write("----------------------\n");

            for (int i = 0; i < records.size();
                 i++) {
                LendingRecord record = records.get(i);
                writer.write(i + 1 + ". ");
                writer.write(record.toString() + "\n"); // Assuming toString() provides readable output
            }
        } catch (IOException e) {
            System.err.println("Error writing overdue lending records to file: " + e.getMessage());
        }
    }

    /**
     * Export a list of available equipment to text file
     *
     * @param equipmentList List of available equipment to export.
     * @param filePath      File path for saving the report.
     */
    public static void exportAvailableEquipmentToFile(List<Equipment> equipmentList, String newFilePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(newFilePath))) {
            writer.write("Available Equipment Report\n");
            writer.write("----------------------\n");

            for (int i = 0; i < equipmentList.size(); i++) {
                Equipment equipment = equipmentList.get(i);
                writer.write(i + 1 + ". ");
                writer.write(equipment.toString() + "\n"); // Assuming toString() provides readable output
            }
        } catch (IOException e) {
            System.err.println("Error writing available equipment to file: " + e.getMessage());
        }
    }
}
