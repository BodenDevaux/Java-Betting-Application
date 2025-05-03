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
    private static Game game = new Game();

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
            boolean ans = model.Login(userInfo);
            while(true){
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                if (ans) {
                    out.println("success");
                    break;
                }
                else{
                    out.println("fail");
                }
            }
            while(true){

                String bet;
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    bet = reader.readLine();
                    String result = game.coinBet(bet);
                    server.sendUserResult(clientSocket,result);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

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

    public void sendUserResult(Socket clientSocket,String result) {
        try {
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            out.println(result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
