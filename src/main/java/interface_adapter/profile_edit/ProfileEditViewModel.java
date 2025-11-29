package interface_adapter.profile_edit;

import interface_adapter.ViewModel;

public class ProfileEditViewModel extends ViewModel<ProfileEditState> {
    public ProfileEditViewModel() {
        super("profile edit view");
        setState(new ProfileEditState());
    }
}
