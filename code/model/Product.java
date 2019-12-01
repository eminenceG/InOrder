package model;

/**
 * The data model for Product table. Product represents a product that can be purchased. It includes the name of the
 * product, a product description, a vendor product SKU (Stock Keeping Unit) that identifies the product. For this
 * exercise the SKU is a 12-character value of the form AA-NNNNNN-CC where A is an upper-case letter, N is a digit from
 * 0-9, and C is either a digit or an uppper case letter. For example, "AB-123456-0N".
 */
public class Product {
    private String name;
    private String description;
    private String sku;

    /**
     * Initializes a Product instance.
     * @param name the product name.
     * @param description the product description.
     * @param sku the product SKU.
     */
    public Product(String name, String description, String sku) {
        this.name = name;
        this.description = description;
        this.sku = sku;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getSku() {
        return sku;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        Product other = (Product) obj;
        return other.getName().equals(name) && other.getDescription().equals(description) && other.getSku().equals(sku);
    }

    @Override
    public String toString() {
        return String.format("name: %s, description: %s, sku: %s", name, description, sku);
    }
}
