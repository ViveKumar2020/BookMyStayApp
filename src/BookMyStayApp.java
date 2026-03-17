import java.io.*;
import java.util.*;

class InventoryState implements Serializable {
    private static final long serialVersionUID = 1L;

    Map<String, Integer> inventory;

    public InventoryState(Map<String, Integer> inventory) {
        this.inventory = inventory;
    }

    public Map<String, Integer> getInventory() {
        return inventory;
    }
}

class PersistenceService {

    private static final String FILE_NAME = "inventory.dat";

    public static void saveInventory(Map<String, Integer> inventory) {

        try (ObjectOutputStream out =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            out.writeObject(new InventoryState(inventory));
            System.out.println("Inventory saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving inventory: " + e.getMessage());
        }
    }

    public static Map<String, Integer> loadInventory() {

        try (ObjectInputStream in =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            InventoryState state = (InventoryState) in.readObject();
            return state.getInventory();

        } catch (Exception e) {
            System.out.println("No valid inventory data found. Starting fresh.");
            return null;
        }
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("System Recovery");

        Map<String, Integer> inventory =
                PersistenceService.loadInventory();

        if (inventory == null) {
            inventory = new HashMap<>();
            inventory.put("Single", 5);
            inventory.put("Double", 3);
            inventory.put("Suite", 2);
        }

        System.out.println("\nCurrent Inventory:");

        for (String room : inventory.keySet()) {
            System.out.println(room + ": " + inventory.get(room));
        }

        PersistenceService.saveInventory(inventory);
    }
}