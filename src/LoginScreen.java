import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginScreen {
    private JFrame loginScreen;

    private JTextField usernameField;
    private JTextField passwordField;

    private JButton loginButton;
    private JButton createAccountPageButton;

    public LoginScreen(){
        loginScreen = new JFrame();
        passwordField = new JTextField();
        usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(200,30));
        passwordField.setPreferredSize(new Dimension(200, 30));
        loginButton = new JButton("Login");
        createAccountPageButton = new JButton("create account");
    }

    public void IntailizeLoginScreen(){
        loginScreen.setSize(500,500);
        loginScreen.setVisible(true);
        loginScreen.setLayout(new GridLayout(2,2,2,2));
        loginScreen.add(usernameField);
        loginScreen.add(passwordField);
        loginScreen.add(loginButton);
        loginScreen.add(createAccountPageButton);
    }

    public void closeLoginScreen(){
        loginScreen.dispose();
    }

    public String getUserInformation(){
        String username = usernameField.getText();
        String password = passwordField.getText();
        return username + "," + password;
    }

    public void loginButtonActionListener(ActionListener actionListener){
        loginButton.addActionListener(actionListener);
    }

    public void setCreateAccountPageButton(ActionListener actionListener){
        createAccountPageButton.addActionListener(actionListener);
    }
}
