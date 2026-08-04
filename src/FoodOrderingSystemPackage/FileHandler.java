
package FoodOrderingSystemPackage;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Handles file I/O operations for the Food Ordering System.
 * Provides static utility methods for saving order invoices.
 */
public class FileHandler {

    /** Utility class — no instantiation needed. */
    private FileHandler() {}

    /**
     * Appends an order invoice to the specified file.
     *
     * @param order    The order to save.
     * @param filename Path to the output file (will be created if it doesn't exist).
     * @throws InvalidInputException if the order is null or has no customer.
     */
    public static void saveOrderToFile(Order order, String filename) throws InvalidInputException {
        if (order == null) {
            throw new InvalidInputException("Cannot save a null order.");
        }
        if (order.getCustomer() == null) {
            throw new InvalidInputException("Order must have a customer before saving.");
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(filename, true))) {
            writer.println(order.toString());
            writer.println(); // blank line between orders
        } catch (IOException e) {
            System.err.println("Error saving order to file: " + e.getMessage());
        }
    }
}
