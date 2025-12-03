package use_case.profile_edit;

import entity.User;

/**
 * Output data for the profile edit use case.
 */
public class ProfileEditOutputData {
    private final User user;

    public ProfileEditOutputData(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
