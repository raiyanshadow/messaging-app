package use_case.search_contact;

import java.sql.SQLException;
import java.util.List;

import entity.User;

/**
 * The user data access interface containing methods used by the search contact use case's interactor.
 */
public interface SearchContactUserDataAccessInterface {
    /**
     * Searches for a user given the keyword to filter by.
     * @param keyword filter string.
     * @return all usernames containing the keyword.
     * @throws SQLException whenever it fails to read the database.
     */
    List<User> searchUsers(String keyword) throws SQLException;
}

