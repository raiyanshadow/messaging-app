package interface_adapter.profile_edit;

import use_case.profile_edit.ProfileEditOutputBoundary;
import use_case.profile_edit.ProfileEditOutputData;

public class ProfileEditPresenter implements ProfileEditOutputBoundary {
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
        profileEditState.setError(null);
        profileEditViewModel.firePropertyChange();}

    public void prepareFailView(String message) {
        ProfileEditState profileEditState = profileEditViewModel.getState();
        profileEditState.setError(message);
        profileEditViewModel.firePropertyChange();
    }
}
