package service;

import model.OrderRecord;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderRecordService {
    protected static boolean createTable(Statement stmt) {
        String createTableOrderRecord =
                "create table OrderRecord("
                        + "  Quantity int not null,"
                        + "  OrderId int,"
                        + "  UnitSellPrice decimal(19,2) not null,"
                        + "  ProductSKU varchar(16),"
                        + "	 foreign key (ProductSKU) references Product (SKU) on delete cascade,"
                        + "  foreign key (OrderId) references OrderTable (OrderId) on delete cascade,"
                        + "  primary key (ProductSKU, OrderId),"
                        + "  check(Quantity >= 0)"
                        + ")";

        try {
            stmt.executeUpdate(createTableOrderRecord);
            System.out.println("Created table OrderRecord");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }

    protected static boolean insert(Connection conn, OrderRecord orderRecord) {
        try {
            // insert order record
            PreparedStatement insertRowOrderRecord =
                    conn.prepareStatement("insert into OrderRecord values(?,?,?,?)");
            try {
                insertRowOrderRecord.setInt(1, orderRecord.getNumUnits());
                insertRowOrderRecord.setInt(2, orderRecord.getOrderId());
                insertRowOrderRecord.setDouble(3, orderRecord.getUnitPrice());
                insertRowOrderRecord.setString(4, orderRecord.getSku());
                insertRowOrderRecord.execute();
            } catch (SQLException e) {
                System.err.println(e.getMessage());
                return false;
            }
            insertRowOrderRecord.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * Gets an order record by the order ID and the product SKU.
     * @param conn the Connection object.
     * @param orderId the order ID
     * @param sku the product SKU.
     * @return the order record, null if not found.
     */
    public static OrderRecord getById(Connection conn, int orderId, String sku) {
        try {
            PreparedStatement sql = conn.prepareStatement(
                    "select * from OrderRecord where OrderId = ? and ProductSKU = ?");
            sql.setInt(1, orderId);
            sql.setString(2, sku);
            ResultSet rs = sql.executeQuery();
            rs.next();
            OrderRecord orderRecord = new OrderRecord(rs.getInt(2), rs.getInt(1),
                    rs.getDouble(3), rs.getString(4));
            rs.close();
            return orderRecord;
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        }
    }

    /**
     * Gets all order records.
     * @param conn the Connection object.
     * @return all order records.
     */
    public static List<OrderRecord> getAll(Connection conn) {
        List<OrderRecord> orderRecords = new ArrayList<>();
        try (
                Statement stmt = conn.createStatement();
        ) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM OrderRecord ORDER BY orderId");
            while (rs.next()) {
                String name = rs.getString(1);
                String description = rs.getString(2);
                String sku = rs.getString(3);
                orderRecords.add(new OrderRecord(
                        rs.getInt(2), rs.getInt(1), rs.getDouble(3), rs.getString(4)));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderRecords;
    }

}
