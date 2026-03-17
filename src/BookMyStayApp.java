import java.util.*;

class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

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

class BookingSystem {

    private Map<String, Integer> roomInventory = new HashMap<>();

    public BookingSystem() {
        
        roomInventory.put("Standard", 2);
        roomInventory.put("Deluxe", 2);
        roomInventory.put("Suite", 1);
    }

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

    public Reservation confirmBooking(String reservationId, String guestName, String roomType, int nights)
            throws InvalidBookingException {

        validateBooking(roomType, nights);

        roomInventory.put(roomType, roomInventory.get(roomType) - 1);

        return new Reservation(reservationId, guestName, roomType, nights);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Room Inventory:");
        for (String room : roomInventory.keySet()) {
            System.out.println(room + " rooms available: " + roomInventory.get(room));
        }
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        BookingSystem system = new BookingSystem();

        system.displayInventory();

        try {

            Reservation r1 = system.confirmBooking("RES201", "Amit", "Deluxe", 2);
            System.out.println("\nBooking Successful:");
            System.out.println(r1);

            Reservation r2 = system.confirmBooking("RES202", "Riya", "Premium", 1);
            System.out.println(r2);

        } catch (InvalidBookingException e) {

            System.out.println("\nBooking Failed: " + e.getMessage());
        }

        system.displayInventory();
    }
}
