package use_case.login;

import entity.User;
import use_case.profile_edit.ProfileEditOutputBoundary;

import java.sql.SQLException;

public class LoginInteractor implements LoginInputBoundary {
    private final LoginUserDataAccessInterface userDataAccess;
    private final LoginOutputBoundary userPresenter;

    public LoginInteractor(LoginUserDataAccessInterface userDataAccess, LoginOutputBoundary userPresenter) {
        this.userDataAccess = userDataAccess;
        this.userPresenter = userPresenter;
    }

    @Override
    public void logIn(LoginInputData data) {
        String username = data.getUsername();
        String password = data.getPassword();

        boolean isValid = false;
        try {
            isValid = userDataAccess.validateCredentials(username, password);
        } catch (SQLException e) {
            userPresenter.prepareFailureView("DB read fail");
        }

        if (isValid) {
            User user = null;
            try {
                user = userDataAccess.getUserByUsername(username);
            } catch (SQLException e) {
                e.printStackTrace();
                userPresenter.prepareFailureView("DB read fail");
            }
            LoginOutputData outputData = new LoginOutputData(user);
            userPresenter.prepareSuccessView(outputData);
        } else {
            userPresenter.prepareFailureView("Invalid username or password.");
        }
    }

    @Override
    public void switchToSignupView() {
        userPresenter.switchToSignUpView();
    }
}
