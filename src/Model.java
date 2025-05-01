import java.sql.*;

public class Model {

    private Connection connection;
    private static String loggedinUser;
    private static int loggedinID;

    public Model(){
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
    public void createScore(int score){
        String cmd = "CREATE TABLE IF NOT EXISTS scores(" +
                "score_id INTEGER PRIMARY KEY," +
                "user_id INTEGER," +
                "username TEXT NOT NULL," +
                "score INTEGER," +
                "FOREIGN KEY(user_id) REFERENCES users(user_id)" +
                ");";
        try (Statement statement = connection.createStatement()) {
            statement.execute(cmd);
            System.out.println(score);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public void updateScore(int score){
        String cmd = "SELECT * FROM scores WHERE user_id = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(cmd);
            preparedStatement.setInt(1, loggedinID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                String update = "UPDATE scores SET score = ? WHERE user_id = ?";
                PreparedStatement preparedStatement1 = connection.prepareStatement(update);
                preparedStatement1.setInt(1, score);
                preparedStatement1.setInt(2, loggedinID);
                preparedStatement1.executeUpdate();
            }else{
                String add = "INSERT INTO scores (user_id, username, score) VALUES (?, ?, ?)";
                PreparedStatement preparedStatement1 = connection.prepareStatement(add);
                preparedStatement1.setInt(1, loggedinID);
                preparedStatement1.setString(2, loggedinUser);
                preparedStatement1.setInt(3, score);
                preparedStatement1.executeUpdate();

            }
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

}
