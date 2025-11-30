package use_case.add_contact;

import data_access.ChatChannelDataAccessObject;
import data_access.DBChatChannelDataAccessObject;
import data_access.DBContactDataAccessObject;
import data_access.DBUserDataAccessObject;
import entity.User;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.Test;
import session.SessionManager;

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
    private SessionManager sessionManager = new SessionManager();
    /*
    @Test
    void successTest() throws SQLException {

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
                assertEquals(addContactOutputData.getReceiverUsername(), "test2");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("use case has failed");
            }
        };

        AddContactInputBoundary interactor = new AddContactInteractor(dummyUserDAO, dummyContactDAO, successPresenter);
        interactor.execute(addContactInputData);
    }

     */

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
        this.sessionManager.setMainUser(temp);


        AddContactInputData addContactInputData = new AddContactInputData(null);

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

        AddContactInputBoundary interactor = new AddContactInteractor(dummyUserDAO, dummyContactDAO, successPresenter,
                sessionManager);
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
        this.sessionManager.setMainUser(temp);


        AddContactInputData addContactInputData = new AddContactInputData("afiewjaiwejf");

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

        AddContactInputBoundary interactor = new AddContactInteractor(dummyUserDAO, dummyContactDAO, successPresenter,
                sessionManager);
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
        this.sessionManager.setMainUser(temp);


        AddContactInputData addContactInputData = new AddContactInputData("Bob");

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

        AddContactInputBoundary interactor = new AddContactInteractor(dummyUserDAO, dummyContactDAO, successPresenter,
                sessionManager);
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
        this.sessionManager.setMainUser(temp);


        AddContactInputData addContactInputData = new AddContactInputData("David");

        AddContactOutputBoundary successPresenter = new AddContactOutputBoundary() {
            @Override
            public void prepareSuccessView(AddContactOutputData addContactOutputData) {
                fail("use case has failed");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("You have already sent David a friend request", errorMessage);
            }
        };

        AddContactInputBoundary interactor = new AddContactInteractor(dummyUserDAO, dummyContactDAO, successPresenter,
                sessionManager);
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
        this.sessionManager.setMainUser(temp);


        AddContactInputData addContactInputData = new AddContactInputData("salem");

        AddContactOutputBoundary successPresenter = new AddContactOutputBoundary() {
            @Override
            public void prepareSuccessView(AddContactOutputData addContactOutputData) {
                fail("use case has failed");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("salem has sent you a friend request, please go and accept their friend request to add them as a contact", errorMessage);
            }
        };

        AddContactInputBoundary interactor = new AddContactInteractor(dummyUserDAO, dummyContactDAO, successPresenter,
                sessionManager);
        interactor.execute(addContactInputData);
    }

}
