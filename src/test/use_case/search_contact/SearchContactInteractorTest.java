package use_case.search_contact;

import entity.User;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class SearchContactInteractorTest {

    @Test
    void successTest() throws SQLException {
        SearchContactInputData inputData = new SearchContactInputData("John");

        SearchContactUserDataAccessInterface userDataAccess = new SearchContactUserDataAccessInterface() {
            @Override
            public List<User> searchUsers(String query) {
                List<User> users = new ArrayList<>();
                users.add(new User(1, "John", "password", "English"));
                users.add(new User(2, "Johnny", "password", "English"));
                return users;
            }
        };

        SearchContactOutputBoundary successPresenter = new SearchContactOutputBoundary() {
            @Override
            public void prepareSuccessView(SearchContactOutputData outputData) {
                assertFalse(outputData.isUseCaseFailed());
                assertEquals(2, outputData.getMatchingUsernames().size());
                assertEquals("John", outputData.getMatchingUsernames().get(0));
                assertEquals("Johnny", outputData.getMatchingUsernames().get(1));
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case failure is unexpected.");
            }
        };

        SearchContactInputBoundary interactor = new SearchContactInteractor(userDataAccess, successPresenter);
        interactor.execute(inputData);
    }

    @Test
    void failureNoUsersFoundTest() throws SQLException {
        SearchContactInputData inputData = new SearchContactInputData("NonExistentUser");

        SearchContactUserDataAccessInterface userDataAccess = new SearchContactUserDataAccessInterface() {
            @Override
            public List<User> searchUsers(String query) {
                return new ArrayList<>();
            }
        };

        SearchContactOutputBoundary failurePresenter = new SearchContactOutputBoundary() {
            @Override
            public void prepareSuccessView(SearchContactOutputData outputData) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("No users found.", error);
            }
        };

        SearchContactInputBoundary interactor = new SearchContactInteractor(userDataAccess, failurePresenter);
        interactor.execute(inputData);
    }

    @Test
    void failureEmptyQueryTest() throws SQLException {
        SearchContactInputData inputData = new SearchContactInputData("");

        SearchContactUserDataAccessInterface userDataAccess = new SearchContactUserDataAccessInterface() {
            @Override
            public List<User> searchUsers(String query) {
                fail("searchUsers should not be called with empty query.");
                return null;
            }
        };

        SearchContactOutputBoundary failurePresenter = new SearchContactOutputBoundary() {
            @Override
            public void prepareSuccessView(SearchContactOutputData outputData) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Please enter a username to search.", error);
            }
        };

        SearchContactInputBoundary interactor = new SearchContactInteractor(userDataAccess, failurePresenter);
        interactor.execute(inputData);
    }

    @Test
    void failureNullQueryTest() throws SQLException {
        SearchContactInputData inputData = new SearchContactInputData(null);

        SearchContactUserDataAccessInterface userDataAccess = new SearchContactUserDataAccessInterface() {
            @Override
            public List<User> searchUsers(String query) {
                fail("searchUsers should not be called with null query.");
                return null;
            }
        };

        SearchContactOutputBoundary failurePresenter = new SearchContactOutputBoundary() {
            @Override
            public void prepareSuccessView(SearchContactOutputData outputData) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Please enter a username to search.", error);
            }
        };

        SearchContactInputBoundary interactor = new SearchContactInteractor(userDataAccess, failurePresenter);
        interactor.execute(inputData);
    }

    @Test
    void failureWhitespaceQueryTest() throws SQLException {
        SearchContactInputData inputData = new SearchContactInputData("   ");

        SearchContactUserDataAccessInterface userDataAccess = new SearchContactUserDataAccessInterface() {
            @Override
            public List<User> searchUsers(String query) {
                fail("searchUsers should not be called with whitespace-only query.");
                return null;
            }
        };

        SearchContactOutputBoundary failurePresenter = new SearchContactOutputBoundary() {
            @Override
            public void prepareSuccessView(SearchContactOutputData outputData) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Please enter a username to search.", error);
            }
        };

        SearchContactInputBoundary interactor = new SearchContactInteractor(userDataAccess, failurePresenter);
        interactor.execute(inputData);
    }
}
