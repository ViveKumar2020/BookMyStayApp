import java.util.*;

class Reservation {

    private String reservationId;
    private String guestName;
    private String roomType;
    private int nights;
    private double price;

    public Reservation(String reservationId, String guestName, String roomType, int nights, double price) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.nights = nights;
        this.price = price;
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

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Reservation ID: " + reservationId +
                ", Guest: " + guestName +
                ", Room: " + roomType +
                ", Nights: " + nights +
                ", Price: ₹" + price;
    }
}


class BookingHistory {

    private List<Reservation> reservations = new ArrayList<>();

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
        System.out.println("Reservation confirmed and added to history: " + reservation.getReservationId());
    }

    public List<Reservation> getReservations() {
        return reservations;
    }
}


class BookingReportService {

    public void displayAllBookings(List<Reservation> reservations) {

        if (reservations.isEmpty()) {
            System.out.println("No booking history available.");
            return;
        }

        System.out.println("\nBooking History:");

        for (Reservation r : reservations) {
            System.out.println(r);
        }
    }

    public void generateSummaryReport(List<Reservation> reservations) {

        int totalBookings = reservations.size();
        double totalRevenue = 0;

        for (Reservation r : reservations) {
            totalRevenue += r.getPrice();
        }

        System.out.println("\nBooking Summary Report");
        System.out.println("----------------------");
        System.out.println("Total Bookings: " + totalBookings);
        System.out.println("Total Revenue: ₹" + totalRevenue);
    }
}


public class BookMyStayApp {

    public static void main(String[] args) {

        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        Reservation r1 = new Reservation("RES101", "Amit", "Deluxe", 2, 4000);
        Reservation r2 = new Reservation("RES102", "Riya", "Suite", 3, 9000);
        Reservation r3 = new Reservation("RES103", "Rahul", "Standard", 1, 1500);

        history.addReservation(r1);
        history.addReservation(r2);
        history.addReservation(r3);

        reportService.displayAllBookings(history.getReservations());

        reportService.generateSummaryReport(history.getReservations());
    }
}