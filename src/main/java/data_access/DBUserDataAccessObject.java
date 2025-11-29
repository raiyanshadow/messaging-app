package data_access;

import entity.Contact;
import entity.User;
import entity.UserFactory;
import use_case.add_contact.AddContactUserDataAccessInterface;
import use_case.login.LoginUserDataAccessInterface;
import use_case.profile_edit.ProfileEditUserDataAccessInterface;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBUserDataAccessObject implements UserDataAccessObject, AddContactUserDataAccessInterface,
        LoginUserDataAccessInterface, ProfileEditUserDataAccessInterface {

    private final Connection connection;

    public DBUserDataAccessObject(Connection connection) {
        this.connection = connection;

    }

    // Save a new user
    public Integer save(User user) throws SQLException {
        String query = "INSERT INTO \"user\" (username, password, preferred_language, created_at) VALUES (?, ?, ?, NOW())";
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getPreferredLanguage());
            statement.executeUpdate();
            ResultSet keys = statement.getGeneratedKeys();
            if (keys.next()) {
                int generatedId = keys.getInt(1);
                user.setUserID(generatedId);
            }
            return keys.getInt(1);
        }
    }

    // Delete a user by username (for rollback)
    public void deleteByUsername(String username) throws SQLException {
        String query = "DELETE FROM \"user\" WHERE username = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.executeUpdate();
        }
    }


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
                User user = new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("preferred_language")
                );
                DBChatChannelDataAccessObject chatChannelDataAccessObject = new DBChatChannelDataAccessObject(connection);
                DBContactDataAccessObject contactDataAccessObject = new DBContactDataAccessObject(connection);
                List<Contact> contacts = new ArrayList<>();
                contactDataAccessObject.updateUserContacts(user, contacts);
                user.setContacts(contacts);
                List<String> friend_requests = new ArrayList<>();
                contactDataAccessObject.updateUserFriendRequests(user, friend_requests);
                user.setFriendRequests(friend_requests);
                List<String> user_chats = chatChannelDataAccessObject.getChatURLsByUserId(user.getUserID());
                user.setUserChats(user_chats);
                return user;
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

    // Edit profile methods
    @Override
    public void updateUsername(int userId, String newUsername) throws SQLException {
        System.out.println("entered updateUsername");
        String query = "UPDATE \"user\" SET username = ? WHERE id = ?";
        try(PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, newUsername);
            statement.setInt(2, userId);
            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updatePassword(int userId, String newPassword) throws SQLException {
        String query = "UPDATE \"user\" SET password = ? WHERE id = ?";
        try(PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, newPassword);
            statement.setInt(2, userId);
            statement.executeUpdate();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updatePreferredLanguage(int userId, String newPreferredLanguage) throws SQLException {
        String query = "UPDATE \"user\" SET preferred_language = ? WHERE id = ?";
        try(PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, newPreferredLanguage);
            statement.setInt(2, userId);
            statement.executeUpdate();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

