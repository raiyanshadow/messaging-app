package use_case.signup;

/**
 * Data transfer object for signup output.
 * Contains information to be presented to the user after a successful signup.
 */
public class SignupOutputData {

    /** The username of the newly created user. */
    private final String username;

    /**
     * Constructs a new SignupOutputData object.
     *
     * @param username the username of the new user
     */
    public SignupOutputData(final String username) {
        this.username = username;
    }

    /**
     * Returns the username of the newly created user.
     *
     * @return the username
     */
    public String getUsername() {
        return this.username;
    }
}
