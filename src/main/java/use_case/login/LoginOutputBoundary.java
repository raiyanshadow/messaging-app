package use_case.login;

/**
 * Interface for the login use case's presenter.
 */
public interface LoginOutputBoundary {
    /**
     * Prepares the success view after a successful login attempt.
     * @param outputData The data to be presented in the success view.
     */
    void prepareSuccessView(LoginOutputData outputData);

    /**
     * Prepares the failure view after a failed login attempt.
     * @param message The error message to be displayed in the failure view.
     */
    void prepareFailureView(String message);

    /**
     * Switches to the home view.
     */
    void switchToHomePageView();

    /**
     * Switches to the signup view.
     */
    void switchToSignUpView();
}
