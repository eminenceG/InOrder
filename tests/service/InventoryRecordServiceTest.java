package service;

import model.InventoryRecord;
import model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InventoryRecordServiceTest {
    private Connection conn = null;
    private InOrder inOrder = null;
    private List<InventoryRecord> inventoryRecords = null;

    @BeforeEach
    void setUp() {
        try {
            inOrder = new InOrder();
            conn = inOrder.init();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        inventoryRecords = new ArrayList<>();
        inventoryRecords.add(new InventoryRecord(1, 1.11, "AB-000001-0N"));
        inventoryRecords.add(new InventoryRecord(2, 2.22, "AB-000002-1N"));

    }

    /** Tests the insertion of an InventoryRecord. */
    @Test
    public void testInsert() {
        Product product = new Product("a", "b", "AB-000001-0N");
        ProductService.insert(conn, product);
        InventoryRecordService.insert(conn, inventoryRecords.get(0));
        assertEquals(inventoryRecords.get(0), getInventoryRecordByKsu(conn, inventoryRecords.get(0).getSku()));
    }

    /** Helper function to get InventoryRecord via ProductSKU. */
    private static InventoryRecord getInventoryRecordByKsu(Connection conn, String sku) {
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
}
