package interface_adapter.profile_edit;

import java.sql.SQLException;

import use_case.profile_edit.ProfileEditInputBoundary;
import use_case.profile_edit.ProfileEditInputData;

/**
 * Controller for the profile edit use case.
 */
public class ProfileEditController {
    private final ProfileEditInputBoundary profileEditInteractor;

    public ProfileEditController(ProfileEditInputBoundary profileEditInteractor) {
        this.profileEditInteractor = profileEditInteractor;
    }

    /**
     * Calls the interactor for the profile edit use case.
     * @param userId id of the user.
     * @param username name to edit to.
     * @param password password to edit to.
     * @param preferredLanguage preferred language to edit to.
     * @throws SQLException whenever we can't access or modify the database.
     */
    public void editProfile(int userId, String username, String password,
                            String preferredLanguage) throws SQLException {
        final ProfileEditInputData inputData = new ProfileEditInputData(userId, username, password, preferredLanguage);
        profileEditInteractor.execute(inputData);
    }
}
