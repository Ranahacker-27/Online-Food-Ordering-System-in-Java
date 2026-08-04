
package FoodOrderingSystemPackage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a customer's food order.
 * Demonstrates composition, ArrayList usage, and polymorphism via calculateTotal().
 */
public class Order {

    private static int orderCount = 0;

    private final int orderId;
    private Customer customer;
    private final List<MenuItem> menuItems;
    private static final double TAX_RATE = 0.15; // 15% VAT

    /** Default constructor — creates an empty order with a unique ID. */
    public Order() {
        this.orderId = generateId();
        this.menuItems = new ArrayList<>();
    }

    /**
     * Parameterized constructor.
     *
     * @param customer The customer placing the order.
     * @param items    Initial list of menu items (may be null or empty).
     */
    public Order(Customer customer, List<MenuItem> items) {
        this();
        this.customer = customer;
        if (items != null) {
            this.menuItems.addAll(items);
        }
    }

    private static int generateId() {
        return ++orderCount;
    }

    /** @return Unique order ID. */
    public int getOrderId() { return orderId; }

    /** @return The customer associated with this order. */
    public Customer getCustomer() { return customer; }

    /** @param customer The customer to associate with this order. */
    public void setCustomer(Customer customer) { this.customer = customer; }

    /** @return An unmodifiable view of the ordered items. */
    public List<MenuItem> getMenuItems() {
        return Collections.unmodifiableList(menuItems);
    }

    /**
     * Adds a menu item to the order.
     * @param item The item to add (ignored if null).
     */
    public void addItem(MenuItem item) {
        if (item != null) {
            menuItems.add(item);
        }
    }

    /**
     * Removes a menu item from the order by its list index.
     * @param index Zero-based index of the item to remove.
     * @throws IndexOutOfBoundsException if index is out of range.
     */
    public void removeItem(int index) {
        menuItems.remove(index);
    }

    /** Removes all items from the order. */
    public void clearItems() {
        menuItems.clear();
    }

    /** @return {@code true} if the order contains no items. */
    public boolean isEmpty() {
        return menuItems.isEmpty();
    }

    /**
     * Calculates the subtotal (before tax).
     * Uses polymorphism — calls the correct {@code calculateTotal()} for each subclass.
     * @return subtotal in SR.
     */
    public double getSubtotal() {
        double subtotal = 0;
        for (MenuItem item : menuItems) {
            subtotal += item.calculateTotal();
        }
        return subtotal;
    }

    /**
     * Calculates the 15% VAT amount.
     * @return tax amount in SR.
     */
    public double getTax() {
        return getSubtotal() * TAX_RATE;
    }

    /**
     * Calculates the final total including tax.
     * @return total in SR.
     */
    public double calculateTotal() {
        return getSubtotal() + getTax();
    }

    /**
     * Generates a formatted order summary.
     * @return Full invoice string.
     * @throws InvalidInputException if no customer is set (checked by caller).
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("========================================\n");
        sb.append(String.format("  ORDER #%d\n", orderId));
        sb.append("========================================\n");
        sb.append("Customer: ").append(customer != null ? customer.toString() : "N/A").append("\n");
        sb.append("----------------------------------------\n");
        sb.append("Items:\n");
        if (menuItems.isEmpty()) {
            sb.append("  (no items)\n");
        } else {
            for (int i = 0; i < menuItems.size(); i++) {
                sb.append(String.format("  %d. %s\n", i + 1, menuItems.get(i).toString()));
            }
        }
        sb.append("----------------------------------------\n");
        sb.append(String.format("  Subtotal  : %8.2f SR\n", getSubtotal()));
        sb.append(String.format("  Tax (15%%) : %8.2f SR\n", getTax()));
        sb.append(String.format("  TOTAL     : %8.2f SR\n", calculateTotal()));
        sb.append("========================================");
        return sb.toString();
    }
}