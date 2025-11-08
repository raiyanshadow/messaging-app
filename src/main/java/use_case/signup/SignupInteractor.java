package use_case.signup;

import entity.User;
import entity.UserFactory;

public class SignupInteractor implements SignupInputBoundary {

    private final SignupUserDataAccessInterface userDataAccessObject;
    private final SignupOutputBoundary userPresenter;
    private final UserFactory userFactory;

    public SignupInteractor(SignupUserDataAccessInterface userDataAccessObject,
                            SignupOutputBoundary userPresenter,
                            UserFactory userFactory) {
        this.userDataAccessObject = userDataAccessObject;
        this.userPresenter = userPresenter;
        this.userFactory = userFactory;
    }

    @Override
    public void execute(SignupInputData inputData) {
        String username = inputData.getUsername();
        String password = inputData.getPassword();
        String repeatPassword = inputData.getRepeatPassword();
        String preferredLanguage = inputData.getPreferredLanguage();

        // Validation using else-if structure
        if (username.isEmpty()) {
            userPresenter.prepareFailView("Username cannot be empty");
        } else if (password.isEmpty()) {
            userPresenter.prepareFailView("Password cannot be empty");
        } else if (!password.equals(repeatPassword)) {
            userPresenter.prepareFailView("Passwords don't match.");
        } else if (userDataAccessObject.existsByName(username)) {
            userPresenter.prepareFailView("User already exists.");
        } else {
            // Create the User
            User user = userFactory.create(username, password, preferredLanguage);

            // Persist the User
            userDataAccessObject.save(user);

            // Prepare output
            SignupOutputData outputData = new SignupOutputData(user.getUsername());
            userPresenter.prepareSuccessView(outputData);
        }
    }

    @Override
    public void switchToLoginView() {
        userPresenter.switchToLoginView();
    }
}
