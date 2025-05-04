import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class gameModel {
    private Connection connection;

    private Player player;
    public gameModel(Player player){

        try {
            this.connection = DBConnection.getConnection();
            System.out.println("Connection Successful");
            this.player = player;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void createScore(int score){
        String cmd = "CREATE TABLE IF NOT EXISTS scores(" +
                "score_id INTEGER PRIMARY KEY," +
                "user_id INTEGER," +
                "username TEXT NOT NULL," +
                "score INTEGER," +
                "FOREIGN KEY(user_id) REFERENCES users(user_id)" +
                ");";
        try (Statement statement = connection.createStatement()) {
            statement.execute(cmd);
            System.out.println("Score: " + score);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public void updateScore(int score){
        String cmd = "SELECT * FROM scores WHERE user_id = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(cmd);
            preparedStatement.setInt(1, player.getLoggedinID());
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                String update = "UPDATE scores SET score = ? WHERE user_id = ?";
                PreparedStatement preparedStatement1 = connection.prepareStatement(update);
                preparedStatement1.setInt(1, score);
                preparedStatement1.setInt(2, player.getLoggedinID());
                preparedStatement1.executeUpdate();
            }else{
                String add = "INSERT INTO scores (user_id, username, score) VALUES (?, ?, ?)";
                PreparedStatement preparedStatement1 = connection.prepareStatement(add);
                preparedStatement1.setInt(1, player.getLoggedinID());
                preparedStatement1.setString(2, player.getLoggedinUser());
                preparedStatement1.setInt(3, score);
                preparedStatement1.executeUpdate();

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public int getScore() {
        String cmd = "SELECT score from scores WHERE user_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(cmd)) {
            preparedStatement.setInt(1, player.getLoggedinID());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("score");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return -1;
    }
    public List<String> getLeaderBoard(){
        String cmd = "SELECT username, score FROM scores ORDER BY score DESC LIMIT 3";
        List<String> leaderboardList = new ArrayList<>();
        try(PreparedStatement preparedStatement = connection.prepareStatement(cmd)){
            ResultSet resultSet = preparedStatement.executeQuery();
            int rank = 1;
            while(resultSet.next()){
                String username = resultSet.getString("username");
                int score = resultSet.getInt("score");
                leaderboardList.add(rank + ". " + username + "  -  " + score);
                rank++;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(leaderboardList);
        return leaderboardList;
    }
}
