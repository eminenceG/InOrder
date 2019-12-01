package service;

import model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CustomerServiceTest {

    private Connection conn = null;
    private InOrder inOrder = null;
    private List<Customer> customers = null;

    @BeforeEach
    void setUp() {
        try {
            inOrder = new InOrder();
            conn = inOrder.init();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        customers = new ArrayList<>();
        customers.add(new Customer(1, "name1", "address1", "city1", "state1", "country1", "postalCode1"));
        customers.add(new Customer(2, "name2", "address2", "city2", "state2", "country2", "postalCode2"));

    }

    /** Tests the insertion of a Customer. */
    @Test
    public void testInsert() {
        CustomerService.insert(conn, customers.get(0));
        Customer customerFromDb = CustomerService.getById(conn, customers.get(0).getCustomerId());
        assertEquals(customers.get(0), customerFromDb);
    }

    /** Tests the update of a Customer. */
    @Test
    public void testUpdate() {
        Customer newOne = new Customer(1, "name3", "address3", "city3", "state3", "country3", "postalCode3");
        CustomerService.insert(conn, customers.get(0));
        CustomerService.update(conn, newOne);
        assertEquals(CustomerService.getById(conn, customers.get(0).getCustomerId()), newOne);
    }

    /** Tests the deletion of a Customer. */
    @Test
    public void testDeleteById() {
        CustomerService.insert(conn, customers.get(0));
        CustomerService.deleteById(conn, customers.get(0).getCustomerId());
        assertEquals(CustomerService.getById(conn,customers.get(0).getCustomerId()), null);
    }

    /** Tests getting a Customer via its id. */
    @Test
    public void testGetById() {
        CustomerService.insert(conn, customers.get(0));
        assertEquals(CustomerService.getById(conn,customers.get(0).getCustomerId()), customers.get(0));
    }
}