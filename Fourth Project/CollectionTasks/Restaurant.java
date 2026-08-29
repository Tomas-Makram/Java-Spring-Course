package CollectionTasks;
import java.util.*;

public class Restaurant {

    private ArrayList<MenuItem> menu = new ArrayList<>();
    private LinkedList<Order> kitchenQueue = new LinkedList<>();
    private HashMap<Integer, Order> orders = new HashMap<>();
    private LinkedHashMap<Integer, Order> completedOrders = new LinkedHashMap<>();

    // ================= MENU OPERATIONS =================

    public boolean addMenuItem(MenuItem item) {
        if (findMenuItem(item.getId()) != null) {
            System.out.println("Error: A menu item with ID " + item.getId() + " already exists.");
            return false;
        }
        menu.add(item);
        System.out.println("Menu item added: " + item.getName());
        return true;
    }

    public boolean removeMenuItem(int id) {
        MenuItem item = findMenuItem(id);
        if (item == null) {
            System.out.println("Error: No menu item found with ID " + id);
            return false;
        }
        menu.remove(item);
        System.out.println("Menu item removed: " + item.getName());
        return true;
    }

    public void displayMenu() {
        if (menu.isEmpty()) {
            System.out.println("The menu is currently empty.");
            return;
        }
        System.out.println("===== MENU =====");
        for (MenuItem item : menu) {
            System.out.println(item);
        }
    }

    public MenuItem findMenuItem(int id) {
        for (MenuItem item : menu) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    public void searchMenuItem(int id) {
        MenuItem item = findMenuItem(id);
        if (item == null) {
            System.out.println("No menu item found with ID " + id);
        } else {
            System.out.println("Found: " + item);
        }
    }

    // ================= ORDER OPERATIONS =================

    public boolean createOrder(int orderId, String customerName) {
        if (orders.containsKey(orderId)) {
            System.out.println("Error: An order with ID " + orderId + " already exists.");
            return false;
        }
        Order order = new Order(orderId, customerName);
        orders.put(orderId, order); // permanent record, keyed by ID
        System.out.println("Order #" + orderId + " created for " + customerName + " (status: PENDING).");
        return true;
    }

    public void addItemToOrder(int orderId, int menuItemId, int quantity) {
        Order order = orders.get(orderId);
        if (order == null) {
            System.out.println("Error: No order found with ID " + orderId);
            return;
        }
        if (!order.isModifiable()) {
            System.out.println("Error: Order #" + orderId + " is " + order.getStatus() + " and cannot be modified.");
            return;
        }
        MenuItem menuItem = findMenuItem(menuItemId);
        if (menuItem == null) {
            System.out.println("Error: No menu item found with ID " + menuItemId);
            return;
        }
        if (quantity <= 0) {
            System.out.println("Error: Quantity must be greater than zero.");
            return;
        }
        order.addItem(new OrderItem(menuItem, quantity));
        System.out.println("Added " + quantity + " x " + menuItem.getName() + " to Order #" + orderId);
    }

    public void removeItemFromOrder(int orderId, int menuItemId) {
        Order order = orders.get(orderId);
        if (order == null) {
            System.out.println("Error: No order found with ID " + orderId);
            return;
        }
        if (!order.isModifiable()) {
            System.out.println("Error: Order #" + orderId + " is " + order.getStatus() + " and cannot be modified.");
            return;
        }
        boolean removed = order.removeItem(menuItemId);
        if (removed) {
            System.out.println("Item removed from Order #" + orderId);
        } else {
            System.out.println("Error: Order #" + orderId + " does not contain menu item ID " + menuItemId);
        }
    }

    public void displayOrder(int orderId) {
        Order order = orders.get(orderId);
        if (order == null) {
            System.out.println("Error: No order found with ID " + orderId);
            return;
        }
        order.displayOrder();
    }

    public void addOrderToKitchenQueue(int orderId) {
        Order order = orders.get(orderId);
        if (order == null) {
            System.out.println("Error: No order found with ID " + orderId);
            return;
        }
        if (order.getStatus() != OrderStatus.PENDING) {
            System.out.println("Error: Order #" + orderId + " cannot be queued (current status: "
                    + order.getStatus() + "). Only PENDING orders can be sent to the kitchen.");
            return;
        }
        kitchenQueue.addLast(order); // enqueue at the tail
        order.updateStatus(OrderStatus.IN_KITCHEN);
        System.out.println("Order #" + orderId + " added to kitchen queue (status: IN_KITCHEN).");
    }

    public void processNextOrder() {
        if (kitchenQueue.isEmpty()) {
            System.out.println("Error: The kitchen queue is empty. No order to process.");
            return;
        }
        Order order = kitchenQueue.removeFirst(); // dequeue from the head (FIFO)
        order.updateStatus(OrderStatus.COMPLETED);
        completedOrders.put(order.getOrderId(), order); // stays in insertion/completion order
        System.out.println("Order #" + order.getOrderId() + " processed and marked COMPLETED.");
    }

    public void searchOrder(int orderId) {
        Order order = orders.get(orderId);
        if (order == null) {
            System.out.println("No order found with ID " + orderId);
        } else {
            order.displayOrder();
        }
    }

    public void checkOrderStatus(int orderId) {
        Order order = orders.get(orderId);
        if (order == null) {
            System.out.println("Error: No order found with ID " + orderId);
            return;
        }
        System.out.println("Order #" + orderId + " status: " + order.getStatus());
    }

    public boolean cancelOrder(int orderId) {
        Order order = orders.get(orderId);
        if (order == null) {
            System.out.println("Error: No order found with ID " + orderId);
            return false;
        }
        if (order.getStatus() == OrderStatus.COMPLETED || order.getStatus() == OrderStatus.CANCELLED) {
            System.out.println("Error: Order #" + orderId + " is already " + order.getStatus() + ".");
            return false;
        }
        kitchenQueue.remove(order); // no-op if it wasn't queued
        order.updateStatus(OrderStatus.CANCELLED);
        System.out.println("Order #" + orderId + " has been CANCELLED.");
        return true;
    }

    public void displayCompletedOrders() {
        if (completedOrders.isEmpty()) {
            System.out.println("No orders have been completed yet.");
            return;
        }
        System.out.println("===== COMPLETED ORDERS (in completion order) =====");
        for (Order order : completedOrders.values()) {
            System.out.println(order);
        }
    }
}