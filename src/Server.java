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
    //private static Game game = new Game(); //moved game to after login

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
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            String userInfo = reader.readLine();
            String parts[] = userInfo.split(",");
            //boolean ans = authenticatemodel.Login(userInfo);
            boolean ans = false;
            if (parts[0].equals("create")) {
                String credentials = parts[1] + "," + parts[2];
                authenticatemodel.addUser(credentials);
                ans = authenticatemodel.Login(credentials);
                if (ans) {
                    out.println("Created");
                } else {
                    out.println("shouldent happen");
                    return;
                }
            }else {
                ans = authenticatemodel.Login(userInfo);
                while (true) {
                    if (ans) {
                        out.println("success");
                        break;
                    } else {
                        out.println("fail");
                    }
                }
            }
            Player player = authenticatemodel.getPlayer();
            Game game = new Game(player);
            while(true){

                String bet;
                try {
                    bet = reader.readLine();
                    String[] info = bet.split(",");
                    String result;
                    if (info[0].equals("dice")) {
                        result = game.diceBet(bet);
                        //server.sendUserResult(clientSocket,result);
                    } else if (info[0].equals("coin")) {
                        result = game.coinBet(bet);
                        //server.sendUserResult(clientSocket,result);}
                    }else if(info[0].equals("leaderboard")){
                        List<String> leaderboardList = game.getLeaderBoard();
                        result = String.join(",", leaderboardList);
                        //System.out.println(result);
                    } else {
                        result = "Invalid bet type.";
                    }
                    out.println(result);

                } catch (IOException e) {
                    System.out.println("Connection lost with client: " + clientSocket.getPort());
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
