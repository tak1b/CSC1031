public class SmartHome {
    public static void main(String[] args) {
        // Testing SmartWashingMachine
        SmartWashingMachine swm = new SmartWashingMachine("Samsung", 1500, 7, true);
        swm.turnOn();
        swm.washClothes();
        swm.connectToWiFi();
        System.out.println("WiFi Enabled: " + swm.hasWiFi());

        // Testing WashingMachine
        WashingMachine wm = new WashingMachine("LG", 1300, 6);
        wm.turnOn();
        wm.washClothes();
        System.out.println("Drum Size: " + wm.getDrumSize() + " kg");

        // Testing Refrigerator
        Refrigerator fridge = new Refrigerator("Whirlpool", 800, 4.0);
        fridge.turnOn();
        fridge.coolItems();
        fridge.setTemperature(2.5);
        System.out.println("Current Temperature: " + fridge.getTemperature() + "°C");
    }
}

// Base Class: Appliance
class Appliance {
    private String brand;
    private double powerConsumption;

    public Appliance(String brand, double powerConsumption) {
        this.brand = brand;
        this.powerConsumption = powerConsumption;
    }

    public void turnOn() {
        System.out.println("Turning on " + brand + " appliance");
    }

    public void turnOff() {
        System.out.println("Turning off " + brand + " appliance");
    }

    public String getBrand() {
        return brand;
    }

    public double getPowerConsumption() {
        return powerConsumption;
    }
}

// First-Level Inherited Class: WashingMachine
class WashingMachine extends Appliance {
    private int drumSize;

    public WashingMachine(String brand, double powerConsumption, int drumSize) {
        super(brand, powerConsumption);
        this.drumSize = drumSize;
    }

    public void washClothes() {
        System.out.println("Washing clothes in a " + getBrand() + " washing machine");
    }

    public int getDrumSize() {
        return drumSize;
    }
}

// First-Level Inherited Class: Refrigerator
class Refrigerator extends Appliance {
    private double temperature;

    public Refrigerator(String brand, double powerConsumption, double temperature) {
        super(brand, powerConsumption);
        this.temperature = temperature;
    }

    public void coolItems() {
        System.out.println("Cooling items in a " + getBrand() + " refrigerator at " + temperature + "°C");
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getTemperature() {
        return temperature;
    }
}

// Second-Level Inherited Class: SmartWashingMachine (Multilevel Inheritance)
class SmartWashingMachine extends WashingMachine {
    private boolean hasWiFi;

    public SmartWashingMachine(String brand, double powerConsumption, int drumSize, boolean hasWiFi) {
        super(brand, powerConsumption, drumSize);
        this.hasWiFi = hasWiFi;
    }

    public void connectToWiFi() {
        System.out.println("Smart Washing Machine connected to WiFi");
    }

    public boolean hasWiFi() {
        return hasWiFi;
    }
}
