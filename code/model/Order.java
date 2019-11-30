package model;

import java.time.LocalDate;

/**
 * Data model for the Order table. Order is an order for a set of products. It includes a customer ID, an order ID
 * gensym, the order date, and shipment date indicating when the order was shipped. If shipment date is null, the order
 * has not yet shipped.
 */
public class Order {
    private final int customerId;
    private final int orderId;
    private final LocalDate orderDate;
    private LocalDate shipDate;
    private static String NOT_VALID_ARGUMENT = "The arguments are not valid.";

    /**
     * Initialize a shipped order.
     * @param customerId the customer ID.
     * @param orderId the order ID.
     * @param orderDate the order date.
     * @param shipDate the shipment date.
     * @throws IllegalArgumentException if customerId or orderId is not valid.
     */
    public Order(int customerId, int orderId, LocalDate orderDate, LocalDate shipDate) throws IllegalArgumentException {
        this(customerId, orderId, orderDate);
        this.shipDate = shipDate;
    }

    /**
     * Initializes a not yet shipped order.
     * @param customerId the customer ID.
     * @param orderDate the order date.
     * @throws IllegalArgumentException if customerId or orderId is not valid.
     */
    public Order(int customerId, int orderId, LocalDate orderDate) throws IllegalArgumentException {
        if (customerId <= 0 || orderId <= 0) {
            throw new IllegalArgumentException(NOT_VALID_ARGUMENT);
        }
        this.customerId = customerId;
        this.orderId = orderId;
        this.orderDate = orderDate;
    }

    public int getCustomerId() {
        return customerId;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public LocalDate getShipDate() {
        return shipDate;
    }
}
