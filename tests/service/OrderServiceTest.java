package service;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderServiceTest {
    private Connection conn = null;
    private InOrderModel inOrderModel = null;
    private List<Order> orders = null;

    @BeforeEach
    void setUp() {
        try {
            inOrderModel = new InOrderModel();
            conn = inOrderModel.init();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        orders = new ArrayList<>();
        orders.add(new Order(1, 1, new Date(1L), null));
        orders.add(new Order(1, 2, new Date(2L), null));
    }

    /** Tests the insertion of an Order. */
    @Test
    public void insertTest(){
        Product product = new Product("a", "b", "AB-000001-0N");
        Customer customer = new Customer(1, "name1", "address1", "city1", "state1", "country1", "postalCode1");
        ProductService.insert(conn, product);
        CustomerService.insert(conn, customer);

        OrderService.insert(conn, orders.get(0), Arrays.asList(new OrderRecord(1, 1, 2.22, "AB-000001-0N")));
        assertEquals(orders.get(0), OrderService.getById(conn, orders.get(0).getOrderId()));
    }

    /** Tests the insertion of an Order without any OrderRecord. */
    @Test
    public void insertTest_noOrderRecord() {
        Product product = new Product("a", "b", "AB-000002-0N");
        Customer customer = new Customer(2, "name1", "address1", "city1", "state1", "country1", "postalCode1");
        ProductService.insert(conn, product);
        CustomerService.insert(conn, customer);

        boolean result = OrderService.insert(conn, orders.get(1), null);
        assertEquals(result, false);
    }

    /** Tests the order ship method. */
    @Test
    public void testShipOrder() {
        Product product = new Product("a", "b", "AB-000001-0N");
        Customer customer = new Customer(1, "name1", "address1", "city1", "state1", "country1", "postalCode1");
        ProductService.insert(conn, product);
        CustomerService.insert(conn, customer);
        OrderService.insert(conn, orders.get(0), Arrays.asList(new OrderRecord(1, 1, 2.22, "AB-000001-0N")));

        OrderService.shipOrder(conn, orders.get(0).getOrderId(), new Date(1000000000L));
        assertEquals(OrderService.getById(conn, orders.get(0).getOrderId()).getShipDate().toString(), "1970-01-12");
    }

    /** Tests getting all orders.  */
    @Test
    public void testGetAllOrders() {
        Product product = new Product("a", "b", "AB-000001-0N");
        Customer customer = new Customer(1, "name1", "address1", "city1", "state1",
                "country1", "postalCode1");
        ProductService.insert(conn, product);
        CustomerService.insert(conn, customer);
        for (Order order: orders) {
            OrderService.insert(conn, order, Arrays.asList(
                    new OrderRecord(order.getOrderId(), 1, 2.22, "AB-000001-0N")));
        }

        List<Order> ordersFromDb = OrderService.getAll(conn);
        assertEquals(ordersFromDb.size(), orders.size());
        for (int i = 0; i < ordersFromDb.size(); i++) {
            assertEquals(ordersFromDb.get(i), orders.get(i));
        }
    }

    /** Tests getting order by its id. */
    @Test
    public void testGetById(){
        Product product = new Product("a", "b", "AB-000001-0N");
        Customer customer = new Customer(1, "name1", "address1", "city1", "state1", "country1", "postalCode1");
        ProductService.insert(conn, product);
        CustomerService.insert(conn, customer);
        OrderService.insert(conn, orders.get(0), Arrays.asList(new OrderRecord(1, 1, 2.22, "AB-000001-0N")));

        assertEquals(OrderService.getById(conn, orders.get(0).getOrderId()), orders.get(0));
    }
}
