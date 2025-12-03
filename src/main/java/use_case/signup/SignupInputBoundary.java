package use_case.signup;

import java.sql.SQLException;

/**
 * Input boundary for the signup use case.
 * Defines the methods that an interactor must implement to handle signup logic.
 */
public interface SignupInputBoundary {

    /**
     * Executes the signup use case using the provided input data.
     * This method is intended to be overridden by implementations
     * that handle validation, database persistence, and any external API calls.
     *
     * @param signupInputData the input data from the UI
     * @throws SQLException if a database error occurs
     */
    void execute(SignupInputData signupInputData) throws SQLException;

    /**
     * Switches the view to the login screen.
     * Intended to be called when the user wants to navigate back to login.
     */
    void switchToLoginView();
}
