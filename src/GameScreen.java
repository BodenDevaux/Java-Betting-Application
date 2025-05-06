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
    private ButtonGroup diceGuessGroup;
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
    private JPanel resultPanel;
    private JLabel resultLabel;
    private JPanel betPanel;
    private JLabel betLabel;
    private JPanel scorePanel;
    private JLabel scoreLabel;

    private final JRadioButton diceGameButton;
    private final JRadioButton coinGameButton;
    private final JRadioButton leaderboardButton;

    public GameScreen(){
        gameScreen = new JFrame();
        betArea = new JTextField();
        betLabel = new JLabel("enter bet");
        betPanel = new JPanel();
        result = new JTextArea();
        resultPanel = new JPanel();
        resultLabel = new JLabel("results");
        playscore = new JTextArea();
        scoreLabel = new JLabel("score:");
        scorePanel = new JPanel();

        diceGameButton = new JRadioButton("Dice");
        coinGameButton = new JRadioButton("Coin Flip");
        leaderboardButton = new JRadioButton("Leaderboard");

        gameSelectionGroup = new ButtonGroup();
        gameSelectionGroup.add(diceGameButton);
        gameSelectionGroup.add(coinGameButton);
        gameSelectionGroup.add(leaderboardButton);

        headsButton = new JRadioButton("Heads");
        tailsButton = new JRadioButton("Tails");

        coinGuessGroup = new ButtonGroup();
        coinGuessGroup.add(headsButton);
        coinGuessGroup.add(tailsButton);

        diceGuessGroup = new ButtonGroup();
        buttonOne = new JRadioButton("1");
        buttonTwo = new JRadioButton("2");
        buttonThree = new JRadioButton("3");
        buttonFour = new JRadioButton("4");
        buttonFive = new JRadioButton("5");
        buttonSix = new JRadioButton("6");
        diceGuessGroup.add(buttonOne);
        diceGuessGroup.add(buttonTwo);
        diceGuessGroup.add(buttonThree);
        diceGuessGroup.add(buttonFour);
        diceGuessGroup.add(buttonFive);
        diceGuessGroup.add(buttonSix);

        betButton = new JButton("place bet");
    }

    public void initializeGameScreen(){
        gameScreen.setSize(500,500);
        gameScreen.setLayout(new GridLayout(7,1,10,10));
        scorePanel.add(scoreLabel);
        scorePanel.add(playscore);
        gameScreen.add(scorePanel);
        resultPanel.add(resultLabel);
        resultPanel.add(result);
        gameScreen.add(resultPanel);
        betPanel.add(betLabel);
        betPanel.add(betArea);
        gameScreen.add(betPanel);
        playscore.setEditable(false);
        result.setEditable(false);
        betArea.setPreferredSize(new Dimension(100,20));
        result.setPreferredSize(new Dimension(100,20));
        playscore.setPreferredSize(new Dimension(100,20));

        gameSelectionPanel = new JPanel();
        gameSelectionPanel.add(new JLabel("choose game type"));
        gameSelectionPanel.add(diceGameButton);
        gameSelectionPanel.add(coinGameButton);
        gameSelectionPanel.add(leaderboardButton);
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
        dicePanel.setVisible(false);

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
    public void leaderboardButtonActionListener(ActionListener actionListener) {
        leaderboardButton.addActionListener(actionListener);
    }


    public String getUserCoinBet(){
        String guess = "";
        if(headsButton.isSelected()){
            guess = "heads";
        }
        if(tailsButton.isSelected()){
            guess = "tails";
        }
        String betAmount = betArea.getText();
        System.out.println("gamescreen coin bet :" + guess + ", " + betAmount);
        return guess + "," + betAmount;
    }

    public String getUserDiceBet(){
        String guess = "";
        if(buttonOne.isSelected()){
            guess = "1";
            System.out.println("right");
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
        System.out.println("gamescreen dice bet :" + guess + ", " + betAmount);
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
    public void displayLeaderboard(String leaderboard) {
        coinPanel.setVisible(false);
        dicePanel.setVisible(false);
        String[] leaders = leaderboard.split(",");
        String board = "";
        for(String entry: leaders){
            board += entry.trim() + "\n";
        }
        result.setText(board);
    }

}
