import java.util.Random;

public class Game {
    public int score = 100;
    private Model model = new Model();
    private View view = new View();
    public void game(String bet) {
        System.out.println("You Win!");
        String[] vals = bet.split(",");
        System.out.println("vals[1]: " + vals[0] + "  vals[2]: " + vals[1]);

        Random random = new Random();
        double randNUM = random.nextDouble();
        System.out.println(randNUM);
        int flip = 0;

        if (randNUM >= 0.50) {
            flip = 1;
            //heads
        } else {
            flip = 0;
            //tails
        }

        int guess = Integer.parseInt(vals[0]);
        if (guess == flip) {
            //player win
            System.out.println("win");
            score += Integer.parseInt(vals[1]);
            model.createScore(score);
            model.updateScore(score);
            view.setScore(String.valueOf(score));
            view.setGameResult("You Win!");
            System.out.println("You Win!");
        } else {
            //player loss
            System.out.println("loss");
            score -= Integer.parseInt(vals[1]);
            model.createScore(score);
            model.updateScore(score);
            view.setScore(String.valueOf(score));
            view.setGameResult("You Lose!");
            System.out.println("You lose");
        }


        //return bet;
    }
}
