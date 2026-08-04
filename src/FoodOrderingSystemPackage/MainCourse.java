package FoodOrderingSystemPackage;

/**
 * Represents a main-course menu item.
 * Extends {@link MenuItem} to demonstrate inheritance and polymorphism.
 */
public class MainCourse extends MenuItem {

    private int cookingTime;    // minutes
    private String cuisineType;

    /** Default constructor. */
    public MainCourse() {
        super();
    }

    /**
     * Parameterized constructor for a Main Course.
     *
     * @param name        Name of the dish.
     * @param price       Price in SR (must be >= 0).
     * @param calories    Calorie count (must be >= 0).
     * @param cookingTime Preparation time in minutes (must be > 0).
     * @param cuisineType Type of cuisine (e.g., Italian, Arabic).
     * @throws InvalidInputException if any base-class validation fails,
     *                               cookingTime <= 0, or cuisineType is blank.
     */
    public MainCourse(String name, double price, int calories,
                      int cookingTime, String cuisineType) throws InvalidInputException {
        super(name, price, calories);
        setCookingTime(cookingTime);
        setCuisineType(cuisineType);
    }

    /** @return Cooking time in minutes. */
    public int getCookingTime() { return cookingTime; }

    /**
     * Sets the cooking time.
     * @param cookingTime Must be greater than 0.
     * @throws InvalidInputException if cookingTime is <= 0.
     */
    public void setCookingTime(int cookingTime) throws InvalidInputException {
        if (cookingTime <= 0) {
            throw new InvalidInputException("Cooking time must be greater than 0. Got: " + cookingTime);
        }
        this.cookingTime = cookingTime;
    }

    /** @return Cuisine type (e.g., Italian, Arabic). */
    public String getCuisineType() { return cuisineType; }

    /**
     * Sets the cuisine type.
     * @param cuisineType Non-blank string.
     * @throws InvalidInputException if cuisineType is null or blank.
     */
    public void setCuisineType(String cuisineType) throws InvalidInputException {
        if (cuisineType == null || cuisineType.trim().isEmpty()) {
            throw new InvalidInputException("Cuisine type cannot be empty.");
        }
        this.cuisineType = cuisineType.trim();
    }

    /**
     * Returns the price of the main course as its total.
     * @return price in SR.
     */
    @Override
    public double calculateTotal() {
        return getPrice();
    }

    /** @return Human-readable summary including cooking time and cuisine type. */
    @Override
    public String toString() {
        return super.toString()
                + String.format(" | Cook: %d min | Cuisine: %s", cookingTime, cuisineType);
    }
}