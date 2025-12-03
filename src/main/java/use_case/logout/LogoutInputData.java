package use_case.logout;

import entity.User;

/**
 * Input data for the logout use case.
 */
public class LogoutInputData {
    private final User user;

    public LogoutInputData(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
