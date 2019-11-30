import java.sql.SQLException;
import java.sql.Statement;

/**
 * This class provides functions to operate the Customer Table. Customer is information about the customer, including
 * name, address, city, state, country, postal code. Make reasonable assumptions about the sizes of the fields, and
 * whether state and country should be enumerated values or strings. The customer also has a customer id that is a
 * numeric gensym. Payment information is not part of this database.
 */
public class Customer {

    public static boolean createTable(Statement stmt) {
        String createTableCustomer =
                "CREATE TABLE Customer("
                        + "  CustomerId INT PRIMARY KEY ,"
                        + "  GivenName varchar(16) not null,"
                        + "  FamilyName varchar(16) not null,"
                        + "  Address varchar(255) not null,"
                        + "  City varchar(16) not null,"
                        + "  State varchar(16) not null,"
                        + "  Country varchar(16) not null,"
                        + "  PostalCode varchar(32) not null"
                        + ")";
        try {
            stmt.executeUpdate(createTableCustomer);
            System.out.println("Created table InventoryRecord");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }

    public static boolean dropTable(Statement stmt) {
        try {
            stmt.executeUpdate("DROP TABLE Customer");
        } catch (SQLException e) {
            return false;
        }
        return true;
    }


}
