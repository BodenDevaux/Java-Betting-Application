import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class authenticateModel {
    private Connection connection;
    private String loggedinUser;
    private int loggedinID;
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
        //String password = info[1];
        String password = hashPassword(info[1]);

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
        //String password = userinfo[1];
        String password = hashPassword(userinfo[1]);
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
    //add password hashing
    //https://howtodoinjava.com/java/java-security/how-to-generate-secure-password-hash-md5-sha-pbkdf2-bcrypt-examples/
    private static String hashPassword(String passwordToHash){
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(passwordToHash.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }
}
