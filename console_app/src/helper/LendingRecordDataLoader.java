package helper;

import model.LendingRecord;
import model.Student;
import model.Academic;
import model.Professional;
import model.Equipment;

import java.io.*;
import java.util.*;

/**
 * @author Luong Thi Tra My - s3987023
 * @version 1.0
 * <p>
 * A helper class to load lending records from a file.
 */
public class LendingRecordDataLoader {
    private static final String FILE_PATH = "console_app/database/lending_records.txt";

    /**
     * Loads lending records from the file and processes them.
     */
    public static void loadLendingRecords() {
        LendingRecordProcessor.getLendingRecords().clear(); // Clear existing records

        // Get students, academics, professionals, and equipment
        List<Student> students = StudentDataProcessor.getStudents();
        List<Academic> academics = StaffDataProcessor.getAcademics();
        List<Professional> professionals = StaffDataProcessor.getProfessionals();
        List<Equipment> equipmentList = EquipmentDataProcessor.getEquipments();

        // Create HashMaps for quick lookup
        Map<String, Student> studentMap = new HashMap<>();
        Map<String, Academic> academicMap = new HashMap<>();
        Map<String, Professional> professionalMap = new HashMap<>();
        Map<String, Equipment> equipmentMap = new HashMap<>();

        for (Student s : students) studentMap.put(s.getBorrowerID(), s);
        for (Academic a : academics) academicMap.put(a.getBorrowerID(), a);
        for (Professional p : professionals) professionalMap.put(p.getBorrowerID(), p);
        for (Equipment e : equipmentList) equipmentMap.put(e.getId(), e);

        // Read lending records from the file and process them
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                LendingRecordProcessor.processLendingRecord(line, studentMap, academicMap, professionalMap, equipmentMap);
            }
        } catch (IOException e) {
            System.err.println("Error loading lending records: " + e.getMessage());
        }
    }
}
