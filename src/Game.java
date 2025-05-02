import java.util.Random;

public class Game {
    public int score = 100;
    private Model model = new Model();
    private View view = new View();
    
    public String coinBet(String bet) {
        System.out.println("You Win!");
        String[] vals = bet.split(",");

        Random random = new Random();
        double randNUM = random.nextDouble();
        System.out.println(randNUM);
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
}
