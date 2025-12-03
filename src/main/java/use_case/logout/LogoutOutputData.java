package use_case.logout;

import entity.User;

/**
 * Output data for the logout use case.
 */
public class LogoutOutputData {
    private final User user;

    public LogoutOutputData(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
