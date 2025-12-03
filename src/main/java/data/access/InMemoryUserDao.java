package data.access;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.User;
import use_case.add_contact.AddContactUserDataAccessInterface;

/**
 * An in memory data access object to represent the user table in our database.
 */
public class InMemoryUserDao implements UserDataAccessObject, AddContactUserDataAccessInterface {

    private final List<User> users = new ArrayList<>();

    @Override
    public boolean existsByName(String username) throws SQLException {
        return users.stream().anyMatch(user -> user.getUsername().equals(username));
    }

    @Override
    public Integer save(User user) throws SQLException {
        users.add(user);
        // Must return Integer to match the interface signature
        return user.getUserID();
    }

    @Override
    public void deleteByUsername(String username) throws SQLException {
        users.removeIf(user -> user.getUsername().equals(username));
    }

    @Override
    public User getUserFromName(String username) throws SQLException {
        return users.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    @Override
    public User getUserFromId(int userID) throws SQLException {
        return users.stream()
                .filter(user -> user.getUserID() == userID)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<User> getAllUsers() throws SQLException {
        return new ArrayList<>(users);
    }

    @Override
    public void sendRequest(User sender, String receiverUsername) {
        // In-memory stub: do nothing for tests
    }

    /**
     * Clears all users in the data access object.
     */
    public void clear() {
        users.clear();
    }
}
