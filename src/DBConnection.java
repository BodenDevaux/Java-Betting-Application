import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {

    private static final String URI = "jdbc:sqlite:users.db";
    //private static Connection connection;

    public static Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(URI);

        try (Statement statement = connection.createStatement()) {
            statement.execute("PRAGMA journal_mode=WAL");
            statement.execute("PRAGMA busy_timeout = 5000");
        }



        return connection;

    }
/*
    public static void closeConnection() throws SQLException{
        connection.close();
    }
*/
}
