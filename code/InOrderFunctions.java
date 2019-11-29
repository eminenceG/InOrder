import java.sql.SQLException;
import java.sql.Statement;

/**
 * This class contains methods to create stored functions for the InOrder database.
 */
public class InOrderFunctions {

    public static String[] functions = {"isSku"};

    // Stored Function: isSKU
    public static boolean isSku(String sku) {
        return sku.matches("([A-Z]{2})-([0-9]{6})-([0-9A-Z]{2})");
    }

    private static boolean createFunctionIsSku(Statement stmt) {
        String functionIsSku =
                "Create function isSKU("
                        + "  SKU varchar(16)"
                        + ") returns boolean"
                        + " PARAMETER STYLE JAVA"
                        + " LANGUAGE JAVA"
                        + " DETERMINISTIC"
                        + " NO SQL"
                        + " EXTERNAL NAME"
                        + " 'InOrderFunctions.isSku'";
        try {
            stmt.executeUpdate(functionIsSku);
            System.out.println("Created function isSKU");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }

    // Stored Function:

    public static boolean createFunction(Statement stmt, String funcName) {
        switch (funcName) {
            case "isSku":
                return createFunctionIsSku(stmt);
            default:
                return false;
        }
    }

    public static boolean dropFunction(Statement stmt, String funcName) {
        try {
            stmt.executeUpdate("DROP FUNCTION " + funcName);
            System.out.println("Dropped function " + funcName);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            System.out.println("Did not drop function " + funcName);
            return false;
        }
        return true;
    }
}
