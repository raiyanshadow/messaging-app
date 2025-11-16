package interface_adapter.login;

import entity.User;

public class LoginState {
    private String username;
    private String errorMessage;
    private boolean isSuccessful;

    public LoginState(){
        this.username = "";
        this.errorMessage = "";
        this.isSuccessful = false;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
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
