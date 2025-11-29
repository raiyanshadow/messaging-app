package use_case.add_contact;

import data_access.*;
import entity.User;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class AddContactInteractorTest {
    private Dotenv dotenv = Dotenv.configure()
            .directory("./assets")
            .filename("env")
            .load();

    @Test
    void successTest() throws SQLException {
        // create the mock DAOs
        InMemoryContactDAO mockContactDAO = new InMemoryContactDAO();
        InMemoryUserDAO mockUserDAO = new InMemoryUserDAO();

        // create users to populate the mock DAOs
        User alice = new User(1, "alice", "alice", "English");
        User bob = new User(2, "bob", "bob", "English");
        User dave = new User(3, "dave", "dave", "English");
        User sam = new User(4, "sam", "sam", "English");

        mockUserDAO.save(alice);
        mockUserDAO.save(bob);
        mockUserDAO.save(dave);
        mockUserDAO.save(sam);

        System.out.println(mockUserDAO.getAllUsers());
    }

    /*
    @Test
    void failureDidNotEnterUsername() throws SQLException {
        String url = dotenv.get("DB_URL");
        String user = dotenv.get("DB_USER");
        String password = dotenv.get("DB_PASSWORD");

        Connection conn = DriverManager.getConnection(url, user, password);

        DBUserDataAccessObject dummyUserDAO = new DBUserDataAccessObject(conn);
        DBContactDataAccessObject dummyContactDAO = new DBContactDataAccessObject(conn);

        // user temp is Alice since she has the userid 1
        User temp =  dummyUserDAO.getUserFromID(1);


        AddContactInputData addContactInputData = new AddContactInputData(temp, null);

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

        AddContactInputBoundary interactor = new AddContactInteractor(dummyUserDAO, dummyContactDAO, successPresenter);
        interactor.execute(addContactInputData);
    }

    @Test
    void failureReceiverDoesNotExist() throws SQLException {
        String url = dotenv.get("DB_URL");
        String user = dotenv.get("DB_USER");
        String password = dotenv.get("DB_PASSWORD");

        Connection conn = DriverManager.getConnection(url, user, password);

        DBUserDataAccessObject dummyUserDAO = new DBUserDataAccessObject(conn);
        DBContactDataAccessObject dummyContactDAO = new DBContactDataAccessObject(conn);

        // user temp is Alice since she has the userid 1
        User temp =  dummyUserDAO.getUserFromID(1);


        AddContactInputData addContactInputData = new AddContactInputData(temp, "afiewjaiwejf");

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

        AddContactInputBoundary interactor = new AddContactInteractor(dummyUserDAO, dummyContactDAO, successPresenter);
        interactor.execute(addContactInputData);
    }

    @Test
    void failureReceiverAlreadyContact() throws SQLException {
        String url = dotenv.get("DB_URL");
        String user = dotenv.get("DB_USER");
        String password = dotenv.get("DB_PASSWORD");

        Connection conn = DriverManager.getConnection(url, user, password);

        DBUserDataAccessObject dummyUserDAO = new DBUserDataAccessObject(conn);
        DBContactDataAccessObject dummyContactDAO = new DBContactDataAccessObject(conn);

        // user temp is Alice since she has the userid 1
        User temp =  dummyUserDAO.getUserFromID(1);


        AddContactInputData addContactInputData = new AddContactInputData(temp, "Bob");

        AddContactOutputBoundary successPresenter = new AddContactOutputBoundary() {
            @Override
            public void prepareSuccessView(AddContactOutputData addContactOutputData) {
                fail("use case has failed");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("This user is already a contact", errorMessage);
            }
        };

        AddContactInputBoundary interactor = new AddContactInteractor(dummyUserDAO, dummyContactDAO, successPresenter);
        interactor.execute(addContactInputData);
    }

    @Test
    void failureAlreadySentRequest() throws SQLException {
        String url = dotenv.get("DB_URL");
        String user = dotenv.get("DB_USER");
        String password = dotenv.get("DB_PASSWORD");

        Connection conn = DriverManager.getConnection(url, user, password);

        DBUserDataAccessObject dummyUserDAO = new DBUserDataAccessObject(conn);
        DBContactDataAccessObject dummyContactDAO = new DBContactDataAccessObject(conn);

        // user temp is Alice since she has the userid 1
        User temp =  dummyUserDAO.getUserFromID(1);


        AddContactInputData addContactInputData = new AddContactInputData(temp, "test2");

        AddContactOutputBoundary successPresenter = new AddContactOutputBoundary() {
            @Override
            public void prepareSuccessView(AddContactOutputData addContactOutputData) {
                fail("use case has failed");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("You have already sent test2 a friend request", errorMessage);
            }
        };

        AddContactInputBoundary interactor = new AddContactInteractor(dummyUserDAO, dummyContactDAO, successPresenter);
        interactor.execute(addContactInputData);
    }


    @Test
    void failureReceiverHasSentRequest() throws SQLException {
        String url = dotenv.get("DB_URL");
        String user = dotenv.get("DB_USER");
        String password = dotenv.get("DB_PASSWORD");

        Connection conn = DriverManager.getConnection(url, user, password);

        DBUserDataAccessObject dummyUserDAO = new DBUserDataAccessObject(conn);
        DBContactDataAccessObject dummyContactDAO = new DBContactDataAccessObject(conn);

        // user temp is Alice since she has the userid 1
        User temp =  dummyUserDAO.getUserFromID(1);


        AddContactInputData addContactInputData = new AddContactInputData(temp, "test4");

        AddContactOutputBoundary successPresenter = new AddContactOutputBoundary() {
            @Override
            public void prepareSuccessView(AddContactOutputData addContactOutputData) {
                fail("use case has failed");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("test4 has sent you a friend request, please go and accept their friend request to add them as a contact", errorMessage);
            }
        };

        AddContactInputBoundary interactor = new AddContactInteractor(dummyUserDAO, dummyContactDAO, successPresenter);
        interactor.execute(addContactInputData);
    }
    */
}
