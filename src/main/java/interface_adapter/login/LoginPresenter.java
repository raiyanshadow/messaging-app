package interface_adapter.login;

import app.AppBuilder;
import interface_adapter.ViewManagerModel;
import interface_adapter.base_UI.baseUIState;
import interface_adapter.home_page.HomePageState;
import interface_adapter.base_UI.baseUIViewModel;
import interface_adapter.signup.SignupViewModel;
import session.SessionManager;
import use_case.login.LoginOutputBoundary;
import use_case.login.LoginOutputData;

public class LoginPresenter implements LoginOutputBoundary {
    private final LoginViewModel loginViewModel;
    private final ViewManagerModel viewManagerModel;
    private final baseUIViewModel baseUIViewModel;
    private final SignupViewModel signupViewModel;
    private final SessionManager session;
    private AppBuilder appBuilder;

    public LoginPresenter(ViewManagerModel viewManagerModel,
                          LoginViewModel loginViewModel,
                          baseUIViewModel baseUIViewModel, SignupViewModel signupViewModel,
                          SessionManager session, AppBuilder appBuilder) {
        this.viewManagerModel = viewManagerModel;
        this.loginViewModel = loginViewModel;
        this.baseUIViewModel = baseUIViewModel;
        this.signupViewModel = signupViewModel;
        this.session = session;
        this.appBuilder = appBuilder;
    }

    @Override
    public void prepareSuccessView(LoginOutputData outputData) {
        final baseUIState baseUIState = baseUIViewModel.getState();
        final LoginState loginState = loginViewModel.getState();
        loginState.setIsSuccessful(true);
        session.setMainUser(outputData.getUser());
        session.setLoggedin(true);
        appBuilder.buildPostLogin();
        loginViewModel.firePropertyChange();
        baseUIViewModel.firePropertyChange();
        switchToHomePageView();
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
