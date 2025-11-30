package interface_adapter.login;

import app.AppBuilder;
import interface_adapter.ViewManagerModel;
import interface_adapter.base_UI.baseUIViewModel;
import interface_adapter.signup.SignupViewModel;
import session.SessionManager;
import use_case.login.LoginOutputBoundary;
import use_case.login.LoginOutputData;

public class LoginPresenter implements LoginOutputBoundary {
    private final LoginViewModel loginViewModel;
    private final ViewManagerModel viewManagerModel;
    private final SignupViewModel signupViewModel;
    private final baseUIViewModel baseUIViewModel;
    private final SessionManager session;
    private AppBuilder appBuilder;

    public LoginPresenter(ViewManagerModel viewManagerModel,
                          LoginViewModel loginViewModel, SignupViewModel signupViewModel,
                          baseUIViewModel baseUIViewModel,
                          SessionManager session, AppBuilder appBuilder) {
        this.viewManagerModel = viewManagerModel;
        this.loginViewModel = loginViewModel;
        this.signupViewModel = signupViewModel;
        this.session = session;
        this.baseUIViewModel = baseUIViewModel;
        this.appBuilder = appBuilder;
    }

    @Override
    public void prepareSuccessView(LoginOutputData outputData) {
        session.setMainUser(outputData.getUser());
        session.setLoggedIn(true);
        appBuilder.buildPostLogin();
        LoginState loginState = new LoginState();
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
        viewManagerModel.setState(baseUIViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }

    @Override
    public void switchToSignUpView() {
        viewManagerModel.setState(signupViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }
}
