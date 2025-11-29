package data_access;

import entity.Contact;
import entity.User;
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
    public Integer save(User user) throws SQLException {
        final String query = "INSERT INTO \"user\" (username, password, preferred_language, created_at) VALUES (?, ?, ?, NOW())";
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getPreferredLanguage());
            statement.executeUpdate();

            // Get database-generated ID
            final ResultSet keys = statement.getGeneratedKeys();
            if (keys.next()) {
                final int generatedId = keys.getInt(1);
                user.setUserID(generatedId);
            }
            return keys.getInt(1);
        }
    }

    // Delete a user by username (for rollback)
    public void deleteByUsername(String username) throws SQLException {
        final String query = "DELETE FROM \"user\" WHERE username = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.executeUpdate();
        }
    }


    public boolean existsByName(String username) throws SQLException {
        final String query = "SELECT 1 FROM \"user\" WHERE username = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            final ResultSet rs = statement.executeQuery();
            return rs.next();
        }
    }

    @Override
    public void sendRequest(User sender, String receiver_username) {
        final String query = "INSERT INTO \"contact\" (user_id, contact_id, is_friend_request, created_at) "
                + "VALUES (?, ?, ?, NOW())";
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, this.getIDFromName(sender.getUsername()));
            statement.setInt(2, this.getIDFromName(receiver_username));
            statement.setBoolean(3, true);
            statement.executeUpdate();

        }
        catch (SQLException except) {
            throw new RuntimeException(except);
        }
    }

    public User getUserFromID(int userId) throws SQLException {
        final String query = "SELECT * FROM \"user\" WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            final ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                final User user = new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("preferred_language")
                );
                final DBChatChannelDataAccessObject chatChannelDataAccessObject = new DBChatChannelDataAccessObject(connection);
                final List<String> chatUrls = chatChannelDataAccessObject.getChatURLsByUserId(user.getUserID());
                user.setUserChats(chatUrls);
                return user;
            }
        }
        return null;
    }


    public List<User> getAllUsers() throws SQLException {
        final List<User> users = new ArrayList<>();
        final String query = "SELECT * FROM \"user\"";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            final ResultSet rs = statement.executeQuery();
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
        final String query = "SELECT * FROM \"user\" WHERE username = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username); // use String for name
            final ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                final User user = new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("preferred_language")
                );
                final DBChatChannelDataAccessObject chatChannelDataAccessObject = new DBChatChannelDataAccessObject(connection);
                final DBContactDataAccessObject contactDataAccessObject = new DBContactDataAccessObject(connection);
                final List<Contact> contacts = new ArrayList<>();
                contactDataAccessObject.updateUserContacts(user, contacts);
                user.setContacts(contacts);
                final List<String> friendRequests = new ArrayList<>();
                contactDataAccessObject.updateUserFriendRequests(user, friendRequests);
                user.setFriendRequests(friendRequests);
                final List<String> userChats = chatChannelDataAccessObject.getChatURLsByUserId(user.getUserID());
                user.setUserChats(userChats);
                return user;
            }
        }
        // No user found
        return null;
    }

    public int getIDFromName(String username) throws SQLException {
        final String query = "SELECT * FROM \"user\" WHERE username = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            // use String for name
            statement.setString(1, username);
            final ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            }
        }
        return 0;
    }


    public String getNameFromID(int id) throws SQLException {
        final String query = "SELECT * FROM \"user\" WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            final ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return rs.getString("username");
            }
        }
        return null;
    }

    public List<User> searchUsers(String keyword) throws SQLException {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM \"user\" WHERE username LIKE ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, "%" + keyword + "%");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                users.add(new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("preferred_language")));
            }
        }
        return users;
    }

    @Override
    public boolean validateCredentials(String username, String password) throws SQLException {
        final String query = "SELECT * FROM \"user\" WHERE username = ? AND password = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, password);
            final ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return true;
            }
        }
        return false;
    }
    public List<User> searchUsers(String keyword) throws SQLException {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM \"user\" WHERE username LIKE ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, "%" + keyword + "%");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                users.add(new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("preferred_language")));
            }
        }
        return users;
    }
}

