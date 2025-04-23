import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Controller {
    private Connection connection;
    public Controller(){
        try {
            this.connection = DBConnection.getConnection();
            System.out.println("Connection Successful");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void addTable(){
        String cmd = "CREATE TABLE IF NOT EXISTS users(" +
                "user_id INTEGER PRIMARY KEY," +
                "username TEXT NOT NULL" +
                "password TEXT NOT NULL" +
                ");";


        try (Statement statement = connection.createStatement()) {
            statement.execute(cmd);
            System.out.println("Table Create or Alreaady exists");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void addUser(String username, String password){

    }
}
