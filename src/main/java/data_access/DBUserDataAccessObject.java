package data_access;

import entity.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBUserDataAccessObject implements UserDataAccessObject {

    private final Connection connection;

    public DBUserDataAccessObject(Connection connection) {
        this.connection = connection;
    }

    // Save a new user
    public void save(User user) throws SQLException {
        String query = "INSERT INTO \"user\" (username, password, preferred_language, created_at) VALUES (?, ?, ?, NOW())";
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getPreferredLanguage());
            statement.executeUpdate();

            // Get database-generated ID
            ResultSet keys = statement.getGeneratedKeys();
            if (keys.next()) {
                int generatedId = keys.getInt(1);
                user.setUserID(generatedId);
            }
        }
    }

    // Check if username exists
    public boolean existsByName(String username) throws SQLException {
        String query = "SELECT 1 FROM \"user\" WHERE username = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            return rs.next();
        }
    }

    // Get a user by ID
    public User getUserFromID(int userId) throws SQLException {
        String query = "SELECT * FROM \"user\" WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("preferred_language")
                );
            }
        }
        return null;
    }

    // Get all users
    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM \"user\"";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                users.add(new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("preferred_language")
                ));
            }
        }
        return users;
    }
}
