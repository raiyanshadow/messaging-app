package interface_adapter.profile_edit;

import use_case.profile_edit.ProfileEditOutputBoundary;
import use_case.profile_edit.ProfileEditOutputData;

/**
 * Presenter of the profile edit use case.
 */
public class ProfileEditPresenter implements ProfileEditOutputBoundary {
    private final ProfileEditViewModel profileEditViewModel;

    public ProfileEditPresenter(ProfileEditViewModel profileEditViewModel) {
        this.profileEditViewModel = profileEditViewModel;
    }

    @Override
    public void prepareSuccessView(ProfileEditOutputData profileEditOutputData) {
        final ProfileEditState profileEditState = profileEditViewModel.getState();
        profileEditState.setUsername(profileEditOutputData.getUser().getUsername());
        profileEditState.setPassword(profileEditOutputData.getUser().getPassword());
        profileEditState.setPreferredLanguage(profileEditOutputData.getUser().getPreferredLanguage());
        profileEditViewModel.firePropertyChange();
    }
}
