package controller;

import model.Equipment;

import java.util.List;

/**
 * @author Luong Thi Tra My - s3987023
 * @version 1.0
 * <p>
 * InventoryManager interface defines core functionalities for managing inventory.
 * This includes adding, updating, removing, and retrieving equipment details.
 */
public interface InventoryManager {

    /**
     * Adds a new equipment item to the inventory.
     *
     * @param equipment The equipment object to be added.
     * @return true if the equipment was successfully added, false otherwise.
     */
    public boolean addEquipment(Equipment equipment);

    /**
     * Updates an existing equipment in the inventory.
     *
     * @param equipmentID The unique ID of the equipment to be updated.
     * @return true if the equipment was successfully updated, false otherwise.
     */
    public boolean updateEquipment(String equipmentID);

    /**
     * Removes an equipment item from the inventory.
     *
     * @param equipmentID The unique ID of the equipment to be removed.
     * @return true if the equipment was successfully removed, false otherwise.
     */
    public boolean removeEquipment(String equipmentID);

    /**
     * Retrieves a list of all equipment stored in the inventory.
     *
     * @return A list containing all equipment objects.
     */
    public List<Equipment> getAllEquipment();

    /**
     * Retrieves a list of all available equipment in the inventory.
     * Equipment is considered available if its status is "Available".
     *
     * @return A list of available equipment objects.
     */
    public List<Equipment> getAvailableEquipment();
}
