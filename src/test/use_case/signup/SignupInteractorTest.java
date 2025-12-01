package use_case.signup;

import data_access.InMemoryUserDAO;
import data_access.UserDataAccessObject;
import entity.User;
import entity.UserFactory;
import org.junit.jupiter.api.Test;
import sendbirdapi.SendbirdUserCreator;
import org.openapitools.client.model.SendbirdUser;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class SignupInteractorTest {

    // -------------------------
    // Success test
    // -------------------------
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

    // -------------------------
    // Failure tests
    // -------------------------
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

    // -------------------------
    // Additional tests for 100% coverage
    // -------------------------

    @Test
    void failureSendbirdNullTest() throws SQLException {
        InMemoryUserDAO mockUserDAO = new InMemoryUserDAO();

        SignupInputData inputData = new SignupInputData(
                "newUser", "pass123", "pass123", "English"
        );

        // Sendbird stub returns null
        SendbirdUserCreator sendbirdStub = new SendbirdUserCreator("dummyAppId") {
            @Override
            public SendbirdUser createUser(String apiToken, Integer userId, String nickname) {
                return null;
            }
        };

        SignupOutputBoundary failurePresenter = new SignupOutputBoundary() {
            @Override
            public void prepareSuccessView(SignupOutputData outputData) {
                fail("Should not succeed when Sendbird fails");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("Sendbird signup failed", errorMessage);
            }

            @Override
            public void switchToLoginView() {
                // no-op
            }
        };

        SignupInputBoundary interactor =
                new SignupInteractor(mockUserDAO, failurePresenter, sendbirdStub);

        interactor.execute(inputData);

        assertFalse(mockUserDAO.existsByName("newUser"));
    }

    @Test
    void failureSQLExceptionTest() throws SQLException {
        // DAO that throws SQLException on save
        UserDataAccessObject mockDAO = new InMemoryUserDAO() {
            @Override
            public Integer save(User user) throws SQLException {
                throw new SQLException("DB save error");
            }
        };

        SignupInputData inputData = new SignupInputData(
                "newUser", "pass123", "pass123", "English"
        );

        SendbirdUserCreator sendbirdStub = new SendbirdUserCreator("dummyAppId");

        SignupOutputBoundary failurePresenter = new SignupOutputBoundary() {
            @Override
            public void prepareSuccessView(SignupOutputData outputData) {
                fail("Should not succeed when DB save fails");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertTrue(errorMessage.contains("Database error"));
            }

            @Override
            public void switchToLoginView() { }
        };

        SignupInputBoundary interactor =
                new SignupInteractor(mockDAO, failurePresenter, sendbirdStub);

        interactor.execute(inputData);
    }

    @Test
    void failureGeneralExceptionTest() throws SQLException {
        InMemoryUserDAO mockUserDAO = new InMemoryUserDAO();

        SignupInputData inputData = new SignupInputData(
                "newUser", "pass123", "pass123", "English"
        );

        // Sendbird stub throws RuntimeException
        SendbirdUserCreator sendbirdStub = new SendbirdUserCreator("dummyAppId") {
            @Override
            public SendbirdUser createUser(String apiToken, Integer userId, String nickname) {
                throw new RuntimeException("Sendbird crash");
            }
        };

        SignupOutputBoundary failurePresenter = new SignupOutputBoundary() {
            @Override
            public void prepareSuccessView(SignupOutputData outputData) {
                fail("Should not succeed when exception occurs");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertTrue(errorMessage.contains("Signup failed"));
            }

            @Override
            public void switchToLoginView() { }
        };

        SignupInputBoundary interactor =
                new SignupInteractor(mockUserDAO, failurePresenter, sendbirdStub);

        interactor.execute(inputData);

        assertFalse(mockUserDAO.existsByName("newUser"));
    }

    @Test
    void switchToLoginViewTest() throws SQLException {
        InMemoryUserDAO mockUserDAO = new InMemoryUserDAO();

        SendbirdUserCreator sendbirdStub = new SendbirdUserCreator("dummyAppId");

        final boolean[] called = {false};

        SignupOutputBoundary presenter = new SignupOutputBoundary() {
            @Override
            public void prepareSuccessView(SignupOutputData outputData) { }
            @Override
            public void prepareFailView(String errorMessage) { }
            @Override
            public void switchToLoginView() {
                called[0] = true;
            }
        };

        SignupInputBoundary interactor =
                new SignupInteractor(mockUserDAO, presenter, sendbirdStub);

        interactor.switchToLoginView();
        assertTrue(called[0], "switchToLoginView should call presenter method");
    }

    @Test
    void failureRollbackSQLExceptionTest() throws SQLException {
        // DAO that throws SQLException when deleting
        UserDataAccessObject mockDAO = new InMemoryUserDAO() {
            @Override
            public Integer save(User user) throws SQLException {
                return 1;
            }

            @Override
            public void deleteByUsername(String username) throws SQLException {
                throw new SQLException("Delete failed");
            }
        };

        SignupInputData inputData = new SignupInputData(
                "newUser", "pass123", "pass123", "English"
        );

        // Stub Sendbird to throw runtime exception
        SendbirdUserCreator sendbirdStub = new SendbirdUserCreator("dummyAppId") {
            @Override
            public SendbirdUser createUser(String apiToken, Integer userId, String nickname) {
                throw new RuntimeException("Sendbird crash");
            }
        };

        SignupOutputBoundary failurePresenter = new SignupOutputBoundary() {
            @Override
            public void prepareSuccessView(SignupOutputData outputData) {
                fail("Should not succeed");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertTrue(errorMessage.contains("Signup failed"));
            }

            @Override
            public void switchToLoginView() { }
        };

        SignupInputBoundary interactor =
                new SignupInteractor(mockDAO, failurePresenter, sendbirdStub);

        interactor.execute(inputData);
    }

}
