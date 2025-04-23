import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Server {
    static Controller controller = new Controller();
    public static void main(String[] args) throws IOException {
        System.out.println("Enter Username: ");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String username = bufferedReader.readLine();
        System.out.println("Enter Password: ");
        String password = bufferedReader.readLine();
        controller.addTable();


    }
}
