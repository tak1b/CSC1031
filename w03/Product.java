import java.util.ArrayList;
import java.util.List;

public class Product {
    private String productName;
    private long price;
    private boolean inStock;
    private List<String> tags;

    // Default constructor
    public Product() {
        this.productName = "Unknown";
        this.price = 0;
        this.inStock = false;
        this.tags = new ArrayList<>();
    }

    // Constructor with productName
    public Product(String productName) {
        this();
        this.productName = productName != null ? productName : "Unknown"; // Handle null productName
    }

    // Constructor with productName and price
    public Product(String productName, long price) {
        this(productName);
        this.price = price >= 0 ? price : 0; // Handle negative price
    }

    // Constructor with productName, price, and inStock
    public Product(String productName, long price, boolean inStock) {
        this(productName, price);
        this.inStock = inStock;
    }

    // Constructor with productName, price, and tags
    public Product(String productName, long price, List<String> tags) {
        this(productName, price);
        this.tags = tags != null ? new ArrayList<>(tags) : new ArrayList<>(); // Handle null tags
    }

    // Constructor with all fields
    public Product(String productName, long price, boolean inStock, List<String> tags) {
        this.productName = productName != null ? productName : "Unknown"; // Handle null productName
        this.price = price >= 0 ? price : 0; // Handle negative price
        this.inStock = inStock;
        this.tags = tags != null ? new ArrayList<>(tags) : new ArrayList<>(); // Handle null tags
    }

    // Deep copy constructor
    public Product(Product other) {
        this.productName = other.productName;
        this.price = other.price;
        this.inStock = other.inStock;
        this.tags = other.tags != null ? new ArrayList<>(other.tags) : new ArrayList<>(); // Handle null tags
    }

    // Getters and Setters
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName != null ? productName : "Unknown"; // Handle null productName
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price >= 0 ? price : 0; // Handle negative price
    }

    public boolean isInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }

    // Encapsulation for tags
    public List<String> getTags() {
        return new ArrayList<>(tags); // Return a copy to preserve encapsulation
    }

    public void setTags(List<String> tags) {
        this.tags = tags != null ? new ArrayList<>(tags) : new ArrayList<>(); // Handle null tags
    }

    public void addTag(String tag) {
        if (tag != null) {
            this.tags.add(tag);
        }
    }

    // Override toString method
    @Override
    public String toString() {
        return "Product{" +
                "productName='" + productName + '\'' +
                ", price=" + price +
                ", inStock=" + inStock +
                ", tags=" + tags +
                '}';
    }
}