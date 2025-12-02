package interface_adapter.signup;

import use_case.signup.SignupInputBoundary;
import use_case.signup.SignupInputData;

import java.sql.SQLException;

/**
 * Controller for the signup use case.
 * Handles user input from the view and delegates actions to the interactor.
 */
public class SignupController {

    /** The interactor that implements the signup use case. */
    private final SignupInputBoundary signupInteractor;

    /**
     * Constructs a SignupController.
     *
     * @param signupInteractor the interactor handling signup logic
     */
    public SignupController(final SignupInputBoundary signupInteractor) {
        this.signupInteractor = signupInteractor;
    }

    /**
     * Executes the signup use case with the provided user input.
     *
     * @param username          the username input
     * @param password          the password input
     * @param repeatPassword    the repeated password input
     * @param preferredLanguage the preferred language
     * @throws SQLException if a database error occurs
     */
    public void execute(final String username, final String password,
                        final String repeatPassword, final String preferredLanguage)
            throws SQLException {
        final SignupInputData inputData = new SignupInputData(
                username, password, repeatPassword, preferredLanguage);
        signupInteractor.execute(inputData);
    }

    /**
     * Switches the view to the login screen.
     */
    public void switchToLoginView() {
        signupInteractor.switchToLoginView();
    }
}
