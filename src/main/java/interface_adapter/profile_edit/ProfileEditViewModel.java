package interface_adapter.profile_edit;

import interface_adapter.ViewModel;

/**
 * View model of the profile edit use case.
 */
public class ProfileEditViewModel extends ViewModel<ProfileEditState> {
    public ProfileEditViewModel() {
        super("profile edit view");
        setState(new ProfileEditState());
    }
}
