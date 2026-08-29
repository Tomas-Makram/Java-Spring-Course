import CollectionTasks.*;

import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);
    private static Restaurant restaurant = new Restaurant();

    public static void main(String[] args) {
        loadSampleData(); // pre-loads the example menu from the assignment sheet
        boolean running = true;

        while (running) {
            printMenu();
            int choice = readInt("Choose an option: ");
            switch (choice) {
                case 1 -> handleAddMenuItem();
                case 2 -> handleRemoveMenuItem();
                case 3 -> restaurant.displayMenu();
                case 4 -> handleSearchMenuItem();
                case 5 -> handleCreateOrder();
                case 6 -> handleAddItemToOrder();
                case 7 -> handleRemoveItemFromOrder();
                case 8 -> handleDisplayOrder();
                case 9 -> handleAddOrderToKitchenQueue();
                case 10 -> restaurant.processNextOrder();
                case 11 -> handleSearchOrder();
                case 12 -> handleCheckOrderStatus();
                case 13 -> restaurant.displayCompletedOrders();
                case 14 -> handleCancelOrder(); // bonus: cancel order, per section 6
                case 15 -> {
                    running = false;
                    System.out.println("Exiting... Goodbye!");
                }
                default -> System.out.println("Invalid option. Please choose a number from the menu.");
            }
            System.out.println();
        }
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("========== RESTAURANT ORDER MANAGER ==========");
        System.out.println(" 1. Add Menu Item");
        System.out.println(" 2. Remove Menu Item");
        System.out.println(" 3. Display Menu");
        System.out.println(" 4. Search Menu Item");
        System.out.println(" 5. Create Order");
        System.out.println(" 6. Add Item to Order");
        System.out.println(" 7. Remove Item from Order");
        System.out.println(" 8. Display Order");
        System.out.println(" 9. Add Order to Kitchen Queue");
        System.out.println("10. Process Next Order");
        System.out.println("11. Search Order");
        System.out.println("12. Check Order Status");
        System.out.println("13. Display Completed Orders");
        System.out.println("14. Cancel Order");
        System.out.println("15. Exit");
        System.out.println("================================================");
    }

    // ================= Menu handlers =================

    private static void handleAddMenuItem() {
        int id = readInt("Enter item ID: ");
        System.out.print("Enter item name: ");
        String name = scanner.nextLine();
        double price = readDouble("Enter item price: ");
        System.out.print("Enter item category: ");
        String category = scanner.nextLine();
        restaurant.addMenuItem(new MenuItem(id, name, price, category));
    }

    private static void handleRemoveMenuItem() {
        int id = readInt("Enter item ID to remove: ");
        restaurant.removeMenuItem(id);
    }

    private static void handleSearchMenuItem() {
        int id = readInt("Enter item ID to search: ");
        restaurant.searchMenuItem(id);
    }

    private static void handleCreateOrder() {
        int orderId = readInt("Enter new order ID: ");
        System.out.print("Enter customer name: ");
        String name = scanner.nextLine();
        restaurant.createOrder(orderId, name);
    }

    private static void handleAddItemToOrder() {
        int orderId = readInt("Enter order ID: ");
        int menuItemId = readInt("Enter menu item ID: ");
        int quantity = readInt("Enter quantity: ");
        restaurant.addItemToOrder(orderId, menuItemId, quantity);
    }

    private static void handleRemoveItemFromOrder() {
        int orderId = readInt("Enter order ID: ");
        int menuItemId = readInt("Enter menu item ID to remove: ");
        restaurant.removeItemFromOrder(orderId, menuItemId);
    }

    private static void handleDisplayOrder() {
        int orderId = readInt("Enter order ID: ");
        restaurant.displayOrder(orderId);
    }

    private static void handleAddOrderToKitchenQueue() {
        int orderId = readInt("Enter order ID to send to kitchen: ");
        restaurant.addOrderToKitchenQueue(orderId);
    }

    private static void handleSearchOrder() {
        int orderId = readInt("Enter order ID to search: ");
        restaurant.searchOrder(orderId);
    }

    private static void handleCheckOrderStatus() {
        int orderId = readInt("Enter order ID: ");
        restaurant.checkOrderStatus(orderId);
    }

    private static void handleCancelOrder() {
        int orderId = readInt("Enter order ID to cancel: ");
        restaurant.cancelOrder(orderId);
    }

    // ================= Input helpers =================

    private static int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = Integer.parseInt(scanner.nextLine().trim());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please try again.");
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please try again.");
            }
        }
    }

    // ================= Sample data =================

    private static void loadSampleData() {
        restaurant.addMenuItem(new MenuItem(1, "Burger", 150, "Main Course"));
        restaurant.addMenuItem(new MenuItem(2, "Pizza", 200, "Main Course"));
        restaurant.addMenuItem(new MenuItem(3, "Pasta", 180, "Main Course"));
        restaurant.addMenuItem(new MenuItem(4, "Cola", 40, "Drinks"));
        System.out.println();
    }
}