package FoodOrderingSystemPackage;

/**
 * Represents an appetizer menu item.
 * Extends {@link MenuItem} to demonstrate inheritance.
 */
public class Appetizer extends MenuItem {

    private int spicyLevel;   // 0 = mild, 5 = very spicy
    private boolean isShareable;

    /** Default constructor. */
    public Appetizer() {
        super();
    }

    /**
     * Parameterized constructor for an Appetizer.
     *
     * @param name        Name of the appetizer.
     * @param price       Price in SR (must be >= 0).
     * @param calories    Calorie count (must be >= 0).
     * @param spicyLevel  Spice level between 0 (mild) and 5 (very spicy).
     * @param isShareable Whether the item is intended for sharing.
     * @throws InvalidInputException if any base-class validation fails or spicyLevel is out of range.
     */
    public Appetizer(String name, double price, int calories,
                     int spicyLevel, boolean isShareable) throws InvalidInputException {
        super(name, price, calories);
        setSpicyLevel(spicyLevel);
        this.isShareable = isShareable;
    }

    /** @return Spice level (0–5). */
    public int getSpicyLevel() { return spicyLevel; }

    /**
     * Sets the spice level.
     * @param spicyLevel Value between 0 and 5 inclusive.
     * @throws InvalidInputException if value is outside the valid range.
     */
    public void setSpicyLevel(int spicyLevel) throws InvalidInputException {
        if (spicyLevel < 0 || spicyLevel > 5) {
            throw new InvalidInputException("Spicy level must be between 0 and 5. Got: " + spicyLevel);
        }
        this.spicyLevel = spicyLevel;
    }

    /** @return {@code true} if this appetizer is meant to be shared. */
    public boolean isShareable() { return isShareable; }

    /** @param isShareable Set whether this appetizer is shareable. */
    public void setShareable(boolean isShareable) { this.isShareable = isShareable; }

    /**
     * Returns the price of the appetizer as its total.
     * @return price in SR.
     */
    @Override
    public double calculateTotal() {
        return getPrice();
    }

    /** @return Human-readable summary including spice level and shareability. */
    @Override
    public String toString() {
        return super.toString()
                + String.format(" | Spicy: %d/5 | Shareable: %s", spicyLevel, isShareable ? "Yes" : "No");
    }
}