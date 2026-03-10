import java.util.HashMap;
import java.util.Map;

public class BookMyStayApp {

    // Abstract Room class
    static abstract class Room {
        int beds;
        double price;
        int size;

        Room(int beds, double price, int size) {
            this.beds = beds;
            this.price = price;
            this.size = size;
        }

        void displayDetails() {
            System.out.println("Beds: " + beds);
            System.out.println("Price: Rs " + price);
            System.out.println("Size: " + size + " ft");
        }
    }

    static class SingleRoom extends Room {
        SingleRoom() {
            super(1, 2000, 300);
        }
    }

    static class DoubleRoom extends Room {
        DoubleRoom() {
            super(2, 3000, 500);
        }
    }

    static class SuiteRoom extends Room {
        SuiteRoom() {
            super(3, 5000, 900);
        }
    }

    static class RoomInventory {

        private Map<String, Integer> roomAvailability;

        public RoomInventory() {
            roomAvailability = new HashMap<>();
            initializeInventory();
        }

        private void initializeInventory() {
            roomAvailability.put("Single Room", 5);
            roomAvailability.put("Double Room", 3);
            roomAvailability.put("Suite Room", 2);
        }

        public Map<String, Integer> getRoomAvailability() {
            return roomAvailability;
        }
    }


    static class SearchService {

        public void searchAvailableRooms(RoomInventory inventory, Map<String, Room> rooms) {

            Map<String, Integer> availability = inventory.getRoomAvailability();

            System.out.println("Available Rooms:\n");

            for (String roomType : rooms.keySet()) {

                int count = availability.getOrDefault(roomType, 0);

                if (count > 0) {

                    System.out.println(roomType + ":");
                    rooms.get(roomType).displayDetails();
                    System.out.println("Available: " + count);
                    System.out.println();
                }
            }
        }
    }

    public static void main(String[] args) {

        Map<String, Room> rooms = new HashMap<>();
        rooms.put("Single Room", new SingleRoom());
        rooms.put("Double Room", new DoubleRoom());
        rooms.put("Suite Room", new SuiteRoom());

        RoomInventory inventory = new RoomInventory();

        SearchService search = new SearchService();

        search.searchAvailableRooms(inventory, rooms);
    }
}
