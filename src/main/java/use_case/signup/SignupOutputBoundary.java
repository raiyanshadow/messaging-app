package use_case.signup;

public interface SignupOutputBoundary {

    /**
     * Called when signup is successful.
     * @param signupOutputData the data to present to the user
     */
    void prepareSuccessView(SignupOutputData signupOutputData);

    /**
     * Called when signup fails due to validation or other errors.
     * @param errorMessage the error message to display
     */
    void prepareFailView(String errorMessage);

    /**
     * Switches the view to the login screen.
     */
    void switchToLoginView();
}
