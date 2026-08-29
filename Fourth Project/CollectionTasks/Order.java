package CollectionTasks;

import java.util.*;

public class Order {
    private int orderId;
    private String customerName;
    private ArrayList<OrderItem> items;
    private double total;
    private OrderStatus status;

    public Order(int orderId, String customerName) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.items = new ArrayList<>();
        this.total = 0.0;
        this.status = OrderStatus.PENDING; // every order starts as PENDING
    }

    public int getOrderId() {
        return orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public ArrayList<OrderItem> getItems() {
        return items;
    }

    public double getTotal() {
        return total;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void addItem(OrderItem orderItem) {
        items.add(orderItem);
        calculateTotal();
    }

    public boolean removeItem(int menuItemId) {
        boolean removed = items.removeIf(oi -> oi.getItem().getId() == menuItemId);
        if (removed) {
            calculateTotal();
        }
        return removed;
    }

    public double calculateTotal() {
        double sum = 0.0;
        for (OrderItem oi : items) {
            sum += oi.calculateSubtotal();
        }
        this.total = sum;
        return this.total;
    }

    public void updateStatus(OrderStatus newStatus) {
        this.status = newStatus;
    }

    public boolean isModifiable() {
        return status != OrderStatus.COMPLETED && status != OrderStatus.CANCELLED;
    }

    public void displayOrder() {
        System.out.println("--------------------------------------------------");
        System.out.println("Order ID   : " + orderId);
        System.out.println("Customer   : " + customerName);
        System.out.println("Status     : " + status);
        if (items.isEmpty()) {
            System.out.println("Items      : (no items yet)");
        } else {
            System.out.println("Items:");
            for (OrderItem oi : items) {
                System.out.println("   " + oi);
            }
        }
        System.out.printf("Total      : %.2f%n", calculateTotal());
        System.out.println("--------------------------------------------------");
    }

    @Override
    public String toString() {
        return String.format("Order #%d | Customer: %-10s | Status: %-10s | Total: %.2f",
                orderId, customerName, status, total);
    }
}
