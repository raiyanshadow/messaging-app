package interface_adapter.login;

import app.AppBuilder;
import interface_adapter.base_UI.BaseUiViewModel;
import interface_adapter.signup.SignupViewModel;
import session.SessionManager;
import use_case.login.LoginOutputBoundary;
import use_case.login.LoginOutputData;

/**
 * Presenter for the login use case.
 */
public class LoginPresenter implements LoginOutputBoundary {
    private final LoginViewModel loginViewModel;
    private final interface_adapter.ViewManagerModel viewManagerModel;
    private final SignupViewModel signupViewModel;
    private final BaseUiViewModel baseUiViewModel;
    private final SessionManager session;
    private AppBuilder appBuilder;

    public LoginPresenter(interface_adapter.ViewManagerModel viewManagerModel,
                          LoginViewModel loginViewModel, SignupViewModel signupViewModel,
                          BaseUiViewModel baseUiViewModel,
                          SessionManager session, AppBuilder appBuilder) {
        this.viewManagerModel = viewManagerModel;
        this.loginViewModel = loginViewModel;
        this.signupViewModel = signupViewModel;
        this.session = session;
        this.baseUiViewModel = baseUiViewModel;
        this.appBuilder = appBuilder;
    }

    @Override
    public void prepareSuccessView(LoginOutputData outputData) {
        session.setMainUser(outputData.getUser());
        session.setLoggedIn(true);
        appBuilder.buildPostLogin();
        final LoginState loginState = new LoginState();
        loginState.setUsername(outputData.getUser().getUsername());
        loginState.setPassword(outputData.getUser().getPassword());
        loginState.setIsSuccessful(true);
        loginViewModel.setState(loginState);
        switchToHomePageView();
        loginViewModel.firePropertyChange();
    }

    @Override
    public void prepareFailureView(String message) {
        final LoginState loginState = loginViewModel.getState();
        loginState.setIsSuccessful(false);
        loginState.setErrorMessage(message);
        loginViewModel.firePropertyChange();
    }

    @Override
    public void switchToHomePageView() {
        viewManagerModel.setState(baseUiViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }

    @Override
    public void switchToSignUpView() {
        viewManagerModel.setState(signupViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }
}
