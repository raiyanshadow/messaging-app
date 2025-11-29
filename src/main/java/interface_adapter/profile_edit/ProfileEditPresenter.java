package interface_adapter.profile_edit;

import interface_adapter.ViewManagerModel;
import interface_adapter.home_page.HomePageState;
import interface_adapter.home_page.HomePageViewModel;
import use_case.profile_edit.ProfileEditOutputBoundary;
import use_case.profile_edit.ProfileEditOutputData;

public class ProfileEditPresenter implements ProfileEditOutputBoundary {
//    private final ViewManagerModel viewManagerModel;
//    private final HomePageViewModel homePageViewModel;
    private final ProfileEditViewModel profileEditViewModel;

    public ProfileEditPresenter(ProfileEditViewModel profileEditViewModel) {
        this.profileEditViewModel = profileEditViewModel;
    }

    @Override
    public void prepareSuccessView(ProfileEditOutputData profileEditOutputData) {
        ProfileEditState profileEditState = profileEditViewModel.getState();
        profileEditState.setUsername(profileEditOutputData.getUser().getUsername());
        profileEditState.setPassword(profileEditOutputData.getUser().getPassword());
        profileEditState.setPreferredLanguage(profileEditOutputData.getUser().getPreferredLanguage());
        profileEditViewModel.firePropertyChange();
//        switchToHomePageView();
    }

//    @Override
//    public void switchToHomePageView() {
//        viewManagerModel.setState(homePageViewModel.getViewName());
//        viewManagerModel.firePropertyChange();
//    }
}
