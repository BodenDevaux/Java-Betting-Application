import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class ThreadMultiClient implements Runnable {
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;
    private authenticateModel authenticatemodel = new authenticateModel();

    public ThreadMultiClient (Socket socket){
        this.clientSocket = socket;
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(),true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        String userInfo = null;
        try {
            userInfo = in.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String parts[] = userInfo.split(",");
        boolean ans = false;
        if (parts[0].equals("create")) {
            String credentials = parts[1] + "," + parts[2];
            authenticatemodel.addUser(credentials);
            ans = authenticatemodel.Login(credentials);
            if (ans) {
                out.println("Created");
            } else {
                out.println("shouldent happen");
                return;
            }
        }else {
            while (true) {
                ans = authenticatemodel.Login(userInfo);
                if (ans) {
                    out.println("success");
                    break;
                } else {
                    out.println("fail");
                    try{
                        userInfo = in.readLine();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        Player player = authenticatemodel.getPlayer();
        Game game = new Game(player);
        while(true){

            String bet;
            try {
                bet = in.readLine();
                String[] info = bet.split(",");
                String result;
                if (info[0].equals("dice")) {
                    result = game.diceBet(bet);
                } else if (info[0].equals("coin")) {
                    result = game.coinBet(bet);
                }else if(info[0].equals("leaderboard")){
                    List<String> leaderboardList = game.getLeaderBoard();
                    result = String.join(",", leaderboardList);
                } else {
                    result = "Invalid bet type.";
                }
                out.println(result);

            } catch (IOException e) {
                System.out.println("Connection lost with client: " + clientSocket.getPort());
                throw new RuntimeException(e);
            }
        }
    }

}
