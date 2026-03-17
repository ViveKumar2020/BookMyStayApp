import java.util.*;

class BookingRequest {
    String guestName;
    String roomType;

    public BookingRequest(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

class BookingSystem {

    private Map<String, Integer> inventory = new HashMap<>();
    private Map<String, Integer> roomCounters = new HashMap<>();

    public BookingSystem() {
        inventory.put("Single", 5);
        inventory.put("Double", 3);
        inventory.put("Suite", 2);

        roomCounters.put("Single", 0);
        roomCounters.put("Double", 0);
        roomCounters.put("Suite", 0);
    }

    public synchronized void processBooking(BookingRequest request) {

        String roomType = request.roomType;

        if (inventory.get(roomType) > 0) {

            int count = roomCounters.get(roomType) + 1;
            roomCounters.put(roomType, count);

            String roomId = roomType + "-" + count;

            inventory.put(roomType, inventory.get(roomType) - 1);

            System.out.println("Booking confirmed for Guest: "
                    + request.guestName + ", Room ID: " + roomId);

        } else {
            System.out.println("No rooms available for Guest: " + request.guestName);
        }
    }

    public void displayInventory() {

        System.out.println("\nRemaining Inventory:");

        for (String room : inventory.keySet()) {
            System.out.println(room + ": " + inventory.get(room));
        }
    }
}

class BookingProcessor extends Thread {

    private BookingSystem system;
    private Queue<BookingRequest> queue;

    public BookingProcessor(BookingSystem system, Queue<BookingRequest> queue) {
        this.system = system;
        this.queue = queue;
    }

    public void run() {

        while (true) {

            BookingRequest request;

            synchronized (queue) {
                if (queue.isEmpty()) {
                    break;
                }
                request = queue.poll();
            }

            system.processBooking(request);
        }
    }
}

public class BookMyStayApp {

    public static void main(String[] args) throws InterruptedException {

        BookingSystem system = new BookingSystem();

        Queue<BookingRequest> bookingQueue = new LinkedList<>();

        bookingQueue.add(new BookingRequest("Abhi", "Single"));
        bookingQueue.add(new BookingRequest("Vamanthi", "Double"));
        bookingQueue.add(new BookingRequest("Kural", "Suite"));
        bookingQueue.add(new BookingRequest("Subha", "Single"));

        System.out.println("Concurrent Booking Simulation");

        BookingProcessor t1 = new BookingProcessor(system, bookingQueue);
        BookingProcessor t2 = new BookingProcessor(system, bookingQueue);

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        system.displayInventory();
    }
}