package FoodOrderingSystemPackage;

/**
 * Custom exception for logical errors and invalid inputs in the Food Ordering System.
 * Extends {@link Exception} and optionally carries an error code for programmatic handling.
 */
public class InvalidInputException extends Exception {

    /** Optional error code to categorise the exception (0 = unspecified). */
    private final int errorCode;

    /**
     * Constructor with message only.
     * @param message Human-readable description of the error.
     */
    public InvalidInputException(String message) {
        super(message);
        this.errorCode = 0;
    }

    /**
     * Constructor with message and error code.
     * @param message   Human-readable description of the error.
     * @param errorCode Numeric code identifying the error category.
     */
    public InvalidInputException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    /**
     * Returns the error code associated with this exception.
     * @return error code, or 0 if none was specified.
     */
    public int getErrorCode() {
        return errorCode;
    }
}