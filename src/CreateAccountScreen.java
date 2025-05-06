import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

public class CreateAccountScreen {
    private JFrame createAccountScreen;

    private static JTextField usernameField;
    private static JTextField passwordField;

    private JButton createAccountButton;
    private JButton backButton;

    private JPanel usernamePanel;
    private JPanel passwordPanel;
    private JLabel usernameLabel;
    private JLabel passwordLabel;

    public CreateAccountScreen(){
        createAccountScreen = new JFrame();
        usernameField = new JTextField();
        passwordField = new JTextField();
        usernameField.setPreferredSize(new Dimension(200,30));
        passwordField.setPreferredSize(new Dimension(200, 30));
        createAccountButton = new JButton("create account");
        backButton = new JButton("go back");
        usernamePanel = new JPanel();
        usernameLabel = new JLabel("Enter Username:");
        passwordPanel = new JPanel();
        passwordLabel = new JLabel("Enter Password:");

    }

    public void intailizeCreateAccountScreen(){
        createAccountScreen.setSize(500,500);
        createAccountScreen.setVisible(true);
        createAccountScreen.setLayout(new GridLayout(2,2,10,10));
        usernamePanel.add(usernameLabel);
        usernamePanel.add(usernameField);
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);
        createAccountScreen.add(usernamePanel);
        createAccountScreen.add(passwordPanel);
        createAccountScreen.add(createAccountButton);
        createAccountScreen.add(backButton);
    }

    public static String newUserInfo(){
        String username = usernameField.getText();
        String password = passwordField.getText();
        System.out.println(username + password);
        return "create," + username + "," + password;
    }

    public void createAccountButtonActionListener(ActionListener actionListener){
        createAccountButton.addActionListener(actionListener);
    }

    public void backButtonActionListener(ActionListener actionListener){backButton.addActionListener(actionListener);}

    public void closeCreateScreen(){
        createAccountScreen.dispose();
    }
}
    