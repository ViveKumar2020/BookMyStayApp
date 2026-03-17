import java.util.*;

// Booking Request Model
class BookingRequest {
    private String customerName;
    private String roomType;

    public BookingRequest(String customerName, String roomType) {
        this.customerName = customerName;
        this.roomType = roomType;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getRoomType() {
        return roomType;
    }
}

// Inventory Service
class InventoryService {

    private Map<String, Integer> roomInventory = new HashMap<>();
    private Map<String, Set<String>> allocatedRooms = new HashMap<>();

    public InventoryService() {

        roomInventory.put("Standard", 3);
        roomInventory.put("Deluxe", 2);
        roomInventory.put("Suite", 1);

        allocatedRooms.put("Standard", new HashSet<>());
        allocatedRooms.put("Deluxe", new HashSet<>());
        allocatedRooms.put("Suite", new HashSet<>());
    }

    public boolean isAvailable(String roomType) {
        return roomInventory.getOrDefault(roomType, 0) > 0;
    }

    public String allocateRoom(String roomType) {

        if (!isAvailable(roomType)) {
            return null;
        }

        // Generate unique room ID
        String roomId = roomType.substring(0, 3).toUpperCase() +
                "-" + UUID.randomUUID().toString().substring(0, 5);

        Set<String> rooms = allocatedRooms.get(roomType);

        // Ensure uniqueness
        while (rooms.contains(roomId)) {
            roomId = roomType.substring(0, 3).toUpperCase() +
                    "-" + UUID.randomUUID().toString().substring(0, 5);
        }

        // Save allocated room
        rooms.add(roomId);

        // Update inventory
        roomInventory.put(roomType, roomInventory.get(roomType) - 1);

        return roomId;
    }

    public void showInventory() {
        System.out.println("\nRemaining Inventory:");
        for (String type : roomInventory.keySet()) {
            System.out.println(type + " -> " + roomInventory.get(type));
        }
    }

    public void showAllocatedRooms() {
        System.out.println("\nAllocated Rooms:");
        for (String type : allocatedRooms.keySet()) {
            System.out.println(type + " -> " + allocatedRooms.get(type));
        }
    }
}

// Booking Service
class BookingService {

    private Queue<BookingRequest> bookingQueue = new LinkedList<>();
    private InventoryService inventoryService;

    public BookingService(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    public void addBookingRequest(BookingRequest request) {
        bookingQueue.offer(request);
    }

    public void processBookings() {

        while (!bookingQueue.isEmpty()) {

            BookingRequest request = bookingQueue.poll();

            System.out.println("\nProcessing Booking for: " + request.getCustomerName());

            if (inventoryService.isAvailable(request.getRoomType())) {

                String roomId = inventoryService.allocateRoom(request.getRoomType());

                System.out.println("Reservation Confirmed");
                System.out.println("Customer: " + request.getCustomerName());
                System.out.println("Room Type: " + request.getRoomType());
                System.out.println("Room ID: " + roomId);

            } else {
                System.out.println("Reservation Failed - No " +
                        request.getRoomType() + " rooms available.");
            }
        }
    }
}

// Main Class
public class BookMyStayApp {

    public static void main(String[] args) {

        InventoryService inventoryService = new InventoryService();
        BookingService bookingService = new BookingService(inventoryService);

        // Add booking requests
        bookingService.addBookingRequest(new BookingRequest("Alice", "Standard"));
        bookingService.addBookingRequest(new BookingRequest("Bob", "Deluxe"));
        bookingService.addBookingRequest(new BookingRequest("Charlie", "Standard"));
        bookingService.addBookingRequest(new BookingRequest("David", "Suite"));
        bookingService.addBookingRequest(new BookingRequest("Eva", "Standard"));

        // Process queue
        bookingService.processBookings();

        // Show system state
        inventoryService.showInventory();
        inventoryService.showAllocatedRooms();
    }
}