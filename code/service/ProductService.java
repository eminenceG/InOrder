package service;

import model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class provides services to operate the Product table.
 */
public class ProductService {

    /**
     * Creates the Product table.
     * @param stmt The Statement objection.
     * @return true if the table is created successfully, otherwise false.
     */
    public static boolean createTable(Statement stmt) {
        String createTable_Product =
                "CREATE TABLE Product("
                        + "  name VARCHAR(255) NOT NULL,"
                        + "  description VARCHAR(255) NOT NULL,"
                        + "  SKU VARCHAR(16),"
                        + "  PRIMARY KEY(SKU),"
                        + "  CHECK(ISSKU(SKU))"
                        + ")";
        try {
            stmt.executeUpdate(createTable_Product);
            System.out.println("Created table Product");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * Inserts one row into the Product table.
     * @param conn the Connection object.
     * @param product one product.
     * @return true if inserted successfully, false if not.
     */
    public static boolean insert(Connection conn, Product product) {
        String name = product.getName();
        String description = product.getDescription();
        String sku = product.getSku();
        return insert(conn, name, description, sku);
    }

    private static boolean insert(Connection conn, String name, String description, String sku) {
        try {
            PreparedStatement insertRow =
                    conn.prepareStatement("INSERT INTO Product Values(?,?,?)");
            insertRow.setString(1, name);
            insertRow.setString(2, description);
            insertRow.setString(3, sku);
            insertRow.execute();
            insertRow.close();
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        }
        return true;
    }

    /**
     * Deletes one row by its ID.
     *
     * @param conn The Connection objection used in the driver.
     * @param sku The SKU of the product.
     * @return true if deleted successfully, otherwise false.
     */
    public static boolean deleteById(Connection conn, String sku) {
        try {
            PreparedStatement delete_sql =
                    conn.prepareStatement("DELETE FROM Product where SKU = ?");
            delete_sql.setString(1, sku);
            delete_sql.execute();
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        }
        return true;
    }

    /**
     * Updates one product.
     * @param conn The Connection object used in the driver.
     * @param product The Production object.
     * @return true if updated successfully, otherwise false.
     */
    public static boolean update(Connection conn, Product product) {
        String sku = product.getSku();
        String name = product.getName();
        String description = product.getDescription();
        return updateById(conn, sku, name, description);
    }

    private static boolean updateById(Connection conn, String sku, String name, String description) {
        try {
            PreparedStatement updateRow=
                    conn.prepareStatement("UPDATE Product SET name = ?, description = ? WHERE SKU = ?");
            updateRow.setString(1, name);
            updateRow.setString(2, description);
            updateRow.setString(3, sku);
            updateRow.execute();
            updateRow.close();
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        }
        return true;
    }

    /**
     * Get the product by SKU.
     * @param conn the Connection object.
     * @param sku the SKU.
     * @return the Product, null if cannot get the product.
     */
    public static Product getProductBySku(Connection conn, String sku) {
        try {
            PreparedStatement sql =
                    conn.prepareStatement("select Name, Description from Product where sku = ?");
            sql.setString(1, sku);

            ResultSet rs = sql.executeQuery();
            rs.next();
            Product product = new Product(rs.getString(1), rs.getString(2), sku);
            rs.close();
            return product;
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        }
    }

    /**
     * Get all products in the Product table, the result list is ordered by product name.
     * @param conn the Connection object.
     * @return all products.
     */
    public static List<Product> getAllProducts(Connection conn)  {
        List<Product> products = new ArrayList<Product>();
        try (
                Statement stmt = conn.createStatement();
        ) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM Product ORDER BY Name");
            while (rs.next()) {
                String name = rs.getString(1);
                String description = rs.getString(2);
                String sku = rs.getString(3);
                products.add(new Product(name, description, sku));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
}
