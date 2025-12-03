package interface_adapter.signup;

/**
 * Represents the state of the signup form.
 * Stores user input, validation errors, and preferred language selection.
 */
public class SignupState {

    /** The username entered by the user. */
    private String username = "";

    /** Error message related to the username input. */
    private String usernameError;

    /** The password entered by the user. */
    private String password = "";

    /** Error message related to the password input. */
    private String passwordError;

    /** The repeated password entered by the user. */
    private String repeatPassword = "";

    /** Error message related to the repeated password input. */
    private String repeatPasswordError;

    /** The preferred language selected by the user. */
    private String preferredLanguage;

    // =========================
    // Getters
    // =========================

    public String getUsername() {
        return username;
    }

    public String getUsernameError() {
        return usernameError;
    }

    public String getPassword() {
        return password;
    }

    public String getPasswordError() {
        return passwordError;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public String getRepeatPasswordError() {
        return repeatPasswordError;
    }

    public String getPreferredLanguage() {
        return preferredLanguage;
    }

    // =========================
    // Setters
    // =========================

    public void setUsername(final String username) {
        this.username = username;
    }

    public void setUsernameError(final String usernameError) {
        this.usernameError = usernameError;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public void setPasswordError(final String passwordError) {
        this.passwordError = passwordError;
    }

    public void setRepeatPassword(final String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    public void setRepeatPasswordError(final String repeatPasswordError) {
        this.repeatPasswordError = repeatPasswordError;
    }

    public void setPreferredLanguage(final String preferredLanguage) {
        this.preferredLanguage = preferredLanguage;
    }

    /**
     * Returns a string representation of the signup state.
     *
     * @return the signup state as a string
     */
    @Override
    public String toString() {
        return "SignupState{"
                + "username='" + username + '\''
                + ", password='" + password + '\''
                + ", repeatPassword='" + repeatPassword + '\''
                + ", preferredLanguage='" + preferredLanguage + '\''
                + '}';
    }
}
