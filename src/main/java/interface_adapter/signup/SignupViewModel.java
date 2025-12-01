package interface_adapter.signup;

import interface_adapter.ViewModel;

/**
 * ViewModel for the signup view.
 * Holds the state of the signup form and provides constants for UI labels.
 */
public class SignupViewModel extends ViewModel<SignupState> {

    /** Title label for the signup form. */
    public static final String TITLE_LABEL = "Sign Up";

    /** Label for the username input field. */
    public static final String USERNAME_LABEL = "Enter username";

    /** Label for the password input field. */
    public static final String PASSWORD_LABEL = "Enter password";

    /** Label for the repeat password input field. */
    public static final String REPEAT_PASSWORD_LABEL = "Enter password again";

    /** Label for the language selection dropdown. */
    public static final String LANGUAGE_LABEL = "Select language";

    /** Label for the signup button. */
    public static final String SIGNUP_BUTTON_LABEL = "Sign up";

    /** Label for the "Go to Login" button. */
    public static final String TO_LOGIN_BUTTON_LABEL = "Go to Login";

    /** Default languages for the dropdown. */
    public static final String[] LANGUAGES = {"English", "French", "Spanish"};

    /**
     * Constructs a new SignupViewModel with initial state.
     * Sets the default preferred language.
     */
    public SignupViewModel() {
        super("signup");
        final SignupState initialState = new SignupState();
        initialState.setPreferredLanguage(LANGUAGES[0]);
        setState(initialState);
    }
}
