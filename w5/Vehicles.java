// Interface Vehicle
interface Vehicle {
    void start();
    void stop();
}

// Class Car implementing Vehicle
class Car implements Vehicle {
    @Override
    public void start() {
        System.out.println("Car is starting...");
    }

    @Override
    public void stop() {
        System.out.println("Car is stopping...");
    }
}

// Class Bicycle implementing Vehicle
class Bicycle implements Vehicle {
    @Override
    public void start() {
        System.out.println("Bicycle is starting...");
    }

    @Override
    public void stop() {
        System.out.println("Bicycle is stopping...");
    }
}

// Main class to test
public class Vehicles {
    public static void main(String[] args) {
        Vehicle car = new Car();
        Vehicle bicycle = new Bicycle();

        car.start();
        car.stop();

        bicycle.start();
        bicycle.stop();
    }
}
