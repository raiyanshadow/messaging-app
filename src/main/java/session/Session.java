package session;

import entity.User;

/**
 * Interface of the session manager.
 */
public interface Session {

    /**
     * Getter for the main user field.
     * @return returns the main User entity.
     */
    User getMainUser();

    /**
     * Setter for the main user field.
     * @param mainUser user to set to.
     */
    void setMainUser(User mainUser);

    /**
     * Setter for the loggedIn field.
     * @param loggedIn boolean value to set to.
     */
    void setLoggedIn(boolean loggedIn);
}
