package session;

import entity.User;

public interface Session {

    User getMainUser();
    void setMainUser(User mainUser);

    boolean isLoggedin();
    void setLoggedin(boolean loggedin);
}
