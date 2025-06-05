package helper;

import model.*;
import utils.DateUtils;

import java.io.*;
import java.util.*;

/**
 * @author Luong Thi Tra My - s3987023
 * @version 1.0
 * <p>
 * A helper class to save and update equipment.
 */
public class EquipmentDataWriter {
    private static final String FILE_PATH = "console_app/database/equipment.txt"; // Path to equipment data

    /**
     * Saves a new equipment to the file.
     * Appends the equipment without removing existing data.
     *
     * @param equipment The equipment to be saved.
     */
    public static void saveEquipment(Equipment equipment) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            StringBuilder equipmentLine = new StringBuilder();
            equipmentLine.append(equipment.getId()).append(", ")
                    .append(equipment.getName()).append(", ")
                    .append(equipment.getStatus()).append(", ")
                    .append(DateUtils.formatDateForFile(equipment.getPurchasedDate())).append(", ")
                    .append(equipment.getCondition());

            // Write to file
            writer.write(equipmentLine.toString());
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error saving equipment: " + e.getMessage());
        }
    }

    /* *
     * Save all equipment to the file.
     * Overwrites existing data.
     */
    public static void saveAllEquipment(List<Equipment> equipmentList) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, false))) { // Overwrite mode
            for (Equipment equipment : equipmentList) {
                writer.write(equipment.getId() + ", "
                        + equipment.getName() + ", "
                        + equipment.getStatus() + ", "
                        + DateUtils.formatDateForFile(equipment.getPurchasedDate()) + ", "
                        + equipment.getCondition());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving all equipment: " + e.getMessage());
        }
    }

    /**
     * Updates an existing equipment record in the file.
     *
     * @param updatedEquipment The modified equipment object with updated details.
     */
    public static void updateEquipmentInFile(Equipment updatedEquipment) {
        List<String> updatedLines = new ArrayList<>();
        boolean found = false;

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                if (data.length == 5 && data[0].trim().equals(updatedEquipment.getId())) {
                    // Clean and validate values
                    String name = updatedEquipment.getName().trim();
                    String status = updatedEquipment.getStatus();
                    String condition = updatedEquipment.getCondition().trim();
                    String date = DateUtils.formatDateForFile(updatedEquipment.getPurchasedDate());


                    // Update fields in array (no leading spaces)
                    data[1] = name;
                    data[2] = status;
                    data[3] = date;
                    data[4] = condition;

                    // Reconstruct the line with space after commas
                    line = String.join(", ", data);
                    System.out.println("Updated line: " + line); // Debug print
                    found = true;
                }

                updatedLines.add(line); // Keep all lines (updated or not)
            }
        } catch (IOException e) {
            System.err.println("Error reading equipment file: " + e.getMessage());
            return;
        }

        // Write back updated content if any changes made
        if (found) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
                for (String updatedLine : updatedLines) {
                    bw.write(updatedLine);
                    bw.newLine();
                }
            } catch (IOException e) {
                System.err.println("Error writing to equipment file: " + e.getMessage());
            }
        } else {
            System.out.println("Error: Equipment ID not found in file.");
        }
    }

    /* *
     * Updates the status of a specific equipment in the file.
     *
     * @param equipmentID The ID of the equipment to update.
     * @param newStatus   The new status to set (e.g., "Borrowed" or "Available").
     */
    public static void updateEquipmentStatus(String equipmentID, String newStatus) {
        List<String> updatedLines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 5 && data[0].trim().equals(equipmentID)) {
                    // Update status field
                    data[2] = " " + newStatus;
                    line = String.join(",", data);
                }
                updatedLines.add(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading equipment file: " + e.getMessage());
            return;
        }

        // Write updated content back to file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (String updatedLine : updatedLines) {
                bw.write(updatedLine);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to equipment file: " + e.getMessage());
        }
    }
}
