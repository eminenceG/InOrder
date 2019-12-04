package service;

import model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceTest {

    private Connection conn = null;
    private InOrderModel inOrderModel = null;
    private List<Product> products = null;

    @BeforeEach
    void setUp() {
        try {
            inOrderModel = new InOrderModel();
            conn = inOrderModel.init();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        products = new ArrayList<>();
        products.add(new Product("a", "b", "AB-000001-0N"));
        products.add(new Product("c", "d", "AB-000002-0N"));
    }

    /** Tests the insertion of a Product. */
    @Test
    public void testInsert() {

        ProductService.insert(conn, products.get(0));
        Product productFromDb = ProductService.getProductBySku(conn, products.get(0).getSku());
        assertEquals(products.get(0), productFromDb);
    }

    /** Tests getting all Products. */
    @Test
    public void testGetAllProducts() {
        for (Product p : products) {
            ProductService.insert(conn, p);
        }
        List<Product> productsFromDb = ProductService.getAllProducts(conn);
        assertEquals(productsFromDb.size(), products.size());
        for (int i = 0; i < products.size(); i++) {
            assertEquals(products.get(i), productsFromDb.get(i));
        }
    }

    /** Tests when the SKU is not valid. */
    @Test
    public void testInvalidSku() {
        Product p = new Product("a", "b", "c");
        assertEquals(ProductService.insert(conn, p), false);
        assertEquals(ProductService.getAllProducts(conn).size(), 0);
    }
}