import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * The InventoryRecord class that provides functions to operate the InventoryRecord Table. InventoryRecord is the number
 * of units available for purchase and the price per unit for the current inventory (positive, with 2 digits after the
 * decimal place).
 */
public class InventoryRecord {

    public static boolean createTable(Statement stmt) {
        String createTableInventoryRecord =
                "create table InventoryRecord("
                        + "  QuantityInStock INT NOT NULL check (QuantityInStock >= 0),"
                        + "  UnitBuyPrice DECIMAL(19,2) NOT NULL,"
                        + "  ProductSKU VARCHAR(16) PRIMARY KEY,"
                        + "  FOREIGN KEY (ProductSKU) REFERENCES Product(SKU) ON DELETE CASCADE"
                        + ")";
        try {
            stmt.executeUpdate(createTableInventoryRecord);
            System.out.println("Created table InventoryRecord");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * Drops the InventoryRecord table.
     *
     * @param stmt The Statement object.
     * @return true if dropped successfully, otherwise false.
     */
    public static boolean dropTable(Statement stmt) {
        try {
            stmt.executeUpdate("DROP TABLE InventoryRecord");
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    /**
     * Inserts one row into the RecordInventory table.
     *
     * @param conn The connection object used in the driver.
     * @param sku The SKU of the product.
     * @param quantityInStock The quantity of the product.
     * @param unitBuyPrice The unit price of the product.
     * @return true if inserted successfully, otherwise false.
     */
    public static boolean insert(Connection conn, String sku, int quantityInStock, double unitBuyPrice) {
        try {
            PreparedStatement insertRow_InventoryRecord =
                    conn.prepareStatement("INSERT INTO InventoryRecord VALUES(?,?,?)");
            // insert costumers into table
            insertRow_InventoryRecord.setInt(1, quantityInStock);
            insertRow_InventoryRecord.setDouble(2, unitBuyPrice);
            insertRow_InventoryRecord.setString(3, sku);
            insertRow_InventoryRecord.execute();
            insertRow_InventoryRecord.close();
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        }
    }
}
