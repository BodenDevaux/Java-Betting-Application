public class Player {
    private String loggedinUser;
    private int loggedinID;


    public Player(String username, int userId) {
        loggedinUser = username;
        loggedinID = userId;
    }

    public String getLoggedinUser() {
        return loggedinUser;
    }

    public int getLoggedinID() {
        return loggedinID;
    }
}
