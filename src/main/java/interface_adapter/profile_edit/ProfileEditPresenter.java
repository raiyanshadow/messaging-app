package interface_adapter.profile_edit;

import interface_adapter.ViewManagerModel;
import interface_adapter.home_page.HomePageState;
import interface_adapter.home_page.HomePageViewModel;
import use_case.profile_edit.ProfileEditOutputBoundary;
import use_case.profile_edit.ProfileEditOutputData;

public class ProfileEditPresenter implements ProfileEditOutputBoundary {
    private final ViewManagerModel viewManagerModel;
    private final HomePageViewModel homePageViewModel;

    public ProfileEditPresenter(ViewManagerModel viewManagerModel, HomePageViewModel homePageViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.homePageViewModel = homePageViewModel;
    }

    @Override
    public void prepareSuccessView(ProfileEditOutputData profileEditOutputData) {
        final HomePageState homePageState = homePageViewModel.getState();
        homePageState.setUser(profileEditOutputData.getUser());
        homePageViewModel.firePropertyChange();
        switchToHomePageView();
    }

    @Override
    public void switchToHomePageView() {
        viewManagerModel.setState(homePageViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }
}
