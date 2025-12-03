package use_case.profile_edit;

/**
 * Input data for profile edit use case.
 */
public class ProfileEditInputData {
    private final int userId;
    private final String username;
    private final String password;
    private final String preferredLanguage;

    public ProfileEditInputData(int userId, String username, String password, String preferredLanguage) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.preferredLanguage = preferredLanguage;
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getPreferredLanguage() {
        return preferredLanguage;
    }
}
