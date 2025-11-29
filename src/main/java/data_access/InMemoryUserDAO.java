package data_access;

import entity.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * In-memory implementation of UserDataAccessObject for testing purposes.
 */
public class InMemoryUserDAO implements UserDataAccessObject {

    private final List<User> users = new ArrayList<>();

    @Override
    public boolean existsByName(String username) throws SQLException {
        return users.stream().anyMatch(u -> u.getUsername().equals(username));
    }

    @Override
    public Integer save(User user) throws SQLException {
        users.add(user);
        // Must return Integer to match the interface signature
        return user.getUserID();
    }

    @Override
    public void deleteByUsername(String username) throws SQLException {
        users.removeIf(u -> u.getUsername().equals(username));
    }

    @Override
    public User getUserFromName(String username) throws SQLException {
        return users.stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    @Override
    public User getUserFromID(int userID) throws SQLException {
        return users.stream()
                .filter(u -> u.getUserID() == userID)
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

    // Optional helper for tests
    public void clear() {
        users.clear();
    }
}