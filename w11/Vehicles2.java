// Abstract class Vehicle with a "has-a" Engine.
abstract class Vehicle {
    String brand;
    Engine engine;  // has-a relationship

    public Vehicle(String brand, Engine engine) {
        this.brand = brand;
        this.engine = engine;
    }

    // Abstract method that must be implemented by subclasses.
    public abstract void startEngine();
}

// Concrete class Car extends Vehicle.
class Car extends Vehicle {
    int numDoors;

    public Car(String brand, int numDoors, Engine engine) {
        super(brand, engine);
        this.numDoors = numDoors;
    }

    // Implements startEngine() for Car.
    public void startEngine() {
        System.out.println("Starting car with " + engine.horsePower + " horsepowers");
    }
}

// Concrete class Bike extends Vehicle.
class Bike extends Vehicle {
    boolean hasCarrier;

    public Bike(String brand, boolean hasCarrier, Engine engine) {
        super(brand, engine);
        this.hasCarrier = hasCarrier;
    }

    // Implements startEngine() for Bike.
    public void startEngine() {
        System.out.println("Starting bike with " + engine.horsePower + " horsepowers");
    }
}

// Concrete class ElectricCar extends Car.
class ElectricCar extends Car {
    int batteryCapacity;

    public ElectricCar(String brand, int numDoors, int batteryCapacity, Engine engine) {
        super(brand, numDoors, engine);
        this.batteryCapacity = batteryCapacity;
    }

    // Overrides startEngine() for ElectricCar.
    public void startEngine() {
        System.out.println("Starting electric car silently with " + engine.horsePower + " horsepowers");
    }
}

// Engine class.
class Engine {
    int horsePower;

    public Engine(int horsePower) {
        this.horsePower = horsePower;
    }
}

// Main class containing the main method.
public class Vehicles2 {
    public static void main(String[] args) {
        Engine carEngine = new Engine(150);
        Car myCar = new Car("Toyota", 4, carEngine);
        myCar.startEngine();

        Engine bikeEngine = new Engine(20);
        Bike myBike = new Bike("Yamaha", true, bikeEngine);
        myBike.startEngine();

        // throw this in somewhere
        Engine electricCarEngine = new Engine(200);
        ElectricCar tesla = new ElectricCar("Tesla", 4, 75, electricCarEngine);
        tesla.startEngine();
    }
}
