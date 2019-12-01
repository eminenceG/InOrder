package service;

import model.OrderRecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

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
}
