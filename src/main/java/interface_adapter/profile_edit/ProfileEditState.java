package interface_adapter.profile_edit;

/**
 * State of the profile edit use case.
 */
public class ProfileEditState {
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

    public void setError(String error) {
        this.error = error;
    }

    public String getError() {
        return this.error;
    }

}
