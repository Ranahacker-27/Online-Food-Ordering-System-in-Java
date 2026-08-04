
package FoodOrderingSystemPackage;

/**
 * Represents a drink menu item.
 * Extends {@link MenuItem} to demonstrate inheritance.
 */
public class DrinkItem extends MenuItem {

    /** Valid size options. */
    public enum Size { SMALL, MEDIUM, LARGE }

    private Size size;

    /** Default constructor. */
    public DrinkItem() {
        super();
    }

    /**
     * Parameterized constructor for a Drink Item.
     *
     * @param name     Drink name.
     * @param price    Price in SR (must be >= 0).
     * @param calories Calorie count (must be >= 0).
     * @param size     Size of the drink ({@link Size}).
     * @throws InvalidInputException if any base-class validation fails or size is null.
     */
    public DrinkItem(String name, double price, int calories, Size size) throws InvalidInputException {
        super(name, price, calories);
        setSize(size);
    }

    /** @return The drink size. */
    public Size getSize() { return size; }

    /**
     * Sets the drink size.
     * @param size A non-null {@link Size} value.
     * @throws InvalidInputException if size is null.
     */
    public void setSize(Size size) throws InvalidInputException {
        if (size == null) {
            throw new InvalidInputException("Drink size cannot be null.");
        }
        this.size = size;
    }

    /**
     * Returns the price of the drink as its total.
     * @return price in SR.
     */
    @Override
    public double calculateTotal() {
        return getPrice();
    }

    /** @return Human-readable summary including drink size. */
    @Override
    public String toString() {
        return super.toString() + " | Size: " + size;
    }
}
