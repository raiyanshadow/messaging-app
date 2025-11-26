package use_case.friend_request;

import data_access.DBContactDataAccessObject;
import data_access.DBUserDataAccessObject;
import entity.User;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class FriendRequestInteractorTest {

    private Dotenv dotenv = Dotenv.configure()
            .directory("./assets")
            .filename("env")
            .load();

    @Test
    void failureDidNotSelect() throws SQLException {
        String url = dotenv.get("DB_URL");
        String user = dotenv.get("DB_USER");
        String password = dotenv.get("DB_PASSWORD");

        Connection conn = DriverManager.getConnection(url, user, password);

        DBUserDataAccessObject dummyUserDAO = new DBUserDataAccessObject(conn);
        DBContactDataAccessObject dummyContactDAO = new DBContactDataAccessObject(conn);

        User temp =  dummyUserDAO.getUserFromID(1);

        FriendRequestInputData friendRequestInputData = new FriendRequestInputData(temp, null, true);
        FriendRequestOutputBoundary failurePresenter = new FriendRequestOutputBoundary() {
            @Override
            public void prepareSuccessView(FriendRequestOutputData friendRequestOutputData) {
                fail("use case failer");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("please select a friend request", errorMessage);

            }

        };
    }
}
