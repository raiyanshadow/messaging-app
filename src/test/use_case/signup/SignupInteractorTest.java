package use_case.signup;

import data_access.InMemoryUserDAO;
import entity.User;
import entity.UserFactory;
import org.junit.jupiter.api.Test;
import SendBirdAPI.SendbirdUserCreator;
import org.openapitools.client.model.SendbirdUser;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class SignupInteractorTest {

    @Test
    void successTest() throws SQLException {
        InMemoryUserDAO mockUserDAO = new InMemoryUserDAO();
        UserFactory userFactory = new UserFactory();

        // Sendbird stub
        SendbirdUserCreator sendbirdStub = new SendbirdUserCreator("dummyAppId") {
            @Override
            public SendbirdUser createUser(String apiToken, String userId, String nickname) {
                SendbirdUser sbUser = new SendbirdUser();
                sbUser.setUserId(userId);
                sbUser.setNickname(nickname);
                return sbUser;
            }
        };

        SignupInputData inputData = new SignupInputData(
                "newUser",
                "strongPass",
                "strongPass",
                "English"
        );

        SignupOutputBoundary successPresenter = new SignupOutputBoundary() {
            @Override
            public void prepareSuccessView(SignupOutputData data) {
                assertEquals("newUser", data.getUsername());
            }

            @Override
            public void prepareFailView(String error) {
                fail("Signup should not fail: " + error);
            }

            @Override
            public void switchToLoginView() {
                // No action needed for tests
            }
        };

        SignupInputBoundary interactor =
                new SignupInteractor(mockUserDAO, successPresenter, sendbirdStub);

        interactor.execute(inputData);

        assertTrue(mockUserDAO.existsByName("newUser"));
    }

    @Test
    void failureUsernameExistsTest() throws SQLException {
        InMemoryUserDAO mockUserDAO = new InMemoryUserDAO();
        UserFactory userFactory = new UserFactory();

        // Pre-store an existing user
        mockUserDAO.save(new User(1, "existingUser", "abc", "English"));

        // Sendbird stub
        SendbirdUserCreator sendbirdStub = new SendbirdUserCreator("dummyAppId") {
            @Override
            public SendbirdUser createUser(String apiToken, String userId, String nickname) {
                return null; // won't be called in this test
            }
        };

        SignupInputData inputData = new SignupInputData(
                "existingUser",
                "password123",
                "password123",
                "English"
        );

        SignupOutputBoundary failurePresenter = new SignupOutputBoundary() {
            @Override
            public void prepareSuccessView(SignupOutputData data) {
                fail("Should not succeed when username exists");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("User already exists.", error);
            }

            @Override
            public void switchToLoginView() {
                // No action needed
            }
        };

        SignupInputBoundary interactor =
                new SignupInteractor(mockUserDAO, failurePresenter, sendbirdStub);

        interactor.execute(inputData);
    }

    @Test
    void failurePasswordsDoNotMatchTest() throws SQLException {
        InMemoryUserDAO mockUserDAO = new InMemoryUserDAO();
        UserFactory userFactory = new UserFactory();

        // Sendbird stub
        SendbirdUserCreator sendbirdStub = new SendbirdUserCreator("dummyAppId") {
            @Override
            public SendbirdUser createUser(String apiToken, String userId, String nickname) {
                return null; // won't be called
            }
        };

        SignupInputData inputData = new SignupInputData(
                "newUser",
                "pass123",
                "differentPass",
                "English"
        );

        SignupOutputBoundary failurePresenter = new SignupOutputBoundary() {
            @Override
            public void prepareSuccessView(SignupOutputData data) {
                fail("Should not succeed with mismatched passwords");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Passwords don't match.", error);
            }

            @Override
            public void switchToLoginView() {
                // No action needed
            }
        };

        SignupInputBoundary interactor =
                new SignupInteractor(mockUserDAO, failurePresenter, sendbirdStub);

        interactor.execute(inputData);
    }

    @Test
    void failureMissingUsernameTest() throws SQLException {
        InMemoryUserDAO mockUserDAO = new InMemoryUserDAO();
        UserFactory userFactory = new UserFactory();

        // Sendbird stub
        SendbirdUserCreator sendbirdStub = new SendbirdUserCreator("dummyAppId") {
            @Override
            public SendbirdUser createUser(String apiToken, String userId, String nickname) {
                return null;
            }
        };

        SignupInputData inputData = new SignupInputData(
                "",
                "pass123",
                "pass123",
                "English"
        );

        SignupOutputBoundary failurePresenter = new SignupOutputBoundary() {
            @Override
            public void prepareSuccessView(SignupOutputData data) {
                fail("Should not succeed with empty username");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Username cannot be empty", error);
            }

            @Override
            public void switchToLoginView() {
                // No action needed
            }
        };

        SignupInputBoundary interactor =
                new SignupInteractor(mockUserDAO, failurePresenter, sendbirdStub);

        interactor.execute(inputData);
    }

    @Test
    void failureMissingPasswordTest() throws SQLException {
        InMemoryUserDAO mockUserDAO = new InMemoryUserDAO();
        UserFactory userFactory = new UserFactory();

        // Sendbird stub
        SendbirdUserCreator sendbirdStub = new SendbirdUserCreator("dummyAppId") {
            @Override
            public SendbirdUser createUser(String apiToken, String userId, String nickname) {
                return null;
            }
        };

        SignupInputData inputData = new SignupInputData(
                "newUser",
                "",
                "",
                "English"
        );

        SignupOutputBoundary failurePresenter = new SignupOutputBoundary() {
            @Override
            public void prepareSuccessView(SignupOutputData data) {
                fail("Should not succeed with empty password");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Password cannot be empty", error);
            }

            @Override
            public void switchToLoginView() {
                // No action needed
            }
        };

        SignupInputBoundary interactor =
                new SignupInteractor(mockUserDAO, failurePresenter, sendbirdStub);

        interactor.execute(inputData);
    }
}
