import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.List;

public class Server {
    private Connection connection;
    private static authenticateModel authenticatemodel = new authenticateModel();

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

            ThreadMultiClient clientHandler = new ThreadMultiClient(clientSocket);
            Thread thread = new Thread(clientHandler);
            thread.start();
        }
    }


}
