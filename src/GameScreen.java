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
    private ButtonGroup coinGuessGroup;
    private ButtonGroup diceGuesGroup;
    private ButtonGroup gameSelectionGroup;

    private JRadioButton buttonOne;
    private JRadioButton buttonTwo;
    private JRadioButton buttonThree;
    private JRadioButton buttonFour;
    private JRadioButton buttonFive;
    private JRadioButton buttonSix;

    private JPanel coinPanel;
    private  JPanel dicePanel;
    private JPanel gameSelectionPanel;

    private JRadioButton diceGameButton;
    private JRadioButton coinGameButton;


    public GameScreen(){
        gameScreen = new JFrame();
        betArea = new JTextField("enter bet here");
        result = new JTextArea();
        playscore = new JTextArea();

        diceGameButton = new JRadioButton();
        coinGameButton = new JRadioButton();
        gameSelectionGroup = new ButtonGroup();
        gameSelectionGroup.add(diceGameButton);
        gameSelectionGroup.add(coinGameButton);

        headsButton = new JRadioButton("Heads");
        tailsButton = new JRadioButton("Tails");

        coinGuessGroup = new ButtonGroup();
        coinGuessGroup.add(headsButton);
        coinGuessGroup.add(tailsButton);


        diceGuesGroup = new ButtonGroup();
        buttonOne = new JRadioButton("1");
        buttonTwo = new JRadioButton("2");
        buttonThree = new JRadioButton("3");
        buttonFour = new JRadioButton("4");
        buttonFive = new JRadioButton("5");
        buttonSix = new JRadioButton("6");
        diceGuesGroup.add(buttonOne);
        diceGuesGroup.add(buttonTwo);
        diceGuesGroup.add(buttonThree);
        diceGuesGroup.add(buttonFour);
        diceGuesGroup.add(buttonFive);
        diceGuesGroup.add(buttonSix);

        betButton = new JButton("place bet");
    }

    public void initializeGameScreen(){
        gameScreen.setSize(500,500);
        gameScreen.setLayout(new GridLayout(7,1,2,2));
        gameScreen.add(playscore);
        gameScreen.add(result);
        gameScreen.add(betArea);

        gameSelectionPanel = new JPanel();
        gameSelectionPanel.add(new JLabel("choose game type"));
        gameSelectionPanel.add(diceGameButton);
        gameSelectionPanel.add(coinGameButton);
        gameSelectionPanel.setVisible(true);

        //add radio
        coinPanel = new JPanel();
        coinPanel.add(new JLabel("Choose Heads or Tails:"));
        coinPanel.add(headsButton);
        coinPanel.add(tailsButton);
        coinPanel.setVisible(false);



        dicePanel = new JPanel();
        dicePanel.add(new JLabel("choose 1-6 dice roll"));
        dicePanel.add(buttonOne);
        dicePanel.add(buttonTwo);
        dicePanel.add(buttonThree);
        dicePanel.add(buttonFour);
        dicePanel.add(buttonFive);
        dicePanel.add(buttonSix);

        gameScreen.add(gameSelectionPanel);
        gameScreen.add(dicePanel);
        gameScreen.add(coinPanel);
        gameScreen.add(betButton);
        gameScreen.setVisible(true);
    }

    public void betButtonActionListener(ActionListener actionListener){
        betButton.addActionListener(actionListener);
    }

    public void diceGameButtonActionListener(ActionListener actionListener){diceGameButton.addActionListener(actionListener);}
    public void coinGameButtonActionListener(ActionListener actionListener){coinGameButton.addActionListener(actionListener);}

    public String getUserCoinBet(){
        String guess = "";
        if(headsButton.isSelected()){
            guess = "heads";
        }else if(tailsButton.isSelected()){
            guess = "tails";
        }else{
            return "Please Enter A Valid Guess";
        }
        String betAmount = betArea.getText();
        return guess + "," + betAmount;
    }

    public String getUserDiceBet(){
        String guess = "";
        if(buttonOne.isSelected()){
            guess = "1";
        }else if(buttonTwo.isSelected()){
            guess = "2";
        }else if(buttonThree.isSelected()){
            guess = "3";
        }else if(buttonFour.isSelected()){
            guess = "4";
        }else if(buttonFive.isSelected()){
            guess = "5";
        }else if(buttonSix.isSelected()){
            guess = "6";
        }else{
            return "Please Enter A Valid Guess";
        }
        String betAmount = betArea.getText();
        return guess + "," + betAmount;
    }

    public void displayCoinGame(){
        coinPanel.setVisible(true);
        dicePanel.setVisible(false);
    }

    public void displayDiceGame(){
        dicePanel.setVisible(true);
        coinPanel.setVisible(false);
    }
    public void getGameResult(String resultText){
        result.setText(resultText);
    }
    public void setScore(String score){
        playscore.setText("Score: " + score);
    }

}
