package interface_adapter.logout;

/**
 * State for the logout use case.
 */
public class LogoutState {
    private boolean isLoggedOutSuccessfully;

    public boolean isLoggedOutSuccessfully() {
        return isLoggedOutSuccessfully;
    }

    public void setLoggedOutSuccessfully(boolean status) {
        this.isLoggedOutSuccessfully = status;
    }
}
