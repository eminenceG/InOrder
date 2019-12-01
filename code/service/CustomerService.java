package service;

import model.Customer;

import java.sql.*;

/**
 * This class provides services to operate the Customer table.
 */
public class CustomerService {

    protected static boolean createTable(Statement stmt) {
        String createTableCustomer =
                "CREATE TABLE Customer("
                        + "  CustomerId INT PRIMARY KEY ,"
                        + "  Name varchar(16) not null,"
                        + "  Address varchar(255) not null,"
                        + "  City varchar(16) not null,"
                        + "  State varchar(16) not null,"
                        + "  Country varchar(16) not null,"
                        + "  PostalCode varchar(32) not null"
                        + ")";
        try {
            stmt.executeUpdate(createTableCustomer);
            System.out.println("Created table Customer");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * Inserts one row into the Customer table.
     * @param conn the Connection object.
     * @param customer the Customer object.
     * @return true if inserted successfully, false other wise.
     */
    public static boolean insert(Connection conn, Customer customer) {
        int id = customer.getCustomerId();
        String name = customer.getName();
        String address = customer.getAddress();
        String city = customer.getCity();
        String state = customer.getState();
        String country = customer.getCountry();
        String postalCode = customer.getPostalCode();
        try {
            PreparedStatement insertRow_Customer =
                    conn.prepareStatement("insert into Customer values(?,?,?,?,?,?,?)");
            // insert costumers into table
            insertRow_Customer.setInt(1, id);
            insertRow_Customer.setString(2, name);
            insertRow_Customer.setString(3, address);
            insertRow_Customer.setString(4, city);
            insertRow_Customer.setString(5, state);
            insertRow_Customer.setString(6, country);
            insertRow_Customer.setString(7, postalCode);
            insertRow_Customer.execute();
            insertRow_Customer.close();
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        }
        return true;
    }

    /**
     * Update a customer in the Customer table.
     * @param conn the connection object.
     * @param customer the Customer object.
     * @return true if updated successfully, false otherwise.
     */
    public static boolean update(Connection conn, Customer customer) {
        try {
            PreparedStatement updateRow_Customer =
                    conn.prepareStatement("update Customer set PostalCode = ?, Name = ?, "
                            + "Address = ?, City = ?, State = ?, Country = ? WHERE CustomerId = ?");
            updateRow_Customer.setString(1, customer.getPostalCode());
            updateRow_Customer.setString(2, customer.getName());
            updateRow_Customer.setString(3, customer.getAddress());
            updateRow_Customer.setString(4, customer.getCity());
            updateRow_Customer.setString(5, customer.getState());
            updateRow_Customer.setString(6, customer.getCountry());
            updateRow_Customer.setInt(7, customer.getCustomerId());
            updateRow_Customer.execute();
            updateRow_Customer.close();
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        }
    }

    /**
     * Delete a customer by it's ID.
     * @param conn the connection object used in the driver.
     * @param id The id of the customer.
     * @return true if deleted successfully, otherwise false.
     */
    public static boolean deleteById(Connection conn, int id) {
        try {
            PreparedStatement delete_sql =
                    conn.prepareStatement("delete from Customer where CustomerId = ?");
            delete_sql.setInt(1, id);
            delete_sql.execute();
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        }
        return true;
    }

    /**
     * Gets a customer by its ID.
     * @param conn the Connection object.
     * @param id the customer ID.
     * @return the customer object, null if not found.
     */
    public static Customer getById(Connection conn, int id) {
        try {
            PreparedStatement sql = conn.prepareStatement("select * from Customer where CustomerId = ?");
            sql.setInt(1, id);
            ResultSet rs = sql.executeQuery();
            rs.next();
            Customer customer = new Customer(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
                    rs.getString(5), rs.getString(6), rs.getString(7));
            rs.close();
            return customer;
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        }
    }


}
