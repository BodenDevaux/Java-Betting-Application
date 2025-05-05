import java.io.IOException;
import java.net.Socket;

public class Client {

    public static void main(String[] args) throws IOException {
        View view = new View();
        Controller controller = new Controller(view);
    }
}
