package CollectionTasks;

public enum OrderStatus {
    PENDING,     // -> order created, not yet sent to the kitchen
    IN_KITCHEN,  // -> order added to the kitchen queue, waiting to be processed
    COMPLETED,   // -> order has been processed by the kitchen
    CANCELLED    // -> order was cancelled before completion
}
