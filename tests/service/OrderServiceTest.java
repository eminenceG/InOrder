package service;

import model.Customer;
import model.Order;
import model.OrderRecord;
import model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderServiceTest {
    private Connection conn = null;
    private InOrder inOrder = null;
    private List<Order> orders = null;

    @BeforeEach
    void setUp() {
        try {
            inOrder = new InOrder();
            conn = inOrder.init();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        orders = new ArrayList<>();
        orders.add(new Order(1, 1, new Date(1L), null));
        orders.add(new Order(2, 2, new Date(2L), null));
    }

    /** Tests the insertion of an Order. */
    @Test
    public void insertTest(){
        Product product = new Product("a", "b", "AB-000001-0N");
        Customer customer = new Customer(1, "name1", "address1", "city1", "state1", "country1", "postalCode1");
        ProductService.insert(conn, product);
        CustomerService.insert(conn, customer);

        OrderService.insert(conn, orders.get(0), Arrays.asList(new OrderRecord(1, 1, 2.22, "AB-000001-0N")));
        assertEquals(orders.get(0), getOrderById(conn, orders.get(0).getOrderId()));
    }

    /** Tests the insertion of an Order without any OrderRecord. */
    @Test
    public void insertTest_noOrderRecord(){
        Product product = new Product("a", "b", "AB-000002-0N");
        Customer customer = new Customer(2, "name1", "address1", "city1", "state1", "country1", "postalCode1");
        ProductService.insert(conn, product);
        CustomerService.insert(conn, customer);

        boolean result = OrderService.insert(conn, orders.get(1), null);
        assertEquals(result, false);
    }

    /** Tests the order ship method.  */
    @Test
    public void testShipOrder(){
        Product product = new Product("a", "b", "AB-000001-0N");
        Customer customer = new Customer(1, "name1", "address1", "city1", "state1", "country1", "postalCode1");
        ProductService.insert(conn, product);
        CustomerService.insert(conn, customer);
        OrderService.insert(conn, orders.get(0), Arrays.asList(new OrderRecord(1, 1, 2.22, "AB-000001-0N")));

        OrderService.shipOrder(conn, orders.get(0).getOrderId(), new Date(1000000000L));
        assertEquals(getOrderById(conn, orders.get(0).getOrderId()).getShipDate().toString(), "1970-01-12");
    }

    /** Helper function to get Order by its id. */
    private static Order getOrderById(Connection conn, int orderId) {
        try {
            PreparedStatement sql = conn.prepareStatement("select * from OrderTable where OrderId = ?");
            sql.setInt(1, orderId);
            ResultSet rs = sql.executeQuery();
            rs.next();
            Order order = new Order(rs.getInt(1), rs.getInt(2),
                    rs.getDate(3), rs.getDate(4));
            rs.close();
            return order;
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        }
    }
}
