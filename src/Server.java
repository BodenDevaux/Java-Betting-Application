import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.Random;

public class Server {
    private Connection connection;
    public int score = 100;
    private String loggedinUser;
    private int loggedinID;
    public Server(){
        try {
            this.connection = DBConnection.getConnection();
            System.out.println("Connection Successful");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public static void main(String[] args) throws IOException {
        Server server = new Server();
        ServerSocket serverSocket = new ServerSocket(5000);
        System.out.println("server started waiting for clients...");

        while(true){
            Socket clientSocket = serverSocket.accept();
            System.out.println("client connected: " + clientSocket.getPort());
            String userInfo= server.getUserInput(clientSocket);
            String[]info = userInfo.split(",");
            boolean ans = server.Login(info[0],info[1]);
            if(ans){
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                out.println("success");
            }

        }

    }

    public String getUserInput(Socket clientSocket){
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String inputLine= reader.readLine();
           return inputLine;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String game(String bet){
        String[] vals = bet.split(",");
        System.out.println("vals[1]: "+ vals[0] + "  vals[2]: " + vals[1]);

        Random random = new Random();
        double randNUM = random.nextDouble();
        System.out.println(randNUM);
        int flip = 0;

        if(randNUM >= 0.50){
            flip = 1;
            //heads
        }else{
            flip =0;
            //tails
        }

        int guess = Integer.parseInt(vals[1]);
        if(guess == flip){
            //player win
            System.out.println("win");
            score += Integer.parseInt(vals[1]);
            createScore(score);
            updateScore(score);
            return "You win!";
        }else{
            //player loss
            System.out.println("loss");
            score -= Integer.parseInt(vals[1]);
            createScore(score);
            updateScore(score);
            return "You lose!";
        }


        //return bet;
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

    public void addUser(String username, String password){

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
    public boolean Login(String username, String password) {
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
