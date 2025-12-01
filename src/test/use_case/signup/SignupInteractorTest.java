package use_case.signup;

import data_access.InMemoryUserDAO;
import entity.User;
import entity.UserFactory;
import org.junit.jupiter.api.Test;
import sendbirdapi.SendbirdUserCreator;
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
            public SendbirdUser createUser(String apiToken, Integer userId, String nickname) {
                SendbirdUser sbUser = new SendbirdUser();
                sbUser.setUserId(userId.toString());
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
            public void prepareSuccessView(SignupOutputData outputData) {
                assertEquals("newUser", outputData.getUsername());
            }

            public void prepareFailView(String errorMessage) {
                fail("Signup should not fail: " + errorMessage);
            }

            public void switchToLoginView() {
                // no-op
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

        SendbirdUserCreator sendbirdStub = new SendbirdUserCreator("dummyAppId") {
            @Override
            public SendbirdUser createUser(String apiToken, Integer userId, String nickname) {
                return null; // won't be called
            }
        };

        SignupInputData inputData = new SignupInputData(
                "existingUser",
                "password123",
                "password123",
                "English"
        );

        SignupOutputBoundary failurePresenter = new SignupOutputBoundary() {
            public void prepareSuccessView(SignupOutputData outputData) {
                fail("Should not succeed when username exists");
            }

            public void prepareFailView(String errorMessage) {
                assertEquals("User already exists.", errorMessage);
            }

            public void switchToLoginView() {
                // no-op
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

        SendbirdUserCreator sendbirdStub = new SendbirdUserCreator("dummyAppId") {
            @Override
            public SendbirdUser createUser(String apiToken, Integer userId, String nickname) {
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
            public void prepareSuccessView(SignupOutputData outputData) {
                fail("Should not succeed with mismatched passwords");
            }

            public void prepareFailView(String errorMessage) {
                assertEquals("Passwords don't match.", errorMessage);
            }

            public void switchToLoginView() {
                // no-op
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

        SendbirdUserCreator sendbirdStub = new SendbirdUserCreator("dummyAppId") {
            @Override
            public SendbirdUser createUser(String apiToken, Integer userId, String nickname) {
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
            public void prepareSuccessView(SignupOutputData outputData) {
                fail("Should not succeed with empty username");
            }

            public void prepareFailView(String errorMessage) {
                assertEquals("Username cannot be empty", errorMessage);
            }

            public void switchToLoginView() {
                // no-op
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

        SendbirdUserCreator sendbirdStub = new SendbirdUserCreator("dummyAppId") {
            @Override
            public SendbirdUser createUser(String apiToken, Integer userId, String nickname) {
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
            public void prepareSuccessView(SignupOutputData outputData) {
                fail("Should not succeed with empty password");
            }

            public void prepareFailView(String errorMessage) {
                assertEquals("Password cannot be empty", errorMessage);
            }

            public void switchToLoginView() {
                // no-op
            }
        };

        SignupInputBoundary interactor =
                new SignupInteractor(mockUserDAO, failurePresenter, sendbirdStub);

        interactor.execute(inputData);
    }
}
