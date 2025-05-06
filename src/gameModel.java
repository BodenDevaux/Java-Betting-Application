import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class gameModel {
    //private Connection connection;

    private final Player player;

    public gameModel(Player player) {
        this.player = player;
        createScore();
        /*
        try {
            this.connection = DBConnection.getConnection();
            System.out.println("Connection Successful");
            this.player = player;
            createScore();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

         */
    }

    public synchronized void createScore() {
        String cmd = "CREATE TABLE IF NOT EXISTS scores(" +
                "score_id INTEGER PRIMARY KEY," +
                "user_id INTEGER," +
                "username TEXT NOT NULL," +
                "score INTEGER," +
                "FOREIGN KEY(user_id) REFERENCES users(user_id)" +
                ");";
        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(cmd);
            //System.out.println("Score: " + score);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public synchronized void updateScore(int score) {
        try (Connection connection = DBConnection.getConnection()) {
            String cmd = "SELECT * FROM scores WHERE user_id = ?";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(cmd);
                preparedStatement.setInt(1, player.getLoggedinID());
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    String update = "UPDATE scores SET score = ? WHERE user_id = ?";
                    PreparedStatement preparedStatement1 = connection.prepareStatement(update);
                    preparedStatement1.setInt(1, score);
                    preparedStatement1.setInt(2, player.getLoggedinID());
                    preparedStatement1.executeUpdate();
                } else {
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized int getScore() {
        try (Connection connection = DBConnection.getConnection()) {
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return -1;
    }

    public synchronized List<String> getLeaderBoard() {
        try (Connection connection = DBConnection.getConnection()) {
            String cmd = "SELECT username, score FROM scores ORDER BY score DESC LIMIT 3";
            List<String> leaderboardList = new ArrayList<>();
            try (PreparedStatement preparedStatement = connection.prepareStatement(cmd)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                int rank = 1;
                while (resultSet.next()) {
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
