package use_case.signup;

/**
 * Data transfer object for signup input data.
 * Contains the necessary information for user registration.
 */
public class SignupInputData {

    /** The username provided by the user. */
    private final String username;

    /** The password provided by the user. */
    private final String password;

    /** The repeated password for confirmation. */
    private final String repeatPassword;

    /** The user's preferred language. */
    private final String preferredLanguage;

    /**
     * Constructs a new SignupInputData object.
     *
     * @param username the username
     * @param password the password
     * @param repeatPassword the repeated password
     * @param preferredLanguage the preferred language
     */
    public SignupInputData(final String username, final String password,
                           final String repeatPassword, final String preferredLanguage) {
        this.username = username;
        this.password = password;
        this.repeatPassword = repeatPassword;
        this.preferredLanguage = preferredLanguage;
    }

    /**
     * Returns the username.
     *
     * @return the username
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Returns the password.
     *
     * @return the password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Returns the repeated password.
     *
     * @return the repeated password
     */
    public String getRepeatPassword() {
        return this.repeatPassword;
    }

    /**
     * Returns the preferred language.
     *
     * @return the preferred language
     */
    public String getPreferredLanguage() {
        return this.preferredLanguage;
    }
}
