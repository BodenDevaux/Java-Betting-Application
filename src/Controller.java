import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

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
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        Controller controller = new Controller();

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

        System.out.print("Enter a 1 or 0(1=heads,tails=0),bet amount:");
        String bet = bufferedReader.readLine();
        controller.serverSend(socket,bet);
        BufferedReader out = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String result = out.readLine();
        System.out.println("Game Result: " + result);
        //System.out.println("Enter a bet:");
        //bet = bufferedReader.readLine();
        
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
