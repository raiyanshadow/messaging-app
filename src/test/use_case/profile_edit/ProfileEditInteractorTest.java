package use_case.profile_edit;

import entity.User;
import org.junit.jupiter.api.Test;
import session.SessionManager;
import static org.junit.jupiter.api.Assertions.*;

class ProfileEditInteractorTest {

    @Test
    void successUpdateAllFieldsTest() throws Exception {
        ProfileEditInputData inputData = new ProfileEditInputData(1, "NewUsername", "NewPassword", "French");

        ProfileEditUserDataAccessInterface userDataAccess = new ProfileEditUserDataAccessInterface() {
            private User user = new User(1, "OldUsername", "OldPassword", "English");

            @Override
            public void updateUsername(int userId, String username) {
                user.setUsername(username);
            }

            @Override
            public void updatePassword(int userId, String password) {
                user.setPassword(password);
            }

            @Override
            public void updatePreferredLanguage(int userId, String preferredLanguage) {
                user.setPreferredLanguage(preferredLanguage);
            }

            @Override
            public User getUserFromID(int userId) {
                return user;
            }
        };

        ProfileEditOutputBoundary successPresenter = new ProfileEditOutputBoundary() {
            @Override
            public void prepareSuccessView(ProfileEditOutputData outputData) {
                assertEquals("NewUsername", outputData.getUser().getUsername());
                assertEquals("NewPassword", outputData.getUser().getPassword());
                assertEquals("French", outputData.getUser().getPreferredLanguage());
            }
        };

        SessionManager sessionManager = new SessionManager();
        ProfileEditInputBoundary interactor = new ProfileEditInteractor(userDataAccess, successPresenter, sessionManager);
        interactor.execute(inputData);
    }

    @Test
    void successUpdateUsernameOnlyTest() throws Exception {
        ProfileEditInputData inputData = new ProfileEditInputData(1, "NewUsername", "", "");

        ProfileEditUserDataAccessInterface userDataAccess = new ProfileEditUserDataAccessInterface() {
            private User user = new User(1, "OldUsername", "password", "English");

            @Override
            public void updateUsername(int userId, String username) {
                user.setUsername(username);
            }

            @Override
            public void updatePassword(int userId, String password) {
            }

            @Override
            public void updatePreferredLanguage(int userId, String preferredLanguage) {
            }

            @Override
            public User getUserFromID(int userId) {
                return user;
            }
        };

        ProfileEditOutputBoundary successPresenter = new ProfileEditOutputBoundary() {
            @Override
            public void prepareSuccessView(ProfileEditOutputData outputData) {
                assertEquals("NewUsername", outputData.getUser().getUsername());
                assertEquals("password", outputData.getUser().getPassword());
                assertEquals("English", outputData.getUser().getPreferredLanguage());
            }
        };

        SessionManager sessionManager = new SessionManager();
        ProfileEditInputBoundary interactor = new ProfileEditInteractor(userDataAccess, successPresenter, sessionManager);
        interactor.execute(inputData);
    }

    @Test
    void successUpdatePasswordOnlyTest() throws Exception {
        ProfileEditInputData inputData = new ProfileEditInputData(1, "", "NewPassword", "");

        ProfileEditUserDataAccessInterface userDataAccess = new ProfileEditUserDataAccessInterface() {
            private User user = new User(1, "username", "OldPassword", "English");

            @Override
            public void updateUsername(int userId, String username) {
            }

            @Override
            public void updatePassword(int userId, String password) {
                user.setPassword(password);
            }

            @Override
            public void updatePreferredLanguage(int userId, String preferredLanguage) {
            }

            @Override
            public User getUserFromID(int userId) {
                return user;
            }
        };

        ProfileEditOutputBoundary successPresenter = new ProfileEditOutputBoundary() {
            @Override
            public void prepareSuccessView(ProfileEditOutputData outputData) {
                assertEquals("username", outputData.getUser().getUsername());
                assertEquals("NewPassword", outputData.getUser().getPassword());
                assertEquals("English", outputData.getUser().getPreferredLanguage());
            }
        };

        SessionManager sessionManager = new SessionManager();
        ProfileEditInputBoundary interactor = new ProfileEditInteractor(userDataAccess, successPresenter, sessionManager);
        interactor.execute(inputData);
    }

    @Test
    void successUpdateLanguageOnlyTest() throws Exception {
        ProfileEditInputData inputData = new ProfileEditInputData(1, "", "", "Spanish");

        ProfileEditUserDataAccessInterface userDataAccess = new ProfileEditUserDataAccessInterface() {
            private User user = new User(1, "username", "password", "English");

            @Override
            public void updateUsername(int userId, String username) {
            }

            @Override
            public void updatePassword(int userId, String password) {
            }

            @Override
            public void updatePreferredLanguage(int userId, String preferredLanguage) {
                user.setPreferredLanguage(preferredLanguage);
            }

            @Override
            public User getUserFromID(int userId) {
                return user;
            }
        };

        ProfileEditOutputBoundary successPresenter = new ProfileEditOutputBoundary() {
            @Override
            public void prepareSuccessView(ProfileEditOutputData outputData) {
                assertEquals("username", outputData.getUser().getUsername());
                assertEquals("password", outputData.getUser().getPassword());
                assertEquals("Spanish", outputData.getUser().getPreferredLanguage());
            }
        };

        SessionManager sessionManager = new SessionManager();
        ProfileEditInputBoundary interactor = new ProfileEditInteractor(userDataAccess, successPresenter, sessionManager);
        interactor.execute(inputData);
    }

    @Test
    void successNoFieldsUpdatedTest() throws Exception {
        ProfileEditInputData inputData = new ProfileEditInputData(1, "", "", "");

        ProfileEditUserDataAccessInterface userDataAccess = new ProfileEditUserDataAccessInterface() {
            private User user = new User(1, "username", "password", "English");

            @Override
            public void updateUsername(int userId, String username) {
            }

            @Override
            public void updatePassword(int userId, String password) {
            }

            @Override
            public void updatePreferredLanguage(int userId, String preferredLanguage) {
            }

            @Override
            public User getUserFromID(int userId) {
                return user;
            }
        };

        ProfileEditOutputBoundary successPresenter = new ProfileEditOutputBoundary() {
            @Override
            public void prepareSuccessView(ProfileEditOutputData outputData) {
                assertEquals("username", outputData.getUser().getUsername());
                assertEquals("password", outputData.getUser().getPassword());
                assertEquals("English", outputData.getUser().getPreferredLanguage());
            }
        };

        SessionManager sessionManager = new SessionManager();
        ProfileEditInputBoundary interactor = new ProfileEditInteractor(userDataAccess, successPresenter, sessionManager);
        interactor.execute(inputData);
    }
}
