package use_case.friend_request;


import data_access.InMemoryContactDAO;

import entity.User;

import org.junit.jupiter.api.Test;
import session.SessionManager;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class FriendRequestInteractorTest {

    @Test
    void successTest() throws SQLException {
        // create the mock DAOs
        InMemoryContactDAO mockContactDAO = new InMemoryContactDAO();

        // create users to populate the mock DAOs
        User alice = new User(1, "alice", "alice", "English");
        User bob = new User(2, "bob", "bob", "English");


        // populate the mockContactDAO
        // so now bob has sent alice a friend request
        InMemoryContactDAO.DummyContact tempContact = new InMemoryContactDAO.DummyContact(bob, alice, true);
        mockContactDAO.addDummyContact(tempContact);

        FriendRequestInputData inputData = new FriendRequestInputData("bob", true);
        FriendRequestOutputBoundary successPresenter = new FriendRequestOutputBoundary() {
            @Override
            public void prepareSuccessView(FriendRequestOutputData friendRequestOutputData) {
                assertEquals("bob", friendRequestOutputData.getAcceptedUsername());

                // what the mockContactDAO should be like
                InMemoryContactDAO expectedMockContactDAO = new InMemoryContactDAO();
                InMemoryContactDAO.DummyContact expectedContact = new InMemoryContactDAO.DummyContact(bob, alice, false);
                expectedMockContactDAO.addDummyContact(expectedContact);

                // check if the contents of the two are equal (if we correctly updated the mockContactDAO
                // (user, contact, isFriendRequest)
                for (int i = 0; i<expectedMockContactDAO.getAllContacts().size();i++) {
                    assertEquals(expectedMockContactDAO.getAllContacts().get(i).getUser(), mockContactDAO.getAllContacts().get(i).getUser());
                    assertEquals(expectedMockContactDAO.getAllContacts().get(i).getContact(), mockContactDAO.getAllContacts().get(i).getContact());
                    assertEquals(expectedMockContactDAO.getAllContacts().get(i).getIsFriendRequest(), mockContactDAO.getAllContacts().get(i).getIsFriendRequest());
                }

            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("use case failed");

            }

        };
        // set the current main user as alice
        SessionManager sessionManager = new SessionManager();
        sessionManager.setMainUser(alice);

        FriendRequestInputBoundary interactor = new FriendRequestInteractor(mockContactDAO, successPresenter, sessionManager);
        interactor.execute(inputData);
    }

    @Test
    void declineRequest() throws SQLException {
        // create the mock DAOs
        InMemoryContactDAO mockContactDAO = new InMemoryContactDAO();

        // create users to populate the mock DAOs
        User alice = new User(1, "alice", "alice", "English");
        User bob = new User(2, "bob", "bob", "English");


        // populate the mockContactDAO
        // so now bob has sent alice a friend request
        InMemoryContactDAO.DummyContact tempContact = new InMemoryContactDAO.DummyContact(bob, alice, true);
        mockContactDAO.addDummyContact(tempContact);

        FriendRequestInputData inputData = new FriendRequestInputData("bob", false);
        FriendRequestOutputBoundary successPresenter = new FriendRequestOutputBoundary() {
            @Override
            public void prepareSuccessView(FriendRequestOutputData friendRequestOutputData) {
                fail("use case failed");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("you have declined the friend request from: bob", errorMessage);

                // what the mockContactDAO should be like
                InMemoryContactDAO expectedMockContactDAO = new InMemoryContactDAO();

                // check if the contents of the two are equal (if we correctly updated the mockContactDAO
                // (user, contact, isFriendRequest)
                for (int i = 0; i<mockContactDAO.getAllContacts().size();i++) {
                    assertEquals(expectedMockContactDAO.getAllContacts().get(i).getUser(), mockContactDAO.getAllContacts().get(i).getUser());
                    assertEquals(expectedMockContactDAO.getAllContacts().get(i).getContact(), mockContactDAO.getAllContacts().get(i).getContact());
                    assertEquals(expectedMockContactDAO.getAllContacts().get(i).getIsFriendRequest(), mockContactDAO.getAllContacts().get(i).getIsFriendRequest());
                }
            }

        };
        // set the current main user as alice
        SessionManager sessionManager = new SessionManager();
        sessionManager.setMainUser(alice);

        FriendRequestInputBoundary interactor = new FriendRequestInteractor(mockContactDAO, successPresenter, sessionManager);
        interactor.execute(inputData);
    }

    @Test
    void failureDidNotSelectAccept() throws SQLException {
        // create the mock DAOs
        InMemoryContactDAO mockContactDAO = new InMemoryContactDAO();

        // create users to populate the mock DAOs
        User alice = new User(1, "alice", "alice", "English");
        User bob = new User(2, "bob", "bob", "English");


        // populate the mockContactDAO
        // so now bob has sent alice a friend request
        InMemoryContactDAO.DummyContact tempContact = new InMemoryContactDAO.DummyContact(bob, alice, true);
        mockContactDAO.addDummyContact(tempContact);

        FriendRequestInputData inputData = new FriendRequestInputData(null, true);
        FriendRequestOutputBoundary successPresenter = new FriendRequestOutputBoundary() {
            @Override
            public void prepareSuccessView(FriendRequestOutputData friendRequestOutputData) {
                fail("use case failed");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("please select a friend request", errorMessage);

                // what the mockContactDAO should be like
                InMemoryContactDAO expectedMockContactDAO = new InMemoryContactDAO();
                InMemoryContactDAO.DummyContact expectedContact = new InMemoryContactDAO.DummyContact(bob, alice, true);
                expectedMockContactDAO.addDummyContact(expectedContact);

                // check if the contents of the two are equal (if we correctly updated the mockContactDAO
                // (user, contact, isFriendRequest)
                for (int i = 0; i<mockContactDAO.getAllContacts().size();i++) {
                    assertEquals(expectedMockContactDAO.getAllContacts().get(i).getUser(), mockContactDAO.getAllContacts().get(i).getUser());
                    assertEquals(expectedMockContactDAO.getAllContacts().get(i).getContact(), mockContactDAO.getAllContacts().get(i).getContact());
                    assertEquals(expectedMockContactDAO.getAllContacts().get(i).getIsFriendRequest(), mockContactDAO.getAllContacts().get(i).getIsFriendRequest());
                }
            }

        };
        // set the current main user as alice
        SessionManager sessionManager = new SessionManager();
        sessionManager.setMainUser(alice);

        FriendRequestInputBoundary interactor = new FriendRequestInteractor(mockContactDAO, successPresenter, sessionManager);
        interactor.execute(inputData);
    }

    @Test
    void failureDidNotSelectDecline() throws SQLException {
        // create the mock DAOs
        InMemoryContactDAO mockContactDAO = new InMemoryContactDAO();

        // create users to populate the mock DAOs
        User alice = new User(1, "alice", "alice", "English");
        User bob = new User(2, "bob", "bob", "English");


        // populate the mockContactDAO
        // so now bob has sent alice a friend request
        InMemoryContactDAO.DummyContact tempContact = new InMemoryContactDAO.DummyContact(bob, alice, true);
        mockContactDAO.addDummyContact(tempContact);

        FriendRequestInputData inputData = new FriendRequestInputData(null, false);
        FriendRequestOutputBoundary successPresenter = new FriendRequestOutputBoundary() {
            @Override
            public void prepareSuccessView(FriendRequestOutputData friendRequestOutputData) {
                fail("use case failed");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("please select a friend request", errorMessage);

                // what the mockContactDAO should be like
                InMemoryContactDAO expectedMockContactDAO = new InMemoryContactDAO();
                InMemoryContactDAO.DummyContact expectedContact = new InMemoryContactDAO.DummyContact(bob, alice, true);
                expectedMockContactDAO.addDummyContact(expectedContact);

                // check if the contents of the two are equal (if we correctly updated the mockContactDAO
                // (user, contact, isFriendRequest)
                for (int i = 0; i<mockContactDAO.getAllContacts().size();i++) {
                    assertEquals(expectedMockContactDAO.getAllContacts().get(i).getUser(), mockContactDAO.getAllContacts().get(i).getUser());
                    assertEquals(expectedMockContactDAO.getAllContacts().get(i).getContact(), mockContactDAO.getAllContacts().get(i).getContact());
                    assertEquals(expectedMockContactDAO.getAllContacts().get(i).getIsFriendRequest(), mockContactDAO.getAllContacts().get(i).getIsFriendRequest());
                }
            }

        };
        // set the current main user as alice
        SessionManager sessionManager = new SessionManager();
        sessionManager.setMainUser(alice);

        FriendRequestInputBoundary interactor = new FriendRequestInteractor(mockContactDAO, successPresenter, sessionManager);
        interactor.execute(inputData);
    }



}
