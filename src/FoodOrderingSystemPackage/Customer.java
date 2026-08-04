package FoodOrderingSystemPackage;

/**
 * Represents a customer in the Food Ordering System.
 * Demonstrates encapsulation with validated setters.
 */
public class Customer {

    private static int customerCount = 0;

    private int customerId;
    private String name;
    private String email;
    private String phone;

    /** Default constructor — auto-assigns a unique customer ID. */
    public Customer() {
        this.customerId = generateId();
    }

    /**
     * Parameterized constructor.
     *
     * @param name  Full name of the customer (non-blank).
     * @param email Contact email (must contain '@').
     * @param phone Contact phone number (digits, spaces, +, - allowed).
     * @throws InvalidInputException if any field fails validation.
     */
    public Customer(String name, String email, String phone) throws InvalidInputException {
        this();
        setName(name);
        setEmail(email);
        setPhone(phone);
    }

    private static int generateId() {
        return ++customerCount;
    }

    /** @return Auto-generated unique customer ID. */
    public int getCustomerId() { return customerId; }

    /** @return Customer's full name. */
    public String getName() { return name; }

    /**
     * Sets the customer's full name.
     * @param name Non-blank string.
     * @throws InvalidInputException if name is null or blank.
     */
    public void setName(String name) throws InvalidInputException {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidInputException("Customer name cannot be empty.");
        }
        this.name = name.trim();
    }

    /** @return Customer's email address. */
    public String getEmail() { return email; }

    /**
     * Sets the customer's email with basic format validation.
     * @param email Must contain '@' and at least one '.'.
     * @throws InvalidInputException if email format is invalid.
     */
    public void setEmail(String email) throws InvalidInputException {
        if (email == null || !email.contains("@") || !email.contains(".")) {
            throw new InvalidInputException("Invalid email address: " + email);
        }
        this.email = email.trim();
    }

    /** @return Customer's phone number. */
    public String getPhone() { return phone; }

    /**
     * Sets the customer's phone number.
     * Accepts digits, spaces, '+', and '-' characters.
     * @param phone Must be at least 7 characters after stripping whitespace.
     * @throws InvalidInputException if phone is too short or contains invalid characters.
     */
    public void setPhone(String phone) throws InvalidInputException {
        if (phone == null || phone.trim().length() < 7) {
            throw new InvalidInputException("Phone number must be at least 7 digits.");
        }
        if (!phone.trim().matches("[0-9 +\\-]+")) {
            throw new InvalidInputException("Phone number contains invalid characters: " + phone);
        }
        this.phone = phone.trim();
    }

    /** @return Formatted customer details. */
    @Override
    public String toString() {
        return String.format("Customer #%d | %s | %s | %s", customerId, name, email, phone);
    }
}

