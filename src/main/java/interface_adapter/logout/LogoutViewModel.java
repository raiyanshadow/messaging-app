package interface_adapter.logout;

import interface_adapter.ViewModel;

/**
 * View model for the logout use case.
 */
public class LogoutViewModel extends ViewModel<LogoutState> {
    public LogoutViewModel() {
        super("logout");
        setState(new LogoutState());
    }
}
