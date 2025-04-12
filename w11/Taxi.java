import java.util.LinkedList;
import java.util.Queue;

// Passenger class
class Passenger {
    private String name;
    private String destination;  // Destination requested by the passenger.

    public Passenger(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getDestination() {
        return destination;
    }

    // Passenger requests a ride to a destination.
    public void requestRide(String destination, DispatchCenter dispatchCenter) {
        this.destination = destination;
        System.out.println("Passenger " + name + " requested a ride to " + destination + ".");
        dispatchCenter.addPassengerRequest(this);
    }
}

// DispatchCenter manages queues of waiting passengers and available taxis.
class DispatchCenter {
    private Queue<Passenger> passengerQueue;
    private Queue<Taxi> taxiQueue;

    public DispatchCenter() {
        passengerQueue = new LinkedList<>();
        taxiQueue = new LinkedList<>();
    }

    // Registers a taxi with the dispatch center by setting its reference.
    public void registerTaxi(Taxi taxi) {
        taxi.setDispatchCenter(this);
    }

    // Adds a passenger request to the queue and attempts to assign a taxi.
    public void addPassengerRequest(Passenger passenger) {
        passengerQueue.add(passenger);
        tryToAssignRide();
    }

    // Adds an available taxi to the queue and attempts to assign a ride.
    public void addAvailableTaxi(Taxi taxi) {
        taxiQueue.add(taxi);
        tryToAssignRide();
    }

    // If both queues are non-empty, assigns a taxi to a waiting passenger.
    private void tryToAssignRide() {
        while (!passengerQueue.isEmpty() && !taxiQueue.isEmpty()) {
            Passenger passenger = passengerQueue.poll();
            Taxi taxi = taxiQueue.poll();
            taxi.setAssignment(passenger, passenger.getDestination());
            System.out.println("Dispatch assigned Taxi " + taxi.getTaxiId() + " to passenger " + passenger.getName() + ".");
        }
    }

    // When a taxi rejects a ride, reassign the same passenger to the next available taxi.
    public void reassignPassenger(Passenger passenger) {
        if (!taxiQueue.isEmpty()) {
            Taxi taxi = taxiQueue.poll();
            taxi.setAssignment(passenger, passenger.getDestination());
            System.out.println("Dispatch assigned Taxi " + taxi.getTaxiId() + " to passenger " + passenger.getName() + ".");
        } else {
            ((LinkedList<Passenger>) passengerQueue).addFirst(passenger);
        }
    }
}

// Public Taxi class – file name must be Taxi.java.
// Represents a taxi driver.
public class Taxi {
    private String taxiId;
    private DispatchCenter dispatchCenter;
    // Holds the current assignment details.
    private Passenger assignedPassenger;
    private String assignedDestination;

    public Taxi(String taxiId) {
        this.taxiId = taxiId;
    }

    public String getTaxiId() {
        return taxiId;
    }

    // Set the reference to the central DispatchCenter.
    public void setDispatchCenter(DispatchCenter dispatchCenter) {
        this.dispatchCenter = dispatchCenter;
    }

    // When a taxi becomes available, it notifies the DispatchCenter.
    public void setAvailable(boolean available) {
        if (available) {
            System.out.println("Taxi " + taxiId + " is now available.");
            dispatchCenter.addAvailableTaxi(this);
        }
    }

    // Called by the DispatchCenter to assign a ride.
    public void setAssignment(Passenger passenger, String destination) {
        this.assignedPassenger = passenger;
        this.assignedDestination = destination;
    }

    // Taxi responds to the current ride assignment.
    public void respondToRide(boolean accept) {
        if (assignedPassenger == null || assignedDestination == null) {
            return; // No assignment to respond to.
        }
        if (accept) {
            System.out.println("Taxi " + taxiId + " accepted the ride to " + assignedDestination + ".");
        } else {
            System.out.println("Taxi " + taxiId + " rejected the ride to " + assignedDestination + ". Searching for another taxi...");
            // Place this taxi at the end of the available taxi queue.
            dispatchCenter.addAvailableTaxi(this);
            // Reassign the passenger.
            dispatchCenter.reassignPassenger(assignedPassenger);
        }
        // Clear the assignment.
        assignedPassenger = null;
        assignedDestination = null;
    }

    // Main method – ensures the file compiles and runs.
    public static void main(String[] args) {
        DispatchCenter dispatchCenter = new DispatchCenter();

        Passenger alice = new Passenger("Alice");
        Passenger bob = new Passenger("Bob");

        Taxi taxi1 = new Taxi("Taxi-01");
        Taxi taxi2 = new Taxi("Taxi-02");
        Taxi taxi3 = new Taxi("Taxi-03");

        // Register taxis with the dispatch center.
        dispatchCenter.registerTaxi(taxi1);
        dispatchCenter.registerTaxi(taxi2);
        dispatchCenter.registerTaxi(taxi3);

        // Passengers request rides.
        alice.requestRide("Airport", dispatchCenter);
        bob.requestRide("Downtown", dispatchCenter);

        // Taxis become available.
        taxi1.setAvailable(true);
        taxi2.setAvailable(true);
        taxi3.setAvailable(true);

        // Taxis respond to their assignments.
        taxi1.respondToRide(true);  // Taxi-01 accepts ride.
        taxi2.respondToRide(false); // Taxi-02 rejects ride.
        taxi3.respondToRide(true);  // Taxi-03 accepts the reassigned ride.
    }
}
