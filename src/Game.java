import java.util.Random;

public class Game {
    public int score = 100;
    private Model model = new Model();
    
    public String coinBet(String bet) {
        String[] vals = bet.split(",");

        Random random = new Random();
        double randNUM = random.nextDouble();

        int flip = 0;
        String flipResult;
        if (randNUM >= 0.50) {
            flip = 1;
            flipResult = "heads";
        } else {
            flip = 0;
            flipResult ="tails";
        }

        String scoreText;
        String playerResult;
        int guess = Integer.parseInt(vals[0]);
        if (guess == flip) {
            //player win
            score += Integer.parseInt(vals[1]);
            model.createScore(score);
            model.updateScore(score);
            scoreText = String.valueOf(score);
            playerResult = "win";
        } else {
            //player loss

            score -= Integer.parseInt(vals[1]);
            model.createScore(score);
            model.updateScore(score);
            scoreText = String.valueOf(score);
            playerResult = "loss";
        }


        return playerResult + "," + flipResult + "," + scoreText;
    }

    public String diceBet(String bet){

        String[] vals = bet.split(",");
        String playerResult;
        String dieResult;
        String scoreText;

        Random random = new Random();
        int randNUM = random.nextInt(7);

        if(randNUM == 1){dieResult ="1";  }
        else if(randNUM == 2){dieResult ="2";   }
        else if (randNUM ==3){dieResult ="3"; }
        else if(randNUM == 4){dieResult ="4"; }
        else if(randNUM == 5){dieResult ="5"; }
        else{dieResult ="6"; }
        int playerGuess = Integer.parseInt(vals[0]);

        if(playerGuess == randNUM){
            //win
            score += playerGuess;
            scoreText = String.valueOf(score);
            playerResult = "win";
        }else{
            score -= playerGuess;
            scoreText = String.valueOf(score);
            playerResult = "loss";
        }

        return playerResult + "," + dieResult + "," + scoreText;
    }
}
