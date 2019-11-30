import java.sql.SQLException;
import java.sql.Statement;

/**
 * This class provides methods to operate the OrderRecord table. OrderRecord is the record for an item in the order. it
 * includes the order ID, the number of units, and the unit price. The item must be available and the inventory is automatically reduced when an order record is cretated for an order.
 */
public class OrderRecord {

    public static boolean setCreateTable(Statement stmt) {
        String createTable_OrderRecord =
                "CREATE TABLE OrderRecord("
                        + "  Quantity int not null CHECK (Quantity >= 0),"
                        + "  OrderId varchar(16),"
                        + "  UnitSellPrice decimal(19,2) not null CHECK (UnitSellPrice >= 0), "
                        + "  ProductSKU varchar(16),"
                        + "	 foreign key (ProductSKU) references Product (SKU) on delete cascade,"
                        + "  foreign key (OrderId) references OrderInfo (OrderId) on delete cascade,"
                        + "  primary key (ProductSKU, OrderId)"
                        + ")";
        try {
            stmt.executeUpdate(createTable_OrderRecord);
            System.out.println("Created table OrderRecord");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }
}
