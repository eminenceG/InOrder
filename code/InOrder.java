import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * The driver class of InOrder.
 */
public class InOrder {
    private Connection conn;

    /**
     * Constructs an InOrder instance.
     * @throws SQLException When SQL problems occur.
     */
    InOrder () throws SQLException {
        // set up the connection to the database
        String protocol = "jdbc:derby:";
        String dbName = "orderManager";
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
    public void init() throws SQLException {
        Statement stmt = conn.createStatement();

        boolean dropped;
        // drops old tables
        dropped = Product.dropTable(stmt);
        System.out.println(dropped);

        // creates new tables
        boolean created;
        created = Product.createTable(stmt);
        System.out.println(created);
    }

    public static void main(String[] args) {
        try {
            InOrder driver = new InOrder();
            driver.init();
        } catch (SQLException e) {
            System.out.println("No Database Connection");
        }
    }
}
