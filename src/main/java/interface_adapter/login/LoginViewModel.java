package interface_adapter.login;

import interface_adapter.ViewModel;

public class LoginViewModel extends ViewModel<LoginState> {
    public static final String LOGIN_BUTTON_LABEL = "Log in";
    public static final String TO_SIGNUP_BUTTON_LABEL = "Go to Sign up";
    public static final String TITLE_LABEL = "Login Page";
    public static final String USERNAME_LABEL = "Enter username";
    public static final String PASSWORD_LABEL = "Enter password";

    public LoginViewModel() {
        super("login");
        setState(new LoginState());
    }
}
