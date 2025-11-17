package interface_adapter.logout;

import interface_adapter.ViewManagerModel;
import interface_adapter.home_page.HomePageState;
import interface_adapter.home_page.HomePageViewModel;
import interface_adapter.login.LoginViewModel;
import use_case.logout.LogoutOutputBoundary;
import use_case.logout.LogoutOutputData;

public class LogoutPresenter implements LogoutOutputBoundary {
    private final LogoutViewModel logoutViewModel;
    private final ViewManagerModel viewManagerModel;
    private final HomePageViewModel homePageViewModel;

    public LogoutPresenter(LogoutViewModel logoutViewModel, ViewManagerModel viewManagerModel, HomePageViewModel homePageViewModel) {
        this.logoutViewModel = logoutViewModel;
        this.viewManagerModel = viewManagerModel;
        this.homePageViewModel = homePageViewModel;
    }

    @Override
    public void prepareSuccessView(LogoutOutputData outputData) {
        LogoutState logoutState = logoutViewModel.getState();
        logoutState.setLoggedOutSuccessfully(true);
        HomePageState homePageState = homePageViewModel.getState();
        homePageState.setisLoggedIn(false);
        homePageViewModel.firePropertyChange();
        logoutViewModel.firePropertyChange();
        // After logout, switch to home page view
        viewManagerModel.setState(homePageViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }
}
