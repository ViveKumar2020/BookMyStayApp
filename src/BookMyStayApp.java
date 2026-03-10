import java.util.LinkedList;
import java.util.Queue;

public class BookMyStayApp {

    static class Reservation {
        String guestName;
        String roomType;

        Reservation(String guestName, String roomType) {
            this.guestName = guestName;
            this.roomType = roomType;
        }
    }

    static class BookingRequestQueue {

        private Queue<Reservation> queue = new LinkedList<>();

        public void addRequest(String guestName, String roomType) {
            queue.add(new Reservation(guestName, roomType));
        }

        public void processRequests() {

            System.out.println("Booking Request Queue");

            while (!queue.isEmpty()) {
                Reservation r = queue.poll();
                System.out.println(
                        "Processing booking for Guest: " + r.guestName +
                                ", Room Type: " + r.roomType
                );
            }
        }
    }

    public static void main(String[] args) {

        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        bookingQueue.addRequest("Abhi", "Single");
        bookingQueue.addRequest("Subha", "Double");
        bookingQueue.addRequest("Vanmathi", "Suite");

        bookingQueue.processRequests();
    }
}