package helper;

import model.LendingRecord;
import model.Student;
import model.Academic;
import model.Professional;
import model.Equipment;
import model.Borrower;
import utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Luong Thi Tra My - s3987023
 * @version 1.0
 * <p>
 * Processes lending record data and stores them in a list.
 */
public class LendingRecordProcessor {
    private static final List<LendingRecord> lendingRecords = new ArrayList<>();

    /**
     * Processes a single line of lending record data and adds it to the list.
     *
     * @param line            A comma-separated string containing lending record details.
     * @param studentMap      A map of student IDs to Student objects.
     * @param academicMap     A map of academic IDs to Academic objects.
     * @param professionalMap A map of professional IDs to Professional objects.
     * @param equipmentMap    A map of equipment IDs to Equipment objects.
     */
    public static void processLendingRecord(String line,
                                            Map<String, Student> studentMap,
                                            Map<String, Academic> academicMap,
                                            Map<String, Professional> professionalMap,
                                            Map<String, Equipment> equipmentMap) {
        line = line.trim(); // Remove leading/trailing spaces

        // Skip blank lines
        if (line.isEmpty()) {
            return;
        }

        String[] data = line.split(",");
        if (data.length != 8) {
            System.err.println("Invalid data format: " + line);
            return;
        }
        try {
            String recordID = data[0].trim();
            String borrowerID = data[1].trim();
            String[] equipmentIDs = data[2].trim().split(";");
            String supervisorID = data[3].trim().isEmpty() ? "-" : data[3].trim();
            Date borrowDate = DateUtils.parseDate(data[4].trim());
            Date returnDate = DateUtils.parseDate(data[5].trim());
            String status = data[6].trim();
            String purpose = data[7].trim();

            Borrower borrower = findBorrower(borrowerID, studentMap, academicMap, professionalMap);
            if (borrower == null) {
                System.err.println("Borrower not found for ID: " + borrowerID);
                return;
            }

            Academic supervisor = (borrower instanceof Student && !supervisorID.equals("-"))
                    ? academicMap.get(supervisorID)
                    : null;

            List<Equipment> borrowedEquipment = findEquipment(equipmentIDs, equipmentMap);
            if (borrowedEquipment.isEmpty()) {
                System.err.println("No valid equipment found for record: " + recordID);
                return;
            }

            addLendingRecord(recordID, borrower, borrowedEquipment, supervisor, borrowDate, returnDate, status, purpose);
        } catch (Exception e) {
            System.err.println("Error processing lending record: " + line);
        }
    }

    /**
     * Adds a lending record to the list.
     */
    private static void addLendingRecord(String recordID, Borrower borrower, List<Equipment> equipmentList,
                                         Academic supervisor, Date borrowDate, Date returnDate,
                                         String status, String purpose) {
        lendingRecords.add(new LendingRecord(recordID, borrower, equipmentList, supervisor, borrowDate, returnDate, status, purpose));
    }

    /**
     * Finds a borrower by ID.
     */
    public static Borrower findBorrower(String id,
                                        Map<String, Student> studentMap,
                                        Map<String, Academic> academicMap,
                                        Map<String, Professional> professionalMap) {
        if (studentMap.containsKey(id)) return studentMap.get(id);
        if (academicMap.containsKey(id)) return academicMap.get(id);
        if (professionalMap.containsKey(id)) return professionalMap.get(id);
        return null;
    }

    /**
     * Finds equipment by IDs.
     */
    public static List<Equipment> findEquipment(String[] ids, Map<String, Equipment> equipmentMap) {
        List<Equipment> foundEquipment = new ArrayList<>();
        for (String id : ids) {
            if (equipmentMap.containsKey(id)) {
                foundEquipment.add(equipmentMap.get(id));
            }
        }
        return foundEquipment;
    }

    /**
     * Returns the list of lending records.
     */
    public static List<LendingRecord> getLendingRecords() {
        return lendingRecords;
    }
}
