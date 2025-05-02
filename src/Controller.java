import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Controller {
    private Model model = new Model();
    private Socket socket;
    private View view = new View();
    public BufferedReader reader;

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
        System.out.println("Hello from debug2");
        view.betButtonActionListen((new ActionListenerBetButton()));
        view.initializeLoginScreen();
    }


    public void serverSend(String bet){
        try {
            PrintWriter serverIn = new PrintWriter(socket.getOutputStream(), true);
            serverIn.println(bet);
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    public String serverRecieve(){
        try {
            String result = reader.readLine();
            return result;
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    public void processResult(String result){
        String [] res = result.split(",");
        System.out.println("res[0]"+res[0]+"  res[1]:"+res[1]);

        String screenResult;
        if(res[0].equals("win")){
            screenResult = "win";
            System.out.println("win");
        }else{
            System.out.println("loss");
            screenResult = "=loss";
        }
        view.setGameResult(screenResult);
        view.setScore(res[2]);

    }


    private class ActionListenerLoginButton implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String info = view.getUserInformation();
            boolean check = model.Login(info);
            if(check){
                serverSend(info);
            }else{
                System.out.println("fail");
            }
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
            String info = view.createUserInformation();
            model.addUser(info);
            serverSend(info);
        }
    }

    private class ActionListenerBetButton implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String bet = view.getUsrBet();
            serverSend(bet);
            String s = serverRecieve();
            System.out.println(s);
            processResult(s);
        }
    }

    public void initializeGame(){
        view.initializeGameScreen();
    }
}
