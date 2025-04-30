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
    private static Model model = new Model();
    public int score = 100;
    private String loggedinUser;
    private int loggedinID;

    public Server() {
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

        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("client connected: " + clientSocket.getPort());
            String userInfo = server.getUserInput(clientSocket);
            String[] info = userInfo.split(",");
            boolean ans = model.Login(info[0], info[1]);
            if (ans) {
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                out.println("success");
            }
            String bet;
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                bet = reader.readLine();
                System.out.println(bet);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

    }

    public String getUserInput(Socket clientSocket) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String inputLine = reader.readLine();
            return inputLine;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String game(String bet) {
        String[] vals = bet.split(",");
        System.out.println("vals[1]: " + vals[0] + "  vals[2]: " + vals[1]);

        Random random = new Random();
        double randNUM = random.nextDouble();
        System.out.println(randNUM);
        int flip = 0;

        if (randNUM >= 0.50) {
            flip = 1;
            //heads
        } else {
            flip = 0;
            //tails
        }

        int guess = Integer.parseInt(vals[1]);
        if (guess == flip) {
            //player win
            System.out.println("win");
            score += Integer.parseInt(vals[1]);
            model.createScore(score);
            model.updateScore(score);
            return "You win!";
        } else {
            //player loss
            System.out.println("loss");
            score -= Integer.parseInt(vals[1]);
            model.createScore(score);
            model.updateScore(score);
            return "You lose!";
        }


        //return bet;
    }
}

