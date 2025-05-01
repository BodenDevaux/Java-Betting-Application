import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GameScreen {
    private JFrame gameScreen;

    private JTextField betArea;
    private JButton betButton;
    private JTextArea result;
    private JTextArea playscore;

    private JRadioButton headsButton;
    private JRadioButton tailsButton;
    private ButtonGroup guessGroup;

    public GameScreen(){
        gameScreen = new JFrame();
        betArea = new JTextField("enter bet here");
        result = new JTextArea();
        playscore = new JTextArea();

        headsButton = new JRadioButton("Heads");
        tailsButton = new JRadioButton("Tails");
        guessGroup = new ButtonGroup();
        guessGroup.add(headsButton);
        guessGroup.add(tailsButton);

        betButton = new JButton("place bet");
    }

    public void initializeGameScreen(){
        gameScreen.setSize(500,500);
        gameScreen.setLayout(new GridLayout(5,1,2,2));
        gameScreen.add(playscore);
        gameScreen.add(result);
        gameScreen.add(betArea);


        //add radio
        JPanel guessPanel = new JPanel();
        guessPanel.add(new JLabel("Choose Heads or Tails:"));
        guessPanel.add(headsButton);
        guessPanel.add(tailsButton);
        gameScreen.add(guessPanel);
        gameScreen.add(betButton);
        gameScreen.setVisible(true);
    }

    public void betButtonActionListener(ActionListener actionListener){
        betButton.addActionListener(actionListener);
    }

    public String getUserBet(){
        String guess = "";
        if(headsButton.isSelected()){
            guess = "1";
        }else if(tailsButton.isSelected()){
            guess = "2";
        }else{
            return "Please Enter A Valid Guess";
        }
        String betAmount = betArea.getText();
        return guess + "," + betAmount;
    }

    public void getGameResult(String resultText){
        result.setText(resultText);
    }
    public void setScore(String score){
        playscore.setText("Score: " + score);
    }

}
