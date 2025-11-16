package use_case.login;

import entity.User;
import use_case.profile_edit.ProfileEditOutputBoundary;

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

        boolean isValid = userDataAccess.validateCredentials(username, password);

        if (isValid) {
            User user = userDataAccess.getUserByUsername(username);
            LoginOutputData outputData = new LoginOutputData(user);
            userPresenter.prepareSuccessView(outputData);
        } else {
            userPresenter.prepareFailureView("Invalid username or password.");
        }
    }
}
