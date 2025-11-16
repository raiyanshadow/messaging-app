package use_case.logout;

import entity.User;

public class LogoutOutputData {
    private final User user;

    public LogoutOutputData(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
