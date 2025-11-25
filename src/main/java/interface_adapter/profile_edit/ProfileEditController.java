package interface_adapter.profile_edit;

import use_case.profile_edit.ProfileEditInputBoundary;
import use_case.profile_edit.ProfileEditInputData;

public class ProfileEditController {
    private final ProfileEditInputBoundary profileEditInteractor;

    public ProfileEditController(ProfileEditInputBoundary profileEditInteractor) {
        this.profileEditInteractor = profileEditInteractor;
    }

    public void editProfile(int userId, String username, String password, String preferredLanguage) {
        ProfileEditInputData inputData = new ProfileEditInputData(userId, username, password, preferredLanguage);
        profileEditInteractor.execute(inputData);
    }
}
