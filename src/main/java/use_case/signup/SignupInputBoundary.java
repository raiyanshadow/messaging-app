package use_case.signup;

public interface SignupInputBoundary {

    /**
     * Executes the signup use case.
     * @param signupInputData the input data from the UI
     */
    void execute(SignupInputData signupInputData);

    /**
     * Switches the view to the login screen.
     */
    void switchToLoginView();
}
