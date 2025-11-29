package data_access;

import entity.User;
import java.sql.SQLException;
import java.util.List;

public interface UserDataAccessObject {

    /**
     * Checks if a user with the given username exists in the data store.
     * @param username The username to check for existence.
     * @return {@code true} if a user with the username exists, {@code false} otherwise.
     * @throws SQLException If a database access error occurs.
     */
    boolean existsByName(String username) throws SQLException;

    /**
     * Retrieves a {@code User} entity based on their unique integer ID.
     * @param UserID The unique ID of the user.
     * @return The populated {@code User} entity.
     * @throws SQLException If a database access error occurs or the user is not found.
     */
    User getUserFromID(int UserID) throws SQLException;

    /**
     * Retrieves a list of all {@code User} entities stored in the data store.
     * @return A list of all {@code User} entities.
     * @throws SQLException If a database access error occurs.
     */
    List<User> getAllUsers() throws SQLException;

    /**
     * Saves a new {@code User} entity to the data store.
     * @param user The {@code User} entity to be saved.
     * @return The integer ID generated for the new user.
     * @throws SQLException If a database access error occurs.
     */
    Integer save(User user) throws SQLException;

    /**
     * Deletes a user record from the data store based on their username.
     * @param username The username of the user to delete.
     * @throws SQLException If a database access error occurs or the user is not found.
     */
    void deleteByUsername(String username) throws SQLException;

    /**
     * Retrieves a {@code User} entity based on their unique username.
     * @param username The username of the user.
     * @return The populated {@code User} entity.
     * @throws SQLException If a database access error occurs or the user is not found.
     */
    User getUserFromName(String username) throws SQLException;

    /**
     * Creates a new friend request record from the sender to the receiver.
     * @param sender The {@code User} entity initiating the friend request.
     * @param receiverUsername The username of the user receiving the friend request.
     */
    void sendRequest(User sender, String receiverUsername);
}