package interface_adapter.login;

import use_case.login.LoginInputBoundary;
import use_case.login.LoginInputData;

import java.sql.SQLException;

public class LoginController {
    private final LoginInputBoundary loginInteractor;

    public LoginController(LoginInputBoundary loginInteractor) {
        this.loginInteractor = loginInteractor;
    }

    public void logIn(String username, String password) throws SQLException {
        LoginInputData inputData = new LoginInputData(username, password);
        loginInteractor.logIn(inputData);
    }

    public void switchToLoginView() {
        loginInteractor.switchToSignupView();
    }
}
