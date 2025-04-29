import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Controller {

    private Socket socket;
    private View view = new View();
    private BufferedReader reader;

    public Controller(View view){

        try {socket = new Socket("localhost",5000);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }catch(IOException e){
            throw new RuntimeException(e);
        }

        this.view = view;
        view.loginButtonActionListener(new ActionListenerLoginButton());
        view.setCreateAccountPageButton(new ActionListenerCreateAccountPage());
        view.createAccountActionListener(new ActionListenerCreateAccountButton());
        view.betButtonActionListen((new ActionListenerBetButton()));
        view.initializeLoginScreen();
    }

    public static void main(String[] args) throws IOException {
        Socket socket;
        View view = new View();
        Controller controller = new Controller(view);
        String ans;
        try{
            ans = controller.reader.readLine();
            System.out.println(ans);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
        if(ans.equals("success")){
            controller.initializeGame();
        }



    }

    public void serverSend(String bet){
        try {
            PrintWriter serverIn = new PrintWriter(socket.getOutputStream(), true);
            serverIn.println(bet);
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    private class ActionListenerLoginButton implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String info = view.getUserInformation();
            serverSend(info);

        }
    }

    private class ActionListenerCreateAccountPage implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            view.closeLoginScreen();
            view.initializeCreateScreen();
        }
    }


    private class ActionListenerCreateAccountButton implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String info = view.getUserInformation();
            serverSend(info);
        }
    }

    private class ActionListenerBetButton implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String bet = view.getUsrBet();
            serverSend(bet);
        }
    }

    public void initializeGame(){
        view.initializeGameScreen();
    }
}
