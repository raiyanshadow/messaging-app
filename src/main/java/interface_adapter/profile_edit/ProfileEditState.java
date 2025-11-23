package interface_adapter.profile_edit;

import entity.User;

public class ProfileEditState {
    private User user;

    public ProfileEditState() {
        this.user = null;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
