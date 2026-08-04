package FoodOrderingSystemPackage;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * JavaFX GUI for the Food Ordering System.
 * Connects all model classes: Customer, Order, MenuItem subclasses, FileHandler.
 */
public class FoodOrderingGUI extends Application {

    // ── State ────────────────────────────────────────────────────────────────
    private Order currentOrder = new Order();
    private final ObservableList<MenuItem> orderedItems = FXCollections.observableArrayList();

    // ── Shared UI controls ───────────────────────────────────────────────────
    private TextArea statusArea;
    private Label totalLabel;
    private TableView<MenuItem> orderTable;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("PNU Food Ordering System");

        // ── Title ────────────────────────────────────────────────────────────
        Label mainTitle = new Label("🍽  Order Management System");
        mainTitle.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        mainTitle.setTextFill(Color.web("#1a237e"));

        // ── Sections ─────────────────────────────────────────────────────────
        VBox customerBox  = buildCustomerSection();
        VBox menuBox      = buildMenuSection();
        VBox orderBox     = buildOrderSection();
        VBox statusBox    = buildStatusSection();

        // ── Root layout ───────────────────────────────────────────────────────
        VBox root = new VBox(16, mainTitle, customerBox, menuBox, orderBox, statusBox);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.TOP_CENTER);
        root.setStyle("-fx-background-color: #F0F4F8;");

        primaryStage.setScene(new Scene(root, 520, 820));
        primaryStage.show();
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Customer Section
    // ─────────────────────────────────────────────────────────────────────────
    private VBox buildCustomerSection() {
        Label title = sectionTitle("👤  Customer Information");

        TextField nameField  = new TextField(); nameField.setPromptText("Full Name *");
        TextField emailField = new TextField(); emailField.setPromptText("Email Address *");
        TextField phoneField = new TextField(); phoneField.setPromptText("Phone Number *");

        Button registerBtn = primaryButton("Register Customer", "#2E7D32");
        registerBtn.setMaxWidth(Double.MAX_VALUE);

        registerBtn.setOnAction(e -> {
            try {
                Customer c = new Customer(
                        nameField.getText(),
                        emailField.getText(),
                        phoneField.getText());
                currentOrder.setCustomer(c);
                setStatus("✔ Customer registered: " + c.getName(), false);
                nameField.clear(); emailField.clear(); phoneField.clear();
            } catch (InvalidInputException ex) {
                setStatus("⚠ " + ex.getMessage(), true);
            }
        });

        return card(title, nameField, emailField, phoneField, registerBtn);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Menu Section
    // ─────────────────────────────────────────────────────────────────────────
    private VBox buildMenuSection() {
        Label title = sectionTitle("🍴  Menu Selection");

        // Category selector
        ComboBox<String> categoryCombo = new ComboBox<>();
        categoryCombo.getItems().addAll("Appetizer", "Main Course", "Drink");
        categoryCombo.setPromptText("Select category");
        categoryCombo.setMaxWidth(Double.MAX_VALUE);

        // Item selector — populated based on category
        ComboBox<String> itemCombo = new ComboBox<>();
        itemCombo.setPromptText("Select item");
        itemCombo.setMaxWidth(Double.MAX_VALUE);
        itemCombo.setDisable(true);

        // Fill items per category
        categoryCombo.setOnAction(e -> {
            itemCombo.getItems().clear();
            switch (categoryCombo.getValue()) {
                case "Appetizer":
                    itemCombo.getItems().addAll(
                        "Garden Salad – 25 SR",
                        "Mozzarella Sticks – 30 SR",
                        "Spring Rolls – 20 SR");
                    break;
                case "Main Course":
                    itemCombo.getItems().addAll(
                        "Grilled Chicken – 55 SR",
                        "Beef Steak – 80 SR",
                        "Vegetable Pasta – 45 SR");
                    break;
                case "Drink":
                    itemCombo.getItems().addAll(
                        "Fresh Orange Juice – 10 SR",
                        "Water Bottle – 5 SR",
                        "Soft Drink – 8 SR");
                    break;
            }
            itemCombo.setDisable(false);
        });

        Button addBtn = primaryButton("Add to Order", "#1565C0");
        addBtn.setMaxWidth(Double.MAX_VALUE);

        addBtn.setOnAction(e -> {
            String category = categoryCombo.getValue();
            String item     = itemCombo.getValue();
            if (category == null || item == null) {
                setStatus("⚠ Please select a category and an item.", true);
                return;
            }
            try {
                MenuItem menuItem = createMenuItem(category, item);
                currentOrder.addItem(menuItem);
                orderedItems.add(menuItem);
                updateTotal();
                setStatus("✔ Added: " + menuItem.getName(), false);
            } catch (InvalidInputException ex) {
                setStatus("⚠ " + ex.getMessage(), true);
            }
        });

        return card(title, categoryCombo, itemCombo, addBtn);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Order Section (table + remove + save)
    // ─────────────────────────────────────────────────────────────────────────
    @SuppressWarnings("unchecked")
    private VBox buildOrderSection() {
        Label title = sectionTitle("🧾  Current Order");

        // Table
        orderTable = new TableView<>(orderedItems);
        orderTable.setPrefHeight(160);
        orderTable.setPlaceholder(new Label("No items added yet."));

        TableColumn<MenuItem, String> nameCol = new TableColumn<>("Item");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setPrefWidth(180);

        TableColumn<MenuItem, Double> priceCol = new TableColumn<>("Price (SR)");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        priceCol.setPrefWidth(100);

        TableColumn<MenuItem, Integer> calCol = new TableColumn<>("kcal");
        calCol.setCellValueFactory(new PropertyValueFactory<>("calories"));
        calCol.setPrefWidth(70);

        orderTable.getColumns().addAll(nameCol, priceCol, calCol);

        // Remove selected
        Button removeBtn = primaryButton("Remove Selected", "#B71C1C");
        removeBtn.setMaxWidth(Double.MAX_VALUE);
        removeBtn.setOnAction(e -> {
            int idx = orderTable.getSelectionModel().getSelectedIndex();
            if (idx < 0) {
                setStatus("⚠ Select an item in the table to remove.", true);
                return;
            }
            String removedName = orderedItems.get(idx).getName();
            orderedItems.remove(idx);
            currentOrder.removeItem(idx);
            updateTotal();
            setStatus("✔ Removed: " + removedName, false);
        });

        // Total label
        totalLabel = new Label("Total (incl. 15% VAT): 0.00 SR");
        totalLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        totalLabel.setTextFill(Color.web("#1a237e"));

        // Save/invoice
        Button saveBtn = primaryButton("💾  Generate Invoice & Save", "#4A148C");
        saveBtn.setMaxWidth(Double.MAX_VALUE);
        saveBtn.setOnAction(e -> {
            if (currentOrder.getCustomer() == null) {
                setStatus("⚠ Please register a customer first.", true);
                return;
            }
            if (currentOrder.isEmpty()) {
                setStatus("⚠ Cannot save an empty order.", true);
                return;
            }
            try {
                FileHandler.saveOrderToFile(currentOrder, "Order_Invoice.txt");
                setStatus("✔ Invoice saved to Order_Invoice.txt\n\n" + currentOrder.toString(), false);
                // Reset for next order
                currentOrder = new Order();
                orderedItems.clear();
                updateTotal();
            } catch (InvalidInputException ex) {
                setStatus("⚠ " + ex.getMessage(), true);
            }
        });

        return card(title, orderTable, removeBtn, totalLabel, saveBtn);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Status / output section
    // ─────────────────────────────────────────────────────────────────────────
    private VBox buildStatusSection() {
        statusArea = new TextArea();
        statusArea.setEditable(false);
        statusArea.setPrefHeight(120);
        statusArea.setStyle("-fx-font-family: monospace; -fx-font-size: 12;");
        return card(sectionTitle("📋  System Messages"), statusArea);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Helpers
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Factory method — creates the correct {@link MenuItem} subclass from
     * the user's category/label selection.
     */
    private MenuItem createMenuItem(String category, String label) throws InvalidInputException {
        switch (category) {
            case "Appetizer":
                switch (label) {
                    case "Garden Salad – 25 SR":
                        return new Appetizer("Garden Salad", 25.0, 120, 1, true);
                    case "Mozzarella Sticks – 30 SR":
                        return new Appetizer("Mozzarella Sticks", 30.0, 280, 0, true);
                    default:
                        return new Appetizer("Spring Rolls", 20.0, 200, 2, false);
                }
            case "Main Course":
                switch (label) {
                    case "Grilled Chicken – 55 SR":
                        return new MainCourse("Grilled Chicken", 55.0, 600, 25, "Arabic");
                    case "Beef Steak – 80 SR":
                        return new MainCourse("Beef Steak", 80.0, 850, 35, "Western");
                    default:
                        return new MainCourse("Vegetable Pasta", 45.0, 420, 20, "Italian");
                }
            default: // Drink
                switch (label) {
                    case "Fresh Orange Juice – 10 SR":
                        return new DrinkItem("Fresh Orange Juice", 10.0, 150, DrinkItem.Size.LARGE);
                    case "Water Bottle – 5 SR":
                        return new DrinkItem("Water Bottle", 5.0, 0, DrinkItem.Size.MEDIUM);
                    default:
                        return new DrinkItem("Soft Drink", 8.0, 140, DrinkItem.Size.MEDIUM);
                }
        }
    }

    private void updateTotal() {
        totalLabel.setText(String.format("Total (incl. 15%% VAT): %.2f SR", currentOrder.calculateTotal()));
    }

    private void setStatus(String msg, boolean isError) {
        statusArea.setText(msg);
        statusArea.setStyle(isError
                ? "-fx-font-family: monospace; -fx-font-size: 12; -fx-text-fill: #B71C1C;"
                : "-fx-font-family: monospace; -fx-font-size: 12;");
    }

    private Label sectionTitle(String text) {
        Label l = new Label(text);
        l.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        l.setTextFill(Color.web("#37474f"));
        return l;
    }

    private Button primaryButton(String text, String hex) {
        Button b = new Button(text);
        b.setStyle("-fx-background-color: " + hex + "; -fx-text-fill: white; -fx-font-weight: bold;");
        return b;
    }

    private VBox card(javafx.scene.Node... children) {
        VBox box = new VBox(8, children);
        box.setStyle("-fx-border-color: #CFD8DC; -fx-border-radius: 6; "
                + "-fx-background-color: white; -fx-background-radius: 6; -fx-padding: 12;");
        box.setMaxWidth(Double.MAX_VALUE);
        return box;
    }

    public static void main(String[] args) {
        launch(args);
    }
}