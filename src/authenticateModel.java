import java.sql.*;

public class authenticateModel {
    private Connection connection;
    private static String loggedinUser;
    private static int loggedinID;
    private Player player;
    public authenticateModel(){

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
                "username TEXT NOT NULL," +
                "password TEXT NOT NULL" +
                ");";


        try (Statement statement = connection.createStatement()) {
            statement.execute(cmd);
            System.out.println("Table Create or Already exists");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addUser(String userInfo){
        String[] info = userInfo.split(",");
        String username = info[0];
        String password = info[1];

        String cmd = "INSERT into users (username,password) VALUES(?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(cmd)) {
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,password);
            preparedStatement.executeUpdate();
            System.out.println("User created");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean Login(String info) {
        String[] userinfo = info.split(",");
        String username = userinfo[0];
        String password = userinfo[1];
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                loggedinUser = resultSet.getString("username");
                loggedinID = resultSet.getInt("user_id");
                player = new Player(loggedinUser, loggedinID);
                System.out.println("Login successful for " + loggedinUser + " id - " + loggedinID);
                return true;
            }else{
                System.out.println("Login failed, invalid user or password");
                return false;
            }

        }catch(SQLException e){
            System.out.println("Login error");
        }
        return false;
    }
    public Player getPlayer(){
        return player;
    }
}
