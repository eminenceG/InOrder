package service;

import java.sql.SQLException;
import java.sql.Statement;

public class InOrderTriggers {
    private static final String createOrderRecordTrigger =
            "create trigger CreateOrderRecord"
                    + "	 after insert on OrderRecord"
                    + "  referencing new as insertedRow"
                    + "  for each row MODE DB2SQL"
                    + "  UPDATE InventoryRecord"
                    + "  set QuantityInStock = QuantityInStock - insertedRow.Quantity"
                    + "  where InventoryRecord.ProductSKU = insertedRow.ProductSKU";


    private static final String deleteOrderRecordTrigger =
            "create trigger DeleteOrderRecord"
                    + "	 after delete on OrderRecord"
                    + "  referencing old as deletedRow"
                    + "  for each row MODE DB2SQL"
                    + "  UPDATE InventoryRecord"
                    + "  set QuantityInStock = QuantityInStock + deletedRow.Quantity"
                    + "  where InventoryRecord.ProductSKU = deletedRow.ProductSKU";

    protected static void createTrigger(Statement stmt) {
        try {
            stmt.executeUpdate(createOrderRecordTrigger);
            stmt.executeUpdate(deleteOrderRecordTrigger);
            System.out.println("Created Trigger CreateOrderRecord, DeleteOrderRecord");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    protected static void dropTriggers(Statement stmt) {
        String[] triggerName = {
                "CreateOrderRecord", "DeleteOrderRecord"
        };

        for (String tgr : triggerName) {
            try {
                stmt.executeUpdate("drop trigger " + tgr);
                System.out.println("Dropped trigger " + tgr);
            } catch (SQLException ex) {
                System.out.println("Did not drop trigger " + tgr);
            }
        }
    }
}
