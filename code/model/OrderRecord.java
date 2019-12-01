package model;

import java.util.PrimitiveIterator;

/**
 * The data model for OrderRecord table. OrderRecord is the record for an item in the order. it includes the order ID,
 * the number of units, and the unit price.
 */
public class OrderRecord {
    private final int orderId;
    private final int numUnits;
    private final int unitPrice;
    private final String sku;
    private static String NOT_VALID_ARGUMENT = "The arguments are not valid.";


    /**
     * Initializes an OrderRecord instance.
     * @param orderId the order ID.
     * @param numUnits the number of units.
     * @param unitPrice the unit price.
     * @throws IllegalArgumentException if any argument is not valid.
     */
    public OrderRecord(int orderId, int numUnits, int unitPrice, String sku) throws IllegalArgumentException {
        if (orderId <= 0 || numUnits <= 0 || unitPrice < 0) {
            throw new IllegalArgumentException(NOT_VALID_ARGUMENT);
        }
        this.orderId = orderId;
        this.numUnits = numUnits;
        this.unitPrice = unitPrice;
        this.sku = sku;
    }

    public int getNumUnits() {
        return numUnits;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getSku() {
        return sku;
    }
}
