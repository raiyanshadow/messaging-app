package session;
import entity.User;
import java.sql.SQLException;

public class SessionManager implements Session {
    private User mainUser;
    private boolean isLoggedin;

    public SessionManager(User mainUser, boolean isLoggedin) throws SQLException {
        this.mainUser = mainUser;
        this.isLoggedin = isLoggedin;
    }

    public User getMainUser() {
        return mainUser;
    }
    public void setMainUser(User mainUser) {
        this.mainUser = mainUser;
    }

    public boolean isLoggedin() {
        return isLoggedin;
    }
    public void setLoggedin(boolean loggedin) {
        isLoggedin = loggedin;
    }
}
