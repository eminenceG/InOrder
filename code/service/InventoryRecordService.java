package service;

import model.InventoryRecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * The service.InventoryRecord class that provides functions to operate the service.InventoryRecord Table. service.InventoryRecord is the number
 * of units available for purchase and the price per unit for the current inventory (positive, with 2 digits after the
 * decimal place).
 */
public class InventoryRecordService {

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
     * inserts one row into the InventoryRecord table.
     *
     * @param conn the connection object used in the driver.
     * @param inventoryRecord the InventoryRecord object.
     * @return true if inserted successfully, otherwise false.
     */
    public static boolean insert(Connection conn, InventoryRecord inventoryRecord)  {
        try {
            PreparedStatement insertRowInventoryRecord =
                    conn.prepareStatement("INSERT INTO InventoryRecord VALUES(?,?,?)");
            // insert costumers into table
            insertRowInventoryRecord.setInt(1, inventoryRecord.getQuantityInStock());
            insertRowInventoryRecord.setDouble(2, inventoryRecord.getUnitPrice());
            insertRowInventoryRecord.setString(3, inventoryRecord.getSku());
            insertRowInventoryRecord.execute();
            insertRowInventoryRecord.close();
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        }
    }
}
