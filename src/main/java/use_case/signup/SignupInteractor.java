package use_case.signup;

import data_access.UserDataAccessObject;
import entity.User;

import java.sql.SQLException;

public class SignupInteractor implements SignupInputBoundary {

    private final UserDataAccessObject userDataAccessObject;
    private final SignupOutputBoundary userPresenter;

    public SignupInteractor(UserDataAccessObject userDataAccessObject,
                            SignupOutputBoundary userPresenter) {
        this.userDataAccessObject = userDataAccessObject;
        this.userPresenter = userPresenter;
    }

    @Override
    public void execute(SignupInputData inputData) throws SQLException {
        String username = inputData.getUsername();
        String password = inputData.getPassword();
        String repeatPassword = inputData.getRepeatPassword();
        String preferredLanguage = inputData.getPreferredLanguage();

        if (username.isEmpty()) {
            userPresenter.prepareFailView("Username cannot be empty");
        } else if (password.isEmpty()) {
            userPresenter.prepareFailView("Password cannot be empty");
        } else if (!password.equals(repeatPassword)) {
            userPresenter.prepareFailView("Passwords don't match.");
        } else if (userDataAccessObject.existsByName(username)) {
            userPresenter.prepareFailView("User already exists.");
        } else {
            User user = new User(56, username, password, preferredLanguage);
            userDataAccessObject.save(user);
            SignupOutputData outputData = new SignupOutputData(user.getUsername());
            userPresenter.prepareSuccessView(outputData);
        }
    }

    @Override
    public void switchToLoginView() {
        userPresenter.switchToLoginView();
    }
}
