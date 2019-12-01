package model;

/**
 * The data model for service.InventoryRecord table. service.InventoryRecord is the number of units available for purchase and the price
 * per unit for the current inventory (positive, with 2 digits after the decimal place).
 */
public class InventoryRecord {

    private int quantityInStock;
    private double unitPrice;
    private String sku;
    private static String NOT_VALID_ARGUMENT = "The arguments are not valid.";

    /**
     * Constructs an service.InventoryRecord instance.
     *
     * @param quantityInStock the quantity in stock.
     * @param unitPrice       the unit price.
     * @param sku             the SKU of the product.
     * @throws IllegalArgumentException if any argument is not valid.
     */
    public InventoryRecord(int quantityInStock, double unitPrice, String sku) throws IllegalArgumentException {
        if (quantityInStock < 0 || unitPrice < 0) {
            throw new IllegalArgumentException(NOT_VALID_ARGUMENT);
        }
        this.quantityInStock = quantityInStock;
        this.unitPrice = unitPrice;
        this.sku = sku;
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        InventoryRecord other = (InventoryRecord) obj;
        return other.getSku().equals(sku) && other.getQuantityInStock() == quantityInStock && other.getUnitPrice() == unitPrice;
    }

}

