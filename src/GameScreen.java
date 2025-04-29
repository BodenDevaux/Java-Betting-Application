import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GameScreen {
    private JFrame gameScreen;

    private JTextField betArea;
    private JButton betButton;
    private JTextArea result;
    private JTextArea playscore;

    public GameScreen(){
        gameScreen = new JFrame();
        betArea = new JTextField("enter bet here");
        result = new JTextArea();
        playscore = new JTextArea();
        betButton = new JButton("place bet");
    }

    public void initializeGameScreen(){
        gameScreen.setSize(500,500);
        gameScreen.setVisible(true);
        gameScreen.setLayout(new GridLayout(4,1,2,2));
        gameScreen.add(playscore);
        gameScreen.add(result);
        gameScreen.add(betArea);
        gameScreen.add(betButton);
    }

    public void betButtonActionListener(ActionListener actionListener){
        betButton.addActionListener(actionListener);
    }

    public String getUserBet(){
        return betArea.getText();
    }
}
