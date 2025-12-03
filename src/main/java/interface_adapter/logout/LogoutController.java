package interface_adapter.logout;

import entity.User;
import use_case.logout.LogoutInputBoundary;
import use_case.logout.LogoutInputData;

/**
 * Controller for the logout use case.
 */
public class LogoutController {
    private final LogoutInputBoundary logoutInputBoundary;

    public LogoutController(LogoutInputBoundary logoutInputBoundary) {
        this.logoutInputBoundary = logoutInputBoundary;
    }

    /**
     * Calls the interactor for the logout use case.
     * @param user the user to logout.
     */
    public void logoutUser(User user) {
        final LogoutInputData logoutInputData = new LogoutInputData(user);
        logoutInputBoundary.logOut(logoutInputData);
    }
}
