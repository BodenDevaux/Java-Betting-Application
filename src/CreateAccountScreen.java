import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class CreateAccountScreen {
    private JFrame createAccountScreen;

    private JTextField usernameField;
    private JTextField passwordField;

    private JButton createAccountButton;

    public CreateAccountScreen(){
        createAccountScreen = new JFrame();
        usernameField = new JTextField();
        passwordField = new JTextField();
        createAccountButton = new JButton();
        usernameField.setPreferredSize(new Dimension(200,30));
        passwordField.setPreferredSize(new Dimension(200, 30));
        createAccountButton = new JButton("create account");

    }

    public void intailizeCreateAccountScreen(){
        createAccountScreen.setSize(500,500);
        createAccountScreen.setVisible(true);
        createAccountScreen.setLayout(new GridLayout(2,2,2,2));
        createAccountScreen.add(usernameField);
        createAccountScreen.add(passwordField);
        createAccountScreen.add(createAccountButton);
    }

    public String newUserInfo (){
        String username = usernameField.getText();
        String password = passwordField.getText();
        return username + "," + password;
    }

    public void createAccountButtonActionListener(ActionListener actionListener){
        createAccountButton.addActionListener(actionListener);
    }
}
