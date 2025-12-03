package session;

import java.sql.SQLException;

import entity.User;

/**
 * Session manager class that encapsulates the current user that is logged into the app.
 */
public class SessionManager implements Session {
    private User mainUser;
    private boolean isLoggedIn;

    public SessionManager() {
        // For initial construction during opening the app.
    }

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
