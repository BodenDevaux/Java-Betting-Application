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
    public BufferedReader reader;
    private String gameType ="";

    public Controller(View view){

        try {
            socket = new Socket("localhost",5000);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        this.view = view;
        view.loginButtonActionListener(new ActionListenerLoginButton());
        view.setCreateAccountPageButton(new ActionListenerCreateAccountPage());
        view.createAccountActionListener(new ActionListenerCreateAccountButton());
        view.betButtonActionListen((new ActionListenerBetButton()));
        view.coinGameButtonActionListener(new ActionListenerCoinGameButton());
        view.diceGameButtonActionListener(new ActionListenerDiceGameButton());
        view.leaderboardButtionActionListener(new ActionListenerLeaderboardButton());
        view.createBackButtonActionListener(new ActionListenerCreateBackButton());
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
            return reader.readLine();
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
            screenResult = "loss";
        }
        view.setGameResult(screenResult);
        view.setScore(res[2]);

    }


    private class ActionListenerLoginButton implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String info = view.getUserInformation();
            serverSend(info);

            String response = serverRecieve();
            if(response.equals("success")){
                view.initializeGameScreen();
                //view.setScore();
            }else{
                System.out.println("it browken");
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
            System.out.println(info);
            serverSend(info);
            String response = serverRecieve();
            if(response.equals("Created")){
                view.initializeGameScreen();
            }
        }
    }

    private class ActionListenerBetButton implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(gameType.equals("coin")){
                String bet = view.getUsrCoinBet();
                String final_bet = gameType + "," + bet;
                serverSend(final_bet);
                String results = serverRecieve();
                System.out.println(results);
                processResult(results);
            }
            else if(gameType.equals("dice")){
                String bet = view.getUsrDiceBet();
                String final_bet = gameType + "," + bet;
                serverSend(final_bet);
                String results = serverRecieve();
                System.out.println(results);
                processResult(results);
            }else{
                view.setGameResult("Please Select A Game Type!");
            }

        }
    }

    private class ActionListenerDiceGameButton implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            view.showDiceGame();
            gameType = "dice";
        }
    }

    private class ActionListenerCoinGameButton implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            view.showCoinGame();
            gameType ="coin";
        }
    }
    private class ActionListenerLeaderboardButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            serverSend("leaderboard");
            String leaderboard = serverRecieve();
            view.showLeaderboard(leaderboard);
        }
    }

    private class ActionListenerCreateBackButton implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            view.closeCreateAccoutScreen();
            view.initializeLoginScreen();
        }
    }
}
