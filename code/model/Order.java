package model;


import java.sql.Date;

/**
 * Data model for the Order table. Order is an order for a set of products. It includes a customer ID, an order ID
 * gensym, the order date, and shipment date indicating when the order was shipped. If shipment date is null, the order
 * has not yet shipped.
 */
public class Order {
    private final int customerId;
    private final int orderId;
    private final Date orderDate;
    private Date shipDate;
    private static String NOT_VALID_ARGUMENT = "The arguments are not valid.";

    /**
     * Initialize a shipped order.
     *
     * @param customerId the customer ID.
     * @param orderId    the order ID.
     * @param orderDate  the order date.
     * @param shipDate   the shipment date.
     * @throws IllegalArgumentException if customerId or orderId is not valid.
     */
    public Order(int customerId, int orderId, Date orderDate, Date shipDate) throws IllegalArgumentException {
        this(customerId, orderId, orderDate);
        this.shipDate = shipDate;
    }

    /**
     * Initializes a not yet shipped order.
     *
     * @param customerId the customer ID.
     * @param orderDate  the order date.
     * @throws IllegalArgumentException if customerId or orderId is not valid.
     */
    public Order(int customerId, int orderId, Date orderDate) throws IllegalArgumentException {
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

    public Date getOrderDate() {
        return orderDate;
    }

    public Date getShipDate() {
        return shipDate;
    }

    public int getOrderId() {
        return orderId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        Order other = (Order) obj;
        return other.getCustomerId() == customerId &&
                other.getOrderId() == orderId &&
                other.orderDate.toString().equals(orderDate.toString()) &&
                ((other.shipDate == null && shipDate == null) || (other.shipDate.toString().equals(shipDate.toString())));
    }

    @Override
    public String toString() {
        if (shipDate == null) {
            return String.format("CustomerId: %d, OrderId: %d, Order date: %s, Not shipped", customerId, orderId, orderDate.toString());
        } else {
            return String.format("CustomerId: %d, OrderId: %d, Order date: %s, Ship Date: %s.", customerId, orderId, orderDate.toString(), shipDate.toString());
        }
    }
}
