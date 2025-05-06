import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class gameModel {
    private final Player player;
    private final Connection connection;
    private static final Object dbLock = new Object();
    //lock  https://www.geeksforgeeks.org/object-level-lock-in-java/

    public gameModel(Player player) {
        this.player = player;
        try {
            this.connection = DBConnection.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize database connection", e);
        }

         
    }

    public void createScore(int score) {
        synchronized (dbLock) {
            String cmd = "CREATE TABLE IF NOT EXISTS scores(" +
                    "score_id INTEGER PRIMARY KEY," +
                    "user_id INTEGER," +
                    "username TEXT NOT NULL," +
                    "score INTEGER," +
                    "FOREIGN KEY(user_id) REFERENCES users(user_id)" +
                    ");";
            try (Statement statement = connection.createStatement()) {
                statement.execute(cmd);
            } catch (SQLException e) {
                throw new RuntimeException("Failed to create scores table", e);
            }
        }
    }

    public void updateScore(int score) {
        synchronized (dbLock) {
            String checkCmd = "SELECT * FROM scores WHERE user_id = ?";
            String updateCmd = "UPDATE scores SET score = ? WHERE user_id = ?";
            String insertCmd = "INSERT INTO scores (user_id, username, score) VALUES (?, ?, ?)";

            try (PreparedStatement checkStmt = connection.prepareStatement(checkCmd)) {
                checkStmt.setInt(1, player.getLoggedinID());
                ResultSet resultSet = checkStmt.executeQuery();

                if (resultSet.next()) {
                    try (PreparedStatement updateStmt = connection.prepareStatement(updateCmd)) {
                        updateStmt.setInt(1, score);
                        updateStmt.setInt(2, player.getLoggedinID());
                        updateStmt.executeUpdate();
                    }
                } else {
                    try (PreparedStatement insertStmt = connection.prepareStatement(insertCmd)) {
                        insertStmt.setInt(1, player.getLoggedinID());
                        insertStmt.setString(2, player.getLoggedinUser());
                        insertStmt.setInt(3, score);
                        insertStmt.executeUpdate();
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException("Failed to update score for user " + player.getLoggedinID(), e);
            }
        }
    }

    public int getScore() {
        synchronized (dbLock) {
            String cmd = "SELECT score from scores WHERE user_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(cmd)) {
                preparedStatement.setInt(1, player.getLoggedinID());
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return resultSet.getInt("score");
                }
            } catch (SQLException e) {
                throw new RuntimeException("Failed to get score for user " + player.getLoggedinID(), e);
            }
            return -1;
        }
    }

    public List<String> getLeaderBoard() {
        synchronized (dbLock) {
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
                throw new RuntimeException("Failed to retrieve leaderboard", e);
            }
            return leaderboardList;
        }
    }

}