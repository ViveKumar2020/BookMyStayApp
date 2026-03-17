import java.util.*;

// Custom Exception for invalid bookings
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// Reservation class
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;
    private int nights;

    public Reservation(String reservationId, String guestName, String roomType, int nights) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.nights = nights;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public int getNights() {
        return nights;
    }

    @Override
    public String toString() {
        return "Reservation ID: " + reservationId +
                ", Guest: " + guestName +
                ", Room: " + roomType +
                ", Nights: " + nights;
    }
}

// Booking system with validation
class BookingSystem {

    private Map<String, Integer> roomInventory = new HashMap<>();

    public BookingSystem() {
        // Initial inventory
        roomInventory.put("Standard", 2);
        roomInventory.put("Deluxe", 2);
        roomInventory.put("Suite", 1);
    }

    // Validate booking request
    public void validateBooking(String roomType, int nights) throws InvalidBookingException {

        if (!roomInventory.containsKey(roomType)) {
            throw new InvalidBookingException("Invalid room type selected.");
        }

        if (nights <= 0) {
            throw new InvalidBookingException("Number of nights must be greater than zero.");
        }

        if (roomInventory.get(roomType) <= 0) {
            throw new InvalidBookingException("Selected room type is fully booked.");
        }
    }

    // Confirm booking
    public Reservation confirmBooking(String reservationId, String guestName, String roomType, int nights)
            throws InvalidBookingException {

        // Fail-fast validation
        validateBooking(roomType, nights);

        // Update inventory safely
        roomInventory.put(roomType, roomInventory.get(roomType) - 1);

        return new Reservation(reservationId, guestName, roomType, nights);
    }

    // Display inventory
    public void displayInventory() {
        System.out.println("\nCurrent Room Inventory:");
        for (String room : roomInventory.keySet()) {
            System.out.println(room + " rooms available: " + roomInventory.get(room));
        }
    }
}

// Main Application
public class BookMyStayApp {

    public static void main(String[] args) {

        BookingSystem system = new BookingSystem();

        system.displayInventory();

        try {

            // Valid booking
            Reservation r1 = system.confirmBooking("RES201", "Amit", "Deluxe", 2);
            System.out.println("\nBooking Successful:");
            System.out.println(r1);

            // Invalid room type
            Reservation r2 = system.confirmBooking("RES202", "Riya", "Premium", 1);
            System.out.println(r2);

        } catch (InvalidBookingException e) {

            // Graceful error handling
            System.out.println("\nBooking Failed: " + e.getMessage());
        }

        system.displayInventory();
    }
}