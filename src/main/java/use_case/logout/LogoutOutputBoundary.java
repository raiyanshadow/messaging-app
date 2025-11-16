package use_case.logout;

public interface LogoutOutputBoundary {
    /**
     * Prepares the success view after a user has successfully logged out.
     *
     * @param outputData The data to be presented in the success view.
     */
    void prepareSuccessView(LogoutOutputData outputData);
}
