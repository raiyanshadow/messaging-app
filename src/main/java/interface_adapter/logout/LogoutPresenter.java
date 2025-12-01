package interface_adapter.logout;

import app.AppBuilder;
import interface_adapter.ViewManagerModel;
import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;
import session.SessionManager;
import use_case.logout.LogoutOutputBoundary;
import use_case.logout.LogoutOutputData;

public class LogoutPresenter implements LogoutOutputBoundary {
    private final LogoutViewModel logoutViewModel;
    private final ViewManagerModel viewManagerModel;
    private final LoginViewModel loginViewModel;
    private final SessionManager session;
    private AppBuilder appBuilder;

    public LogoutPresenter(LogoutViewModel logoutViewModel, ViewManagerModel viewManagerModel,
                           LoginViewModel loginViewModel, SessionManager session, AppBuilder appBuilder) {
        this.logoutViewModel = logoutViewModel;
        this.viewManagerModel = viewManagerModel;
        this.loginViewModel = loginViewModel;
        this.session = session;
        this.appBuilder = appBuilder;
    }

    @Override
    public void prepareSuccessView(LogoutOutputData outputData) {
        LogoutState logoutState = logoutViewModel.getState();
        logoutState.setLoggedOutSuccessfully(true);
        LoginState loginState = loginViewModel.getState();
        session.setLoggedIn(false);
        appBuilder.destroyPostLogin();
        loginViewModel.firePropertyChange();
        logoutViewModel.firePropertyChange();
        // After logout, switch to home page view
        viewManagerModel.setState(loginViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }
}
