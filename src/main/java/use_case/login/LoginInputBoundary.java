package use_case.login;

import java.sql.SQLException;

/**
 * Interface for the login use case's interactor.
 */
public interface LoginInputBoundary {
    /**
     * Method to log in a user with the provided input data.
     * @param data The input data required for logging in.
     * @throws SQLException whenever we can't read the database.
     */
    void logIn(LoginInputData data) throws SQLException;

    /**
     * Displays the signup view on button click.
     */
    void switchToSignupView();
}
