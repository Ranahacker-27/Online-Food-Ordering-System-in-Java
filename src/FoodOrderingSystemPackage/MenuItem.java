package FoodOrderingSystemPackage;

/**
 * Abstract base class for all menu items.
 * Demonstrates abstraction, encapsulation, and polymorphism.
 */
public abstract class MenuItem implements Orderable {

    private static int itemCount = 0;

    private int id;
    private String name;
    private double price;
    private int calories;

    /** Default constructor — auto-assigns a unique ID. */
    public MenuItem() {
        this.id = generateId();
    }

    /**
     * Parameterized constructor.
     *
     * @param name     Name of the menu item.
     * @param price    Price in SR (must be >= 0).
     * @param calories Calorie count (must be >= 0).
     * @throws InvalidInputException if name is blank, price is negative, or calories is negative.
     */
    public MenuItem(String name, double price, int calories) throws InvalidInputException {
        this();
        setName(name);
        setPrice(price);
        setCalories(calories);
    }

    private static int generateId() {
        return ++itemCount;
    }

    /** @return Auto-generated unique item ID. */
    public int getId() { return id; }

    /** @return Name of the item. */
    public String getName() { return name; }

    /** @return Price of the item in SR. */
    public double getPrice() { return price; }

    /** @return Calorie count of the item. */
    public int getCalories() { return calories; }

    /**
     * Sets the item name.
     * @param name Non-blank item name.
     * @throws InvalidInputException if name is null or blank.
     */
    public void setName(String name) throws InvalidInputException {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidInputException("Item name cannot be empty.");
        }
        this.name = name.trim();
    }

    /**
     * Sets the item price.
     * @param price Price in SR; must be >= 0.
     * @throws InvalidInputException if price is negative.
     */
    public void setPrice(double price) throws InvalidInputException {
        if (price < 0) {
            throw new InvalidInputException("Price cannot be negative. Got: " + price);
        }
        this.price = price;
    }

    /**
     * Sets the calorie count.
     * @param calories Must be >= 0.
     * @throws InvalidInputException if calories is negative.
     */
    public void setCalories(int calories) throws InvalidInputException {
        if (calories < 0) {
            throw new InvalidInputException("Calories cannot be negative. Got: " + calories);
        }
        this.calories = calories;
    }

    /**
     * Abstract method — each subclass must provide its own pricing logic.
     * Fulfils polymorphism requirement.
     */
    @Override
    public abstract double calculateTotal();

    /** @return Human-readable summary of the menu item. */
    @Override
    public String toString() {
        return String.format("ID: %d | %-20s | %6.2f SR | %d kcal", id, name, price, calories);
    }
}