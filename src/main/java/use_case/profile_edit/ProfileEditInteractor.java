package use_case.profile_edit;

import java.sql.SQLException;

import entity.User;
import session.SessionManager;

/**
 * Interactor for profile edit use case.
 */
public class ProfileEditInteractor implements ProfileEditInputBoundary {
    private final ProfileEditUserDataAccessInterface userDataAccessObject;
    private final ProfileEditOutputBoundary userPresenter;
    private final SessionManager sessionManager;

    public ProfileEditInteractor(ProfileEditUserDataAccessInterface userDataAccessObject,
                                 ProfileEditOutputBoundary userPresenter, SessionManager sessionManager) {
        this.userDataAccessObject = userDataAccessObject;
        this.userPresenter = userPresenter;
        this.sessionManager = sessionManager;
    }

    @Override
    public void execute(ProfileEditInputData inputData) throws SQLException {
        final int userId = inputData.getUserId();
        final String username = inputData.getUsername();
        final String password = inputData.getPassword();
        final String preferredLanguage = inputData.getPreferredLanguage();

        if (!username.isEmpty()) {
            final boolean updated = userDataAccessObject.updateUsername(userId, username);
            if (!updated) {
                userPresenter.prepareFailView("Username already exists");
                return;
            }
        }

        if (!username.isEmpty()) {
            userDataAccessObject.updateUsername(userId, username);
        }

        if (!password.isEmpty()) {
            userDataAccessObject.updatePassword(userId, password);
        }

        if (!preferredLanguage.isEmpty()) {
            userDataAccessObject.updatePreferredLanguage(userId, preferredLanguage);
        }

        final User user = userDataAccessObject.getUserFromId(userId);
        System.out.println(user.getUsername());
        sessionManager.setMainUser(user);
        final ProfileEditOutputData outputData = new ProfileEditOutputData(user);
        userPresenter.prepareSuccessView(outputData);
    }
}
