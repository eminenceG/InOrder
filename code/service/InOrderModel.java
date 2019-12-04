package service;

import model.Product;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * The driver class of InOrder.
 */
public class InOrderModel {
    private Connection conn;

    /**
     * Constructs an InOrder instance.
     * @throws SQLException When SQL problems occur.
     */
    public InOrderModel() throws SQLException {
        // set up the connection to the database
        String protocol = "jdbc:derby:";
        String dbName = "InOrder";
        String connectionSetUp = protocol + dbName + ";create=true";

        Properties props = new Properties();
        props.put("user", "user1");
        props.put("password", "user1");

        conn = DriverManager.getConnection(connectionSetUp, props);
    }

    public Connection getConnection() {
        return conn;
    }

    /**
     * Initializes the database.
     * @throws SQLException When SQL problems occur.
     */
    public Connection init() throws SQLException {
        Statement stmt = conn.createStatement();
        String[] tableName = {
                "InventoryRecord", "OrderRecord", "OrderTable", "Product", "Customer"
        };

        // drop old triggers
        InOrderTriggers.dropTriggers(stmt);

        // drops old tables
        for (String tbl : tableName) {
            try {
                stmt.executeUpdate("drop table " + tbl);
                System.out.println("Dropped table " + tbl);
            } catch (SQLException ex) {
                System.err.println("Did not drop table " + tbl + " " + ex.getMessage());
            }
        }

        // drops old stored functions and creates new ones.
        String[] storedFunctions = InOrderFunctions.functions;
        for (String func : storedFunctions) {
            InOrderFunctions.dropFunction(stmt, func);
            InOrderFunctions.createFunction(stmt, func);
        }

        // creates new tables
        ProductService.createTable(stmt);
        InventoryRecordService.createTable(stmt);
        CustomerService.createTable(stmt);
        OrderService.CreateTable(stmt);
        OrderRecordService.createTable(stmt);

        // creates triggers
        InOrderTriggers.createTrigger(stmt);



        return this.conn;
    }

    public static void main(String[] args) {
        Connection conn = null;
        try {
            InOrderModel driver = new InOrderModel();
            conn = driver.init();
        } catch (SQLException e) {
            System.out.println("No Database Connection");
        }

        Product p0 = new Product("apple", "apple", "AB-123456-0N");
        ProductService.insert(conn, p0);
        Product p = ProductService.getProductBySku(conn, "AB-123456-0N");
        System.out.println(p.getDescription());
    }
}
