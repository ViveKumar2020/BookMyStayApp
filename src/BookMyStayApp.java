import java.util.*;

class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
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
}

class BookingSystem {

    private Map<String, Integer> roomInventory = new HashMap<>();
    private Map<String, Reservation> confirmedBookings = new HashMap<>();
    private Stack<String> rollbackHistory = new Stack<>();

    public BookingSystem() {
        roomInventory.put("Single", 5);
        roomInventory.put("Double", 3);
        roomInventory.put("Suite", 2);
    }

    public void confirmBooking(String reservationId, String guestName, String roomType) {

        if (!roomInventory.containsKey(roomType) || roomInventory.get(roomType) <= 0) {
            System.out.println("Booking failed. Room not available.");
            return;
        }

        Reservation reservation = new Reservation(reservationId, guestName, roomType);
        confirmedBookings.put(reservationId, reservation);

        roomInventory.put(roomType, roomInventory.get(roomType) - 1);

        System.out.println("Booking confirmed: " + reservationId + " (" + roomType + ")");
    }

    public void cancelBooking(String reservationId) {

        if (!confirmedBookings.containsKey(reservationId)) {
            System.out.println("Cancellation failed. Reservation not found.");
            return;
        }

        Reservation reservation = confirmedBookings.remove(reservationId);
        String roomType = reservation.getRoomType();

        rollbackHistory.push(reservationId);

        roomInventory.put(roomType, roomInventory.get(roomType) + 1);

        System.out.println("\nBooking Cancellation");
        System.out.println("Booking cancelled successfully. Inventory restored for room type: " + roomType);
    }

    public void showRollbackHistory() {

        System.out.println("\nRollback History (Most Recent First):");

        while (!rollbackHistory.isEmpty()) {
            System.out.println("Released Reservation ID: " + rollbackHistory.pop());
        }
    }

    public void showInventory(String roomType) {
        System.out.println("\nUpdated " + roomType + " Room Availability: " + roomInventory.get(roomType));
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        BookingSystem system = new BookingSystem();

        system.confirmBooking("Single-1", "Rahul", "Single");

        system.cancelBooking("Single-1");

        system.showRollbackHistory();

        system.showInventory("Single");
    }
}