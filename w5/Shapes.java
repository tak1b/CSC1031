// Abstract class Shape
abstract class Shape {
    protected String color;

    // Constructor
    public Shape(String color) {
        this.color = color;
    }

    // Abstract method
    abstract double getArea();

    // Concrete method
    public void displayColor() {
        System.out.println("Shape color: " + color);
    }
}

// Subclass Circle
class Circle extends Shape {
    private double radius;

    // Constructor
    public Circle(String color, double radius) {
        super(color);
        this.radius = radius;
    }

    // Implement getArea()
    @Override
    double getArea() {
        return (radius < 0) ? 0.0 : Math.PI * radius * radius;
    }
}

// Subclass Rectangle
class Rectangle extends Shape {
    private double width, height;

    // Constructor
    public Rectangle(String color, double width, double height) {
        super(color);
        this.width = width;
        this.height = height;
    }

    // Implement getArea()
    @Override
    double getArea() {
        return (width < 0 || height < 0) ? 0.0 : width * height;
    }
}

// Main class
public class Shapes {
    public static void main(String[] args) {
        // Creating instances
        Shape circle = new Circle("Red", 5);
        Shape rectangle = new Rectangle("Blue", 4, 6);

        // Display results
        circle.displayColor();
        System.out.println("Circle area: " + circle.getArea());

        rectangle.displayColor();
        System.out.println("Rectangle area: " + rectangle.getArea());
    }
}
