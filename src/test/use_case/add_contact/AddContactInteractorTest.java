package use_case.add_contact;

import data.access.InMemoryContactDao;
import data.access.InMemoryUserDao;
import entity.User;
import org.junit.jupiter.api.Test;
import session.SessionManager;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class AddContactInteractorTest {
    @Test
    void successTest() throws SQLException {
        // create the mock DAOs
        InMemoryContactDao mockContactDAO = new InMemoryContactDao();
        InMemoryUserDao mockUserDAO = new InMemoryUserDao();

        // create users to populate the mock DAOs
        User alice = new User(1, "alice", "alice", "English");
        User bob = new User(2, "bob", "bob", "English");

        // populate the mockUserDAO
        mockUserDAO.save(alice);
        mockUserDAO.save(bob);

        AddContactInputData inputData = new AddContactInputData("bob");
        AddContactOutputBoundary successPresenter = new AddContactOutputBoundary() {
            @Override
            public void prepareSuccessView(AddContactOutputData addContactOutputData) {
                // alice correctly sent request to bob
                assertEquals("bob", addContactOutputData.getReceiverUsername());
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("use case has failed :((((");
            }
        };
        // set the current main user as alice
        SessionManager sessionManager = new SessionManager();
        sessionManager.setMainUser(alice);

        AddContactInputBoundary interactor = new AddContactInteractor(mockUserDAO, mockContactDAO, successPresenter, sessionManager);
        interactor.execute(inputData);
    }


    @Test
    void failureDidNotEnterUsername() throws SQLException {

        // create the mock DAOs
        InMemoryContactDao mockContactDAO = new InMemoryContactDao();
        InMemoryUserDao mockUserDAO = new InMemoryUserDao();

        // create users to populate the mock DAOs
        User alice = new User(1, "alice", "alice", "English");
        User bob = new User(2, "bob", "bob", "English");

        // populate the mockUserDAO
        mockUserDAO.save(alice);
        mockUserDAO.save(bob);

        // no input data
        AddContactInputData inputData = new AddContactInputData(null);

        AddContactOutputBoundary successPresenter = new AddContactOutputBoundary() {
            @Override
            public void prepareSuccessView(AddContactOutputData addContactOutputData) {
                fail("use case has failed");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("Please enter in a username", errorMessage);
            }
        };

        // set the current main user as alice
        SessionManager sessionManager = new SessionManager();
        sessionManager.setMainUser(alice);

        AddContactInputBoundary interactor = new AddContactInteractor(mockUserDAO, mockContactDAO, successPresenter,
                sessionManager);
        interactor.execute(inputData);
    }

    @Test
    void failureSendRequestToYourself() throws SQLException {
        // create the mock DAOs
        InMemoryContactDao mockContactDAO = new InMemoryContactDao();
        InMemoryUserDao mockUserDAO = new InMemoryUserDao();

        // create users to populate the mock DAOs
        User alice = new User(1, "alice", "alice", "English");
        User bob = new User(2, "bob", "bob", "English");

        // populate the mockUserDAO
        mockUserDAO.save(alice);
        mockUserDAO.save(bob);

        // no input data
        AddContactInputData inputData = new AddContactInputData("alice");

        AddContactOutputBoundary successPresenter = new AddContactOutputBoundary() {
            @Override
            public void prepareSuccessView(AddContactOutputData addContactOutputData) {
                fail("use case has failed");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("Can not send request to yourself", errorMessage);
            }
        };

        // set the current main user as alice
        SessionManager sessionManager = new SessionManager();
        sessionManager.setMainUser(alice);

        AddContactInputBoundary interactor = new AddContactInteractor(mockUserDAO, mockContactDAO,
                successPresenter, sessionManager);
        interactor.execute(inputData);
    }

    @Test
    void failureReceiverDoesNotExist() throws SQLException {
        // create the mock DAOs
        InMemoryContactDao mockContactDAO = new InMemoryContactDao();
        InMemoryUserDao mockUserDAO = new InMemoryUserDao();

        // create users to populate the mock DAOs
        User alice = new User(1, "alice", "alice", "English");
        User bob = new User(2, "bob", "bob", "English");

        // populate the mockUserDAO
        mockUserDAO.save(alice);
        mockUserDAO.save(bob);

        AddContactInputData inputData = new AddContactInputData("userDNE");

        AddContactOutputBoundary successPresenter = new AddContactOutputBoundary() {
            @Override
            public void prepareSuccessView(AddContactOutputData addContactOutputData) {
                fail("use case has failed");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("The user you want to add does not exist", errorMessage);
            }
        };

        // set the current main user as alice
        SessionManager sessionManager = new SessionManager();
        sessionManager.setMainUser(alice);

        AddContactInputBoundary interactor = new AddContactInteractor(mockUserDAO, mockContactDAO, successPresenter, sessionManager);
        interactor.execute(inputData);
    }

    @Test
    void failureReceiverAlreadyContact1() throws SQLException {
        // create the mock DAOs
        InMemoryContactDao mockContactDAO = new InMemoryContactDao();
        InMemoryUserDao mockUserDAO = new InMemoryUserDao();

        // create users to populate the mock DAOs
        User alice = new User(1, "alice", "alice", "English");
        User bob = new User(2, "bob", "bob", "English");

        // populate the mockUserDAO
        mockUserDAO.save(alice);
        mockUserDAO.save(bob);

        // populate the mockContactDAO
        // so now alice and bob are contacts because isFriendRequest is false
        InMemoryContactDao.DummyContact tempContact = new InMemoryContactDao.DummyContact(alice, bob, false);
        mockContactDAO.addDummyContact(tempContact);

        AddContactInputData inputData = new AddContactInputData("bob");

        AddContactOutputBoundary successPresenter = new AddContactOutputBoundary() {
            @Override
            public void prepareSuccessView(AddContactOutputData addContactOutputData) {
                fail("use case has failed");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("bob is already a contact", errorMessage);
            }
        };

        // set the current main user as alice
        SessionManager sessionManager = new SessionManager();
        sessionManager.setMainUser(alice);

        AddContactInputBoundary interactor = new AddContactInteractor(mockUserDAO, mockContactDAO, successPresenter, sessionManager);
        interactor.execute(inputData);
    }



    @Test
    void failureAlreadySentRequest() throws SQLException {
        // create the mock DAOs
        InMemoryContactDao mockContactDAO = new InMemoryContactDao();
        InMemoryUserDao mockUserDAO = new InMemoryUserDao();

        // create users to populate the mock DAOs
        User alice = new User(1, "alice", "alice", "English");
        User bob = new User(2, "bob", "bob", "English");

        // populate the mockUserDAO
        mockUserDAO.save(alice);
        mockUserDAO.save(bob);

        // populate the mockContactDAO
        // so now alice has already sent bob a request
        InMemoryContactDao.DummyContact tempContact = new InMemoryContactDao.DummyContact(alice, bob, true);
        mockContactDAO.addDummyContact(tempContact);

        AddContactInputData inputData = new AddContactInputData("bob");

        AddContactOutputBoundary successPresenter = new AddContactOutputBoundary() {
            @Override
            public void prepareSuccessView(AddContactOutputData addContactOutputData) {
                fail("use case has failed");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("You have already sent bob a friend request", errorMessage);
            }
        };

        // set the current main user as alice
        SessionManager sessionManager = new SessionManager();
        sessionManager.setMainUser(alice);

        AddContactInputBoundary interactor = new AddContactInteractor(mockUserDAO, mockContactDAO, successPresenter, sessionManager);
        interactor.execute(inputData);
    }


    @Test
    void failureReceiverHasSentRequest() throws SQLException {
        // create the mock DAOs
        InMemoryContactDao mockContactDAO = new InMemoryContactDao();
        InMemoryUserDao mockUserDAO = new InMemoryUserDao();

        // create users to populate the mock DAOs
        User alice = new User(1, "alice", "alice", "English");
        User bob = new User(2, "bob", "bob", "English");

        // populate the mockUserDAO
        mockUserDAO.save(alice);
        mockUserDAO.save(bob);

        // populate the mockContactDAO
        // so now bob has already sent alice a request
        InMemoryContactDao.DummyContact tempContact = new InMemoryContactDao.DummyContact(bob, alice, true);
        mockContactDAO.addDummyContact(tempContact);

        AddContactInputData inputData = new AddContactInputData("bob");

        AddContactOutputBoundary successPresenter = new AddContactOutputBoundary() {
            @Override
            public void prepareSuccessView(AddContactOutputData addContactOutputData) {
                fail("use case has failed");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("bob has sent you a friend request, please go and accept their friend request to add them as a contact", errorMessage);
            }
        };

        // set the current main user as alice
        SessionManager sessionManager = new SessionManager();
        sessionManager.setMainUser(alice);

        AddContactInputBoundary interactor = new AddContactInteractor(mockUserDAO, mockContactDAO, successPresenter, sessionManager);
        interactor.execute(inputData);
    }

}
