package session;

import entity.User;

public interface Session {
    public User getMainUser();
    public void setMainUser(User mainUser);
    public boolean isLoggedin();
    public void setLoggedin(boolean loggedin);
}
