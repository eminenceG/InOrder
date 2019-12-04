package controller;

/**
 * This interface represents a set of features that the program offers. Each feature is exposed as a
 * function in this interface. This function is used suitably as a callback by the view, to pass
 * control to the controller. How the view uses them as callbacks is completely up to how the view
 * is designed.
 */
public interface Features {

    /**
     * Adds a new product.
     *
     * @param name the product name.
     * @param description the product description.
     * @param sku the product sku.
     */
    void addProduct(String name, String description, String sku);

    /**
     * Shows all product.
     */
    void showProduct();
}

