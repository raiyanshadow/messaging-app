package interface_adapter.login;

/**
 * State for the login use case.
 */
public class LoginState {
    private String username;
    private String password;
    private String errorMessage;
    private boolean isSuccessful;

    public LoginState() {
        this.password = "";
        this.username = "";
        this.errorMessage = null;
        this.isSuccessful = false;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setIsSuccessful(boolean isSuccessful) {
        this.isSuccessful = isSuccessful;
    }

    public boolean getIsSuccessful() {
        return isSuccessful;
    }
}
