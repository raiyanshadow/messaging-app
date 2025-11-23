package use_case.profile_edit;

import entity.User;

public class ProfileEditOutputData {
    private final User user;

    public ProfileEditOutputData(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
