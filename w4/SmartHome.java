public class SmartHome {
    public static void main(String[] args) {
        System.out.println("=== Testing Smart Home System ===");
        
        // Base Appliance test
        Appliance generic = new Appliance("Generic", 500.0);
        generic.turnOn();
        generic.turnOff();
        System.out.println("Brand: " + generic.getBrand());
        System.out.println("Power Consumption: " + generic.getPowerConsumption() + " watts");
        System.out.println("-----------------------------------");
        
        // WashingMachine test
        WashingMachine wm = new WashingMachine("LG", 1300, 6);
        wm.turnOn();
        wm.washClothes();
        System.out.println("Washing Machine Drum Size: " + wm.getDrumSize() + " kg");
        System.out.println("-----------------------------------");
        
        // Refrigerator test
        Refrigerator fridge = new Refrigerator("Whirlpool", 800, 4.0);
        fridge.turnOn();
        fridge.coolItems();
        System.out.println("Initial Refrigerator Temperature: " + fridge.getTemperature() + "°C");
        fridge.setTemperature(2.5);
        System.out.println("Updated Refrigerator Temperature: " + fridge.getTemperature() + "°C");
        System.out.println("-----------------------------------");
        
        // SmartWashingMachine test
        SmartWashingMachine swm = new SmartWashingMachine("Samsung", 1500, 7, true);
        swm.turnOn();
        swm.washClothes();
        swm.connectToWiFi();
        System.out.println("WiFi Enabled: " + swm.hasWiFi());
        System.out.println("-----------------------------------");
        
        // Edge Case Testing
        System.out.println("=== Edge Case Testing ===");
        SmartWashingMachine swmNoWiFi = new SmartWashingMachine("Bosch", 1200, 8, false);
        swmNoWiFi.turnOn();
        swmNoWiFi.washClothes();
        swmNoWiFi.connectToWiFi();
        fridge.setTemperature(-10.0);
        System.out.println("Updated Refrigerator Temperature: " + fridge.getTemperature() + "°C");
        System.out.println("=== All Tests Completed Successfully! ===");
        System.out.println("Program executed successfully.");
    }
    
    // Base Class: Appliance
    public static class Appliance {
        private String brand;
        private double powerConsumption; // in watts
        
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
        
        public void setBrand(String brand) {
            this.brand = brand;
        }
        
        public double getPowerConsumption() {
            return powerConsumption;
        }
        
        public void setPowerConsumption(double powerConsumption) {
            this.powerConsumption = powerConsumption;
        }
    }
    
    // First-Level Inherited Class: WashingMachine
    public static class WashingMachine extends Appliance {
        private int drumSize;  // in kg
        
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
        
        public void setDrumSize(int drumSize) {
            this.drumSize = drumSize;
        }
    }
    
    // First-Level Inherited Class: Refrigerator
    public static class Refrigerator extends Appliance {
        private double temperature; // in °C
        
        public Refrigerator(String brand, double powerConsumption, double temperature) {
            super(brand, powerConsumption);
            this.temperature = temperature;
        }
        
        public void coolItems() {
            System.out.println("Cooling items in a " + getBrand() + " refrigerator at " + temperature + "°C");
        }
        
        public double getTemperature() {
            return temperature;
        }
        
        public void setTemperature(double temperature) {
            this.temperature = temperature;
        }
    }
    
    // Second-Level Inherited Class: SmartWashingMachine (inherits from WashingMachine)
    public static class SmartWashingMachine extends WashingMachine {
        private boolean hasWiFi;
        
        public SmartWashingMachine(String brand, double powerConsumption, int drumSize, boolean hasWiFi) {
            super(brand, powerConsumption, drumSize);
            this.hasWiFi = hasWiFi;
        }
        
        public void connectToWiFi() {
            if (hasWiFi) {
                System.out.println("Smart Washing Machine connected to WiFi");
            } else {
                System.out.println("This washing machine does not support WiFi");
            }
        }
        
        public boolean hasWiFi() {
            return hasWiFi;
        }
        
        public void setHasWiFi(boolean hasWiFi) {
            this.hasWiFi = hasWiFi;
        }
    }
}
