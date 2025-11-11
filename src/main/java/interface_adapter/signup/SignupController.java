package interface_adapter.signup;

import use_case.signup.SignupInputBoundary;
import use_case.signup.SignupInputData;

public class SignupController {

    private final SignupInputBoundary signupInteractor;

    public SignupController(SignupInputBoundary signupInteractor) {
        this.signupInteractor = signupInteractor;
    }

    public void execute(String username, String password, String repeatPassword, String preferredLanguage) {
        SignupInputData inputData = new SignupInputData(username, password, repeatPassword, preferredLanguage);
        signupInteractor.execute(inputData);
    }

    public void switchToLoginView() {
        signupInteractor.switchToLoginView();
    }
}
