package use_case.login;

import entity.User;

/**
 * Output data for the login use case.
 */
public class LoginOutputData {
    private final User user;

    public LoginOutputData(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
