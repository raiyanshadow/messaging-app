package use_case.login;

import entity.User;

public interface LoginUserDataAccessInterface {

    /**
     * Validates the provided username and password against stored user data.
     *
     * @param username The username to validate.
     * @param password The password to validate.
     * @return true if the credentials are valid, false otherwise.
     */
    boolean validateCredentials(String username, String password);

    /**
     * Retrieves a User entity based on the provided username.
     *
     * @param username The username of the user to retrieve.
     * @return The User entity corresponding to the username.
     */
    User getUserByUsername(String username);
}
