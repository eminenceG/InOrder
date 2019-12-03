package service;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderRecordServiceTest {

    private Connection conn = null;
    private InOrder inOrder = null;
    private List<OrderRecord> orderRecords = null;

    @BeforeEach
    void setUp() {
        try {
            inOrder = new InOrder();
            conn = inOrder.init();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        orderRecords = new ArrayList<>();
        orderRecords.add(new OrderRecord(1, 2, 1.11, "AB-000001-0N"));
        orderRecords.add(new OrderRecord(1, 1, 2.11, "AB-000007-0N"));
    }

    /** Tests the insertion of an OrderRecord. */
    @Test
    public void testInsert() {
        Product product = new Product("a", "b", "AB-000001-0N");
        Product product2 = new Product("a", "b", "AB-123456-0N");
        Customer customer = new Customer(1, "name1", "address1", "city1", "state1", "country1", "postalCode1");
        Order order = new Order(1,1,new Date(1L),new Date(2L));
        ProductService.insert(conn, product);
        ProductService.insert(conn, product2);
        CustomerService.insert(conn, customer);
        OrderService.insert(conn, order, Arrays.asList(new OrderRecord(1, 1, 2.22, "AB-123456-0N")));

        OrderRecordService.insert(conn, orderRecords.get(0));
        assertEquals(orderRecords.get(0), OrderRecordService.getById(conn,
                orderRecords.get(0).getOrderId(), orderRecords.get(0).getSku()));
    }

    /** Tests the insertion of an OrderRecord when the quantity exceeds the number in the stock. */
    @Test
    public void testInsert_exceedInStock() {
        Product product = new Product("a", "b", "AB-000001-0N");
        InventoryRecord record = new InventoryRecord(1, 1.11, "AB-000001-0N");
        Product product2 = new Product("a", "b", "AB-000002-0N");
        Customer customer = new Customer(1, "name1", "address1", "city1", "state1", "country1", "postalCode1");
        Order order = new Order(1,1,new Date(1L),new Date(2L));
        ProductService.insert(conn, product);
        ProductService.insert(conn, product2);
        CustomerService.insert(conn, customer);
        InventoryRecordService.insert(conn, record);
        OrderService.insert(conn, order, Arrays.asList(new OrderRecord(1, 1, 2.22, "AB-000002-0N")));

        boolean result = OrderRecordService.insert(conn, orderRecords.get(0));
        assertEquals(result, false);
    }

    /** Tests getting an OrderRecord via its id. */
    @Test
    public void testGetById() {
        Product product = new Product("a", "b", "AB-000001-0N");
        Customer customer = new Customer(1, "name1", "address1", "city1", "state1", "country1", "postalCode1");
        Order order = new Order(1,1,new Date(1L),new Date(2L));
        ProductService.insert(conn, product);
        CustomerService.insert(conn, customer);
        OrderService.insert(conn, order, Arrays.asList(orderRecords.get(0)));
        assertEquals(orderRecords.get(0), OrderRecordService.getById(conn,
                orderRecords.get(0).getOrderId(), orderRecords.get(0).getSku()));
    }

    /** Tests getting all OrderRecords. */
    @Test
    public void testGetAll() {
        Product product = new Product("a", "b", "AB-000001-0N");
        Product product2 = new Product("a", "b", "AB-000007-0N");
        Customer customer = new Customer(1, "name1", "address1", "city1", "state1", "country1", "postalCode1");
        Order order = new Order(1,1,new Date(1L),new Date(2L));
        ProductService.insert(conn, product);
        ProductService.insert(conn, product2);
        CustomerService.insert(conn, customer);
        OrderService.insert(conn, order, orderRecords);

        List<OrderRecord> orderRecordsFromDb = OrderRecordService.getAll(conn);
        assertEquals(orderRecordsFromDb.size(), orderRecords.size());
    }
}
