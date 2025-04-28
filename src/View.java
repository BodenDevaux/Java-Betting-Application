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
        gameScreen.initializeGameScreen();
    }

}
