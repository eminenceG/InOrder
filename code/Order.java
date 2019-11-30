import java.sql.SQLException;
import java.sql.Statement;

/**
 * This class provides functions to operate the Order table. Order is an order for a set of products. It includes a
 * customer ID, an order ID gensym, the order date, and shipment date indicating when the order was shipped. If shipment
 * date is null, the order has not yet shipped. All items must be available in a single transaction to place an order.
 */
public class Order {

    public static boolean CreateTable(Statement stmt) {
        final String createTableOrder =
                "create table Order("
                        + "  CustomerId INT,"
                        + "  OrderId INT PRIMARY KEY ,"
                        + "  OrderDate date not null,"
                        + "  ShipmentDate date,"
                        + "  foreign key (CustomerId) references Customer (CustomerId) on delete cascade,"
                        + ")";
        try {
            stmt.executeUpdate(createTableOrder);
            System.out.println("Created table OrderInfo");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }

    public static boolean dropTable(Statement stmt) {
        try {
            stmt.executeUpdate("DROP TABLE Order");
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        }
        return true;
    }
}
