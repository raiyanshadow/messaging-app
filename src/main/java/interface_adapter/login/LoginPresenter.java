package interface_adapter.login;

import interface_adapter.ViewManagerModel;
import interface_adapter.home_page.HomePageState;
import interface_adapter.home_page.HomePageViewModel;
import interface_adapter.signup.SignupState;
import interface_adapter.signup.SignupViewModel;
import use_case.login.LoginOutputBoundary;
import use_case.login.LoginOutputData;

public class LoginPresenter implements LoginOutputBoundary {
    private final LoginViewModel loginViewModel;
    private final ViewManagerModel viewManagerModel;
    private final HomePageViewModel homePageViewModel;
    private final SignupViewModel signupViewModel;

    public LoginPresenter(ViewManagerModel viewManagerModel,
                          LoginViewModel loginViewModel,
                          HomePageViewModel homePageViewModel, SignupViewModel signupViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.loginViewModel = loginViewModel;
        this.homePageViewModel = homePageViewModel;
        this.signupViewModel = signupViewModel;
    }

    @Override
    public void prepareSuccessView(LoginOutputData outputData) {
        final HomePageState homePageState = homePageViewModel.getState();
        final LoginState loginState = loginViewModel.getState();
        loginState.setIsSuccessful(true);
        homePageState.setUser(outputData.getUser());
        homePageState.setisLoggedIn(true);
        loginViewModel.firePropertyChange();
        homePageViewModel.firePropertyChange();
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
        viewManagerModel.setState(homePageViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }

    @Override
    public void switchToSignUpView() {
        viewManagerModel.setState(signupViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }
}
