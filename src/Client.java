import java.io.IOException;
import java.net.Socket;

public class Client {

    public static void main(String[] args) throws IOException {
        Socket socket;
        View view = new View();
        Controller controller = new Controller(view);
        String ans;
        try{
            ans = controller.reader.readLine();
            //System.out.println(ans);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
        if(ans.equals("success")){
            controller.initializeGame();
        }



    }
}
