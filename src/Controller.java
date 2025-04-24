import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Controller {
    private Socket socket;
    private BufferedReader bufferedReader;
    private static Server server;

    public Controller(){
        server = new Server();
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    }

    public static void main(String[] args) throws IOException {
        Socket socket;

        try {socket = new Socket("localhost",5000);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Controller controller = new Controller();
        System.out.print("Enter a 1 or 0(1=heads,tails=0),bet amount:");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String bet = bufferedReader.readLine();
        controller.serverSend(socket,bet);
        System.out.println("Enter a bet:");
        bet = bufferedReader.readLine();

        System.out.print("Enter Username: ");
        String username = bufferedReader.readLine();
        System.out.print("Enter Password: ");
        String password = bufferedReader.readLine();
        System.out.println("username:"+ username + " password: "+ password);
        server.addTable();
        server.addUser(username, password);
        System.out.print("Enter Login Username: ");
        String loginusername = bufferedReader.readLine();
        System.out.print("Enter Login Password: ");
        String loginpassword = bufferedReader.readLine();
        server.Login(loginusername, loginpassword);
        
    }

    public void serverSend(Socket socket, String bet){
        try {
            PrintWriter serverIn = new PrintWriter(socket.getOutputStream(), true);
            serverIn.println(bet);
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }



}
