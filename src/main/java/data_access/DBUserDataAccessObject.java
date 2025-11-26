package data_access;

import entity.User;
import entity.UserFactory;
import use_case.add_contact.AddContactUserDataAccessInterface;
import use_case.login.LoginUserDataAccessInterface;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBUserDataAccessObject implements UserDataAccessObject, AddContactUserDataAccessInterface,
        LoginUserDataAccessInterface {

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

    @Override
    public void sendRequest(User sender, String receiver_username) {
        String query = "INSERT INTO \"contact\" (user_id, contact_id, is_friend_request, created_at) VALUES (?, ?, ?, NOW())";
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, this.getIDFromName(sender.getUsername()));
            statement.setInt(2, this.getIDFromName(receiver_username));
            statement.setBoolean(3, true);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Get a user by ID
    public User getUserFromID(int userId) throws SQLException {
        String query = "SELECT * FROM \"user\" WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                User user = new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("preferred_language")
                );
                DBChatChannelDataAccessObject chatChannelDataAccessObject = new DBChatChannelDataAccessObject(connection);
                List<String> chatUrls = chatChannelDataAccessObject.getChatURLsByUserId(user.getUserID());
                user.setUserChats(chatUrls);
                return user;
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

    public User getUserFromName(String username) throws SQLException {
        String query = "SELECT * FROM \"user\" WHERE username = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username); // use String for name
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
        return null; // no user found
    }

    public int getIDFromName(String username) throws SQLException {
        String query = "SELECT * FROM \"user\" WHERE username = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username); // use String for name
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            }
        }
        // no user found so no userID
        return 0;
    }

    public String getNameFromID(int id) throws SQLException {
        String query = "SELECT * FROM \"user\" WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id); // use String for name
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return rs.getString("username");
            }
        }
        // no user found so no username
        return null;
    }

    @Override
    public boolean validateCredentials(String username, String password) throws SQLException {
        String query = "SELECT * FROM \"user\" WHERE username = ? AND password = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public User getUserByUsername(String username) throws SQLException {
        String query =  "SELECT * FROM \"user\" WHERE username = ? RETURNING id, username, password, preferred_language";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return new User(rs.getInt("id"), rs.getString("username"),
                        rs.getString("password"), rs.getString("preferred_language"));
            }
        }
        throw new SQLException();
    }
}
