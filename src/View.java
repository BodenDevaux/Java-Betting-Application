import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class View {

    private LoginScreen loginScreen;
    private CreateAccountScreen createAccountScreen;
    private GameScreen gameScreen;

    public View() {
        loginScreen = new LoginScreen();
        createAccountScreen = new CreateAccountScreen();
        gameScreen = new GameScreen();
    }



    //create account screen
    public void initializeCreateScreen(){
        createAccountScreen.intailizeCreateAccountScreen();
    }

    public void createAccountActionListener(ActionListener actionListener){
        createAccountScreen.createAccountButtonActionListener(actionListener);
    }
    public void createBackButtonActionListener(ActionListener actionListener){createAccountScreen.backButtonActionListener(actionListener);}

    public void closeCreateAccoutScreen(){
        createAccountScreen.closeCreateScreen();
    }

    public String createUserInformation(){
        return CreateAccountScreen.newUserInfo();
    }

    /// ///////////////////////////////////////
    //login screen
    public void initializeLoginScreen(){
        loginScreen.IntailizeLoginScreen();
    }

    public void closeLoginScreen(){
        loginScreen.closeLoginScreen();
    }

    public String getUserInformation(){
        return loginScreen.getUserInformation();
    }

    public void loginButtonActionListener(ActionListener actionListener){
        loginScreen.loginButtonActionListener(actionListener);
    }

    public void setCreateAccountPageButton(ActionListener actionListener){
        loginScreen.setCreateAccountPageButton(actionListener);
    }

    /// //////////// / ////
    //game screen

    public void initializeGameScreen(){
        loginScreen.closeLoginScreen();
        createAccountScreen.closeCreateScreen();
        gameScreen.initializeGameScreen();
    }

    public void betButtonActionListen(ActionListener actionListener){
        gameScreen.betButtonActionListener(actionListener);
    }
    public String getUsrDiceBet(){
        return gameScreen.getUserDiceBet();
    }

    public String getUsrCoinBet(){
        return gameScreen.getUserCoinBet();
    }
    public void setGameResult(String result) {
        gameScreen.getGameResult(result);
    }

    public void setScore(String score){
        gameScreen.setScore(score);
    }


    public void diceGameButtonActionListener(ActionListener actionListener){gameScreen.diceGameButtonActionListener(actionListener);}
    public void coinGameButtonActionListener(ActionListener actionListener){gameScreen.coinGameButtonActionListener(actionListener);}
    public void leaderboardButtionActionListener(ActionListener actionListener){gameScreen.leaderboardButtonActionListener(actionListener);}

    public void showCoinGame(){
        gameScreen.displayCoinGame();
    }

    public void showDiceGame(){
        gameScreen.displayDiceGame();
    }
    public void showLeaderboard(String leaderboard){
        gameScreen.displayLeaderboard(leaderboard);
    }
}
