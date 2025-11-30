package session;

import entity.User;

public interface Session {

    User getMainUser();
    void setMainUser(User mainUser);

    void setLoggedIn(boolean loggedIn);
}
