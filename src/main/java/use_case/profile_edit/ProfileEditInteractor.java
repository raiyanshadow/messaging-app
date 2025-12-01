package use_case.profile_edit;

import entity.User;
import session.SessionManager;

import java.sql.SQLException;

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
        int userId = inputData.getUserId();
        String username = inputData.getUsername();
        String password = inputData.getPassword();
        String preferredLanguage = inputData.getPreferredLanguage();

        if (!username.isEmpty()) {
            boolean noDuplicate = userDataAccessObject.updateUsername(userId, username);
            if (!noDuplicate) {
                userPresenter.prepareFailView("Username already exists");
                return;
            }
        }

        if (!password.isEmpty()) {
            userDataAccessObject.updatePassword(userId, password);
        }

        if (!preferredLanguage.isEmpty()) {
            userDataAccessObject.updatePreferredLanguage(userId, preferredLanguage);
        }

        User user = userDataAccessObject.getUserFromID(userId);
        System.out.println(user.getUsername());
        sessionManager.setMainUser(user);
        ProfileEditOutputData outputData = new ProfileEditOutputData(user);
        userPresenter.prepareSuccessView(outputData);
    }
}
