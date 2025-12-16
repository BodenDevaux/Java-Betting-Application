import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginScreen {
    private JFrame loginScreen;

    private JTextField usernameField;
    private JTextField passwordField;

    private JButton loginButton;
    private JButton createAccountPageButton;

    private JPanel usernamePanel;
    private JPanel passwordPanel;
    private JLabel usernameLabel;
    private JLabel passwordLabel;

    public LoginScreen(){
        loginScreen = new JFrame();
        passwordField = new JTextField();
        usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(200,30));
        passwordField.setPreferredSize(new Dimension(200, 30));
        loginButton = new JButton("Login");
        createAccountPageButton = new JButton("create account");
        usernamePanel = new JPanel();
        usernameLabel = new JLabel("Enter Username:");
        passwordPanel = new JPanel();
        passwordLabel = new JLabel("Enter Password:");
    }

    public void IntailizeLoginScreen(){
        loginScreen.setSize(500,500);
        loginScreen.setVisible(true);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(25,10,25,10);
        loginScreen.setLayout(new GridBagLayout());

        gbc.gridx = 0;
        gbc.gridy =0;
        loginScreen.add(usernameLabel,gbc);

        gbc.gridx = 1;
        gbc.gridy =0;
        loginScreen.add(usernameField,gbc);

        gbc.gridx = 3;
        gbc.gridy =0;
        gbc.weightx = .5;
        loginScreen.add(passwordLabel,gbc);

        gbc.gridx = 4;
        gbc.gridy =0;
        loginScreen.add(passwordField,gbc);

        gbc.gridx = 0;
        gbc.gridy =1;
        gbc.weightx = .5;
        gbc.gridwidth =2;
        gbc.fill = GridBagConstraints.BOTH;
        loginScreen.add(loginButton,gbc);

        gbc.gridx = 3;
        gbc.gridy =1;
        gbc.gridwidth =2;
        gbc.fill = GridBagConstraints.BOTH;
        loginScreen.add(createAccountPageButton,gbc);

        /*
        usernamePanel.add(usernameLabel);
        usernamePanel.add(usernameField);
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);
        loginScreen.add(usernamePanel);
        loginScreen.add(passwordPanel);
        loginScreen.add(loginButton);
        loginScreen.add(createAccountPageButton);
        */
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
