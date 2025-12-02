package session;
import entity.User;
import java.sql.SQLException;

public class SessionManager implements Session {
    private User mainUser;
    private boolean isLoggedIn;
    public SessionManager() {}

    public SessionManager(User mainUser, boolean isLoggedIn) throws SQLException {
        this.mainUser = mainUser;
        this.isLoggedIn = isLoggedIn;
    }

    public User getMainUser() {
        return mainUser;
    }
    public void setMainUser(User mainUser) {
        this.mainUser = mainUser;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }
}
