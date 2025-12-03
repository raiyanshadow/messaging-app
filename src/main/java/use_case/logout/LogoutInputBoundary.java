package use_case.logout;

/**
 * Interface for logout use case's interactor.
 */
public interface LogoutInputBoundary {
    /**
     * Logs out of the current application session.
     * @param inputData input data needed to log out.
     */
    void logOut(LogoutInputData inputData);
}
