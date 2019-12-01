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
        assertEquals(inventoryRecords.get(0), InventoryRecordService.getById(conn, inventoryRecords.get(0).getSku()));
    }

    @Test
    public void testGetAll() {
        ProductService.insert(conn, new Product("a", "b", "AB-000001-0N"));
        ProductService.insert(conn, new Product("c", "b", "AB-000002-1N"));

        for (InventoryRecord inventoryRecord : inventoryRecords) {
            InventoryRecordService.insert(conn, inventoryRecord);
        }
        List<InventoryRecord> inventoryRecordsFromDB = InventoryRecordService.getAll(conn);
        assertEquals(inventoryRecordsFromDB.size(), inventoryRecords.size());
        for (int i = 0; i < inventoryRecordsFromDB.size(); i++) {
            assertEquals(inventoryRecords.get(i), inventoryRecordsFromDB.get(i));
        }
    }

    @Test
    public void testAddInvalidInventoryRecord() {
        // The key is not in the Product table
        InventoryRecordService.insert(conn, inventoryRecords.get(0));
        assertEquals(null, InventoryRecordService.getById(conn, inventoryRecords.get(0).getSku()));

        // TODO: The quantity is negative

        // TODO: The unit price is negative
    }
}
