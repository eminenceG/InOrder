package service;

import model.InventoryRecord;
import model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    /**
     * Gets an inventory record by the product's SKU.
     * @param conn the Connection object.
     * @param sku the product SKU.
     * @return the InventoryRecord instance, null if not found.
     */
    public static InventoryRecord getById(Connection conn, String sku) {
        try {
            PreparedStatement sql = conn.prepareStatement("select * from InventoryRecord where ProductSKU = ?");
            sql.setString(1, sku);
            ResultSet rs = sql.executeQuery();
            rs.next();
            InventoryRecord inventoryRecord = new InventoryRecord(rs.getInt(1), rs.getDouble(2),
                    rs.getString(3));
            rs.close();
            return inventoryRecord;
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        }
    }

    /**
     * Gets all inventory records ordered by the peoduct SKU.
     * @param conn the Connection object.
     * @return list of all inventory records
     */
    public static List<InventoryRecord> getAll(Connection conn) {
        List<InventoryRecord> inventoryRecords = new ArrayList<>();
        try (
                Statement stmt = conn.createStatement();
        ) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM InventoryRecord ORDER BY ProductSKU");
            while (rs.next()) {
                inventoryRecords.add(new InventoryRecord(rs.getInt(1), rs.getDouble(2), rs.getString(3)));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return inventoryRecords;
    }

    /**
     * Updates an inventory record.
     * @param conn the Connection object.
     * @param inventoryRecord the inventory record.
     * @return true if updated successfully, otherwise false.
     */
    public static boolean update(Connection conn, InventoryRecord inventoryRecord) {
        try {
            PreparedStatement updateRow = conn.prepareStatement(
                    "UPDATE InventoryRecord SET QuantityInStock = ?, UnitBuyPrice = ? WHERE ProductSKU =?");
            // insert costumers into table
            updateRow.setInt(1, inventoryRecord.getQuantityInStock());
            updateRow.setDouble(2, inventoryRecord.getUnitPrice());
            updateRow.setString(3, inventoryRecord.getSku());
            updateRow.execute();
            updateRow.close();
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        }
    }
}
