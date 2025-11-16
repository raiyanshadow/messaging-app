package interface_adapter.logout;

import entity.User;
import use_case.logout.LogoutInputBoundary;
import use_case.logout.LogoutInputData;
import view.LogoutView;

public class LogoutController{

    private final LogoutInputBoundary logoutInputBoundary;

    public LogoutController(LogoutInputBoundary logoutInputBoundary, LogoutView logoutView) {
        this.logoutInputBoundary = logoutInputBoundary;
    }

    public void logoutUser(User user) {
        LogoutInputData logoutInputData = new LogoutInputData(user);
        logoutInputBoundary.logOut(logoutInputData);
    }
}
