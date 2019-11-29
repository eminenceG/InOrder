import java.sql.*;

/**
 * The Product class that provides functions to operate the Product Table. Product represents a product that can be
 * purchased. It includes the name of the product, a product description, a vendor product SKU (Stock Keeping Unit)
 * that identifies the product. For this exercise the SKU is a 12-character value of the form AA-NNNNNN-CC where A is
 * an upper-case letter, N is a digit from 0-9, and C is either a digit or an uppper case letter. For example,
 * "AB-123456-0N".
 */
public class Product {

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
     *
     * @param conn The Connection objection used in the driver.
     * @param name  The name of the product.
     * @param description The description of the product.
     * @param sku The SKU of the product.
     * @return true if the row is inserted successfully, otherwise false.
     */
    public static boolean insert(Connection conn, String name, String description, String sku) {
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
     * Update one row by its ID in the Product table.
     *
     * @param conn The Connection objection used in the driver.
     * @param sku The SKU of the product.
     * @param name  The name of the product.
     * @param description The description of the product.
     * @return true if updated successfully, otherwise false.
     */
    public static boolean updateById(Connection conn, String sku, String name, String description) {
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
     * Returns the number of rows in the the Product table. Returns -1 if the query fails.
     *
     * @param conn The Connection objection used in the driver.
     * @return the number of rows.
     */
    public static int numRows(Connection conn) {
        int num = -1;

        try {
            PreparedStatement sql =
                    conn.prepareStatement("SELECT COUNT(*) AS COUNT FROM Product");
            ResultSet rs = sql.executeQuery();
            rs.next();
            num = rs.getInt(1);
            rs.close();
        } catch (SQLException e) {
            System.err.println(e);
        }
        return num;
    }

    /**
     * Drops the Product table.
     *
     * @param stmt The Statement object.
     * @return true if dropped successfully, otherwise false.
     */
    public static boolean dropTable(Statement stmt) {
        try {
            stmt.executeUpdate("DROP TABLE Product");
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        }
        return true;
    }
}
