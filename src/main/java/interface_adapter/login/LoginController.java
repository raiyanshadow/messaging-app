package interface_adapter.login;

import java.sql.SQLException;

import use_case.login.LoginInputBoundary;
import use_case.login.LoginInputData;

/**
 * Controller for the login use case.
 */
public class LoginController {
    private final LoginInputBoundary loginInteractor;

    public LoginController(LoginInputBoundary loginInteractor) {
        this.loginInteractor = loginInteractor;
    }

    /**
     * Calls the interactor for the login use case.
     * @param username the username to login with.
     * @param password the password to login with.
     * @throws SQLException whenever we can't access the database.
     */
    public void logIn(String username, String password) throws SQLException {
        final LoginInputData inputData = new LoginInputData(username, password);
        loginInteractor.logIn(inputData);
    }

    /**
     * Calls the interactor to switch to the signup view.
     */
    public void switchToSignupView() {
        loginInteractor.switchToSignupView();
    }
}
