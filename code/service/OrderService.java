package service;

import model.Customer;
import model.Order;
import model.OrderRecord;
import model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class contains order related methods.
 */
public class OrderService {
    protected static boolean CreateTable(Statement stmt) {
        String createTableOrder =
                "create table OrderTable("
                        + "  CustomerId INT,"
                        + "  OrderId INT,"
                        + "  OrderDate date not null,"
                        + "  ShipmentDate date,"
                        + "  primary key (OrderId),"
                        + "  foreign key (CustomerId) references Customer (CustomerId) on delete cascade,"
                        + "  check (OrderId > 0)"
                        + ")";
        try {
            stmt.executeUpdate(createTableOrder);
            System.out.println("Created table OrderTable");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * Inserts a new record.
     * @param conn the Connection object.
     * @param order the order.
     * @param orderRecordList list of order records.
     * @return true if inserted successfully, otherwise false.
     */
    public static boolean insert(Connection conn, Order order, List<OrderRecord> orderRecordList) {
        try {
            conn.setAutoCommit(false);
            Savepoint sp = conn.setSavepoint("Insert Order");
            PreparedStatement insertRow_Order =
                    conn.prepareStatement("insert into OrderTable values(?,?,?,?)");
            try {
                insertRow_Order.setInt(1, order.getCustomerId());
                insertRow_Order.setInt(2, order.getOrderId());
                insertRow_Order.setDate(3, order.getOrderDate());
                insertRow_Order.setDate(4, null);
                insertRow_Order.execute();
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
            insertRow_Order.close();
            if (orderRecordList != null && !orderRecordList.isEmpty()) {
                for (OrderRecord orderRecord : orderRecordList) {
                    boolean inserted = OrderRecordService.insert(conn, orderRecord);
                    if (!inserted) {
                        conn.rollback(sp);
                        break;
                    }
                }
            } else {
                System.out.println("Failed to place the order. Order without records is not permitted.");
                conn.rollback(sp);
                conn.setAutoCommit(true);
                return false;
            }
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * Ships the order by updating the ShipmentDate in the OrderTable table.
     * @param conn the Connection object.
     * @param orderId the orderID.
     * @param shipDate the ship date.
     * @return true if updated successfully, otherwise false.
     */
    public static boolean shipOrder(Connection conn, int orderId, Date shipDate) {
        try {
            PreparedStatement updateRow =
                    conn.prepareStatement("UPDATE OrderTable SET ShipmentDate = ? where OrderId = ?");
            try {
                updateRow.setDate(1, shipDate);
                updateRow.setInt(2, orderId);
                updateRow.execute();
            } catch (SQLException ex) {
                System.out.println("Did not updateById the row. " + ex.getMessage());
            }
            updateRow.close();
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    /**
     * Gets an order by its ID.
     *
     * @param conn the Connection object.
     * @param orderId the order Id.
     * @return the order, null if not found.
     */
    public static Order getById(Connection conn, int orderId) {
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

    /**
     * Gets all records.
     * @param conn the Connection object.
     * @return all records as a List.
     */
    public static List<Order> getAll(Connection conn) {
        List<Order> orders = new ArrayList<>();
        try (
                Statement stmt = conn.createStatement();
        ) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM OrderTable ORDER BY OrderId");
            while (rs.next()) {
                orders.add(new Order(rs.getInt(1), rs.getInt(2), rs.getDate(3), rs.getDate(4)));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }
}
