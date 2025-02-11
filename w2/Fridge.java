import java.util.ArrayList;
import java.util.List;

public class Fridge {

    // Attributes
    private List<String> foodItems; // List to store food items in the fridge
    private int balance; // Tracks the available money for purchasing food

    // Constructor
    public Fridge(int initialBalance) {
        // If the initial balance is negative, print an error and set balance to 0
        if (initialBalance < 0) {
            System.out.println("Error");
            this.balance = 0;
        } else {
            // Otherwise, initialize the balance with the provided value
            this.balance = initialBalance;
        }
        // Initialize the foodItems list as an empty list
        this.foodItems = new ArrayList<>();
    }

    // Method to add food
    public void addFood(String item, int cost) {
        // Check if the item is null, empty, or if the cost is negative
        if (item == null || item.isEmpty() || cost < 0) {
            System.out.println("Error"); // Print error for invalid input
            return; // Exit the method
        }

        // Check if there is enough balance to add the food
        if (cost > balance) {
            System.out.println("Error"); // Print error for insufficient balance
        } else {
            // Add the item to the foodItems list and deduct the cost from balance
            foodItems.add(item);
            balance -= cost;
            System.out.println("Item " + item + " has been added to the fridge.");
        }
    }

    // Method to get food
    public void getFood(String item) {
        // Check if the item is null, empty, or not present in the foodItems list
        if (item == null || item.isEmpty() || !foodItems.contains(item)) {
            System.out.println("Error"); // Print error if item is not found or invalid
            return; // Exit the method
        }

        // Remove the item from the foodItems list
        foodItems.remove(item);
        System.out.println("Item " + item + " has been removed from the fridge.");
    }

    // Method to check fridge status
    public void checkStatus() {
        // Print the list of food items
        System.out.println("Food items:");
        if (foodItems.isEmpty()) {
            // If the fridge is empty, print "(none)"
            System.out.println("(none)");
        } else {
            // Otherwise, print each item in the fridge
            for (String item : foodItems) {
                System.out.println(item);
            }
        }
        // Print the remaining balance
        System.out.println("Balance: €" + balance);
    }

}
