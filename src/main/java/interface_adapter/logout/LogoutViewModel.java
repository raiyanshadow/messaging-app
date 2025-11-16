package interface_adapter.logout;

import interface_adapter.ViewModel;

public class LogoutViewModel extends ViewModel<LogoutState> {
    public LogoutViewModel() {
        super("logout");
        setState(new LogoutState());
    }
}
