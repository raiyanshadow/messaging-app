package use_case.login;

import java.sql.SQLException;

import entity.User;

/**
 * The user data access interface containing methods used by the login use case's interactor.
 */
public interface LoginUserDataAccessInterface {

    /**
     * Validates the provided username and password against stored user data.
     * @param username The username to validate.
     * @param password The password to validate.
     * @return true if the credentials are valid, false otherwise.
     * @throws SQLException whenever it fails to read the database.
     */
    boolean validateCredentials(String username, String password) throws SQLException;

    /**
     * Retrieves a User entity based on the provided username.
     * @param username The username of the user to retrieve.
     * @return The User entity corresponding to the username.
     * @throws SQLException it fails to read the database.
     */
    User getUserFromName(String username) throws SQLException;
}
