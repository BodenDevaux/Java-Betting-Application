import java.util.List;
import java.util.Random;

public class Game {
    public int score;
    private gameModel gamemodel;
    private static final Random random = new Random();

    public Game(Player player){
        //System.out.println("Hello");
        this.gamemodel = new gameModel(player);
        int current = gamemodel.getScore();
        if(current == -1){
            score = 100;
            //gamemodel.createScore(score);
            gamemodel.updateScore(score);
        }else{
            score = current;
        }
    }



    public synchronized String coinBet(String bet) {
        String[] vals = bet.split(",");

        double randNUM = random.nextDouble();

        String flipResult;
        if (randNUM >= 0.50) {
            flipResult = "heads";
        } else {
            flipResult ="tails";
        }
        String scoreText;
        String playerResult;
        String guess =vals[1];
        if (guess.equals(flipResult)) {
            //player win
            score += Integer.parseInt(vals[2]);
            //gamemodel.createScore();
            gamemodel.updateScore(score);
            scoreText = String.valueOf(score);
            playerResult = "win";
        } else {
            //player loss

            score -= Integer.parseInt(vals[2]);
            //gamemodel.createScore(score);
            gamemodel.updateScore(score);
            scoreText = String.valueOf(score);
            playerResult = "loss";
        }

        return playerResult + "," + flipResult + "," + scoreText;
    }

    public synchronized String diceBet(String bet){

        System.out.println(Thread.currentThread().getName() + " - Entering coinBet");
        try {
            String[] vals = bet.split(",");
            String playerResult;
            String dieResult;
            String scoreText;

            int randNUM = random.nextInt(6)+1;
            if(randNUM == 1){dieResult ="1";  }
            else if(randNUM == 2){dieResult ="2";   }
            else if (randNUM ==3){dieResult ="3"; }
            else if(randNUM == 4){dieResult ="4"; }
            else if(randNUM == 5){dieResult ="5"; }
            else{dieResult ="6"; }
            String playerGuess = vals[1];
            int betAmount = Integer.parseInt(vals[2]);

            if(playerGuess.equals(dieResult)){
                //win
                score += betAmount * 6;
                gamemodel.createScore(score);
                gamemodel.updateScore(score);
                scoreText = String.valueOf(score);
                playerResult = "win";
            }else{
                //loss
                score -= betAmount;
                gamemodel.createScore(score);
                gamemodel.updateScore(score);
                scoreText = String.valueOf(score);
                playerResult = "loss";
            }

            return playerResult + "," + dieResult + "," + scoreText;
        } finally {
            System.out.println(Thread.currentThread().getName() + " - Exiting coinBet");
        }

    }
    public int getScore(){
        return score;
    }
    public List<String> getLeaderBoard(){
        return gamemodel.getLeaderBoard();
    }
}
