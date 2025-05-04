public class Player {
    private static String loggedinUser;
    private static int loggedinID;


    public Player(String username, int userId) {
        loggedinUser = username;
        loggedinID = userId;
    }

    public static String getLoggedinUser() {
        return loggedinUser;
    }

    public static int getLoggedinID() {
        return loggedinID;
    }
}
