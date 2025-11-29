package interface_adapter.profile_edit;

import entity.User;

public class ProfileEditState {
    private Integer userId;
    private String username;
    private String password;
    private String preferredLanguage;
    private String error;

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPreferredLanguage(String preferredLanguage) {
        this.preferredLanguage = preferredLanguage;
    }

    public String getPreferredLanguage() {
        return this.preferredLanguage;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getError() {
        return this.error;
    }

}
