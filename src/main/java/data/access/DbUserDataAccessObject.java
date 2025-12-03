package data.access;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entity.Contact;
import entity.User;
import use_case.add_contact.AddContactUserDataAccessInterface;
import use_case.login.LoginUserDataAccessInterface;
import use_case.profile_edit.ProfileEditUserDataAccessInterface;
import use_case.search_contact.SearchContactUserDataAccessInterface;

/**
 * The data access object containing methods to extract data from the user table in our database.
 */
public class DbUserDataAccessObject implements UserDataAccessObject, AddContactUserDataAccessInterface,
        LoginUserDataAccessInterface, ProfileEditUserDataAccessInterface, SearchContactUserDataAccessInterface {

    private final Connection connection;
    private final String idString;
    private final String usernameString;
    private final String passwordString;
    private final String preferredLanguageString;

    public DbUserDataAccessObject(Connection connection) {
        this.connection = connection;
        idString = "id";
        usernameString = "username";
        passwordString = "password";
        preferredLanguageString = "preferred_language";
    }

    /**
     * Saves a new user.
     * @param user The {@code User} entity to be saved.
     * @return The integer id of the added user.
     * @throws SQLException whenever we cant add to the database.
     */
    public Integer save(User user) throws SQLException {
        final String query =
                "INSERT INTO \"user\" (username, password, preferred_language, created_at) VALUES (?, ?, ?, NOW())";
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());

            final int preferredLanguagePosition = 3;
            statement.setString(preferredLanguagePosition, user.getPreferredLanguage());
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

    /**
     * Deletes a user in the database by their username.
     * @param username The username of the user to delete.
     * @throws SQLException whenever we can't access or modify the database.
     */
    public void deleteByUsername(String username) throws SQLException {
        final String query = "DELETE FROM \"user\" WHERE username = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.executeUpdate();
        }
    }

    /**
     * Check if a user exists in the database by checking the username field.
     * @param username The username to check for existence.
     * @return True if user found, false otherwise.
     * @throws SQLException whenever we can't access the database.
     */
    public boolean existsByName(String username) throws SQLException {
        final String query = "SELECT 1 FROM \"user\" WHERE username = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            final ResultSet rs = statement.executeQuery();
            return rs.next();
        }
    }

    @Override
    public void sendRequest(User sender, String receiverUsername) {
        final String query = "INSERT INTO \"contact\" (user_id, contact_id, is_friend_request, created_at) "
                + "VALUES (?, ?, ?, NOW())";
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, this.getIdFromName(sender.getUsername()));
            statement.setInt(2, this.getIdFromName(receiverUsername));
            final int isFriendRequestPosition = 3;
            statement.setBoolean(isFriendRequestPosition, true);
            statement.executeUpdate();

        }
        catch (SQLException except) {
            throw new RuntimeException(except);
        }
    }

    /**
     * Accesses database using the userId to generate a User entity.
     * @param userId The unique ID of the user.
     * @return A generated User entity corresponding to the user id.
     * @throws SQLException whenever we can't access the database.
     */
    public User getUserFromId(int userId) throws SQLException {
        final String query = "SELECT * FROM \"user\" WHERE id = ?";
        User user = null;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            final ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                user = new User(
                        rs.getInt(idString),
                        rs.getString(usernameString),
                        rs.getString(passwordString),
                        rs.getString(preferredLanguageString)
                );
                final DbChatChannelDataAccessObject chatChannelDataAccessObject =
                        new DbChatChannelDataAccessObject(connection);
                final List<String> chatUrls = chatChannelDataAccessObject.getChatUrlsByUserId(user.getUserID());
                user.setUserChats(chatUrls);
            }
        }
        return user;
    }

    /**
     * Gathers all users from the database.
     * @return A list of User entities using information the database.
     * @throws SQLException whenever we can't access the database.
     */
    public List<User> getAllUsers() throws SQLException {
        final List<User> users = new ArrayList<>();
        final String query = "SELECT * FROM \"user\"";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            final ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                users.add(new User(
                        rs.getInt(idString),
                        rs.getString(usernameString),
                        rs.getString(passwordString),
                        rs.getString(preferredLanguageString)
                ));
            }
        }
        return users;
    }

    /**
     * Generates a user entity from a given username.
     * @param username The username of the user.
     * @return User entity containing information of the user by their username.
     * @throws SQLException whenever we can't access the database.
     */
    public User getUserFromName(String username) throws SQLException {
        final String query = "SELECT * FROM \"user\" WHERE username = ?";
        User user = null;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            final ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                user = new User(
                        rs.getInt(idString),
                        rs.getString(usernameString),
                        rs.getString(passwordString),
                        rs.getString(preferredLanguageString)
                );
                final DbChatChannelDataAccessObject chatChannelDataAccessObject =
                        new DbChatChannelDataAccessObject(connection);
                final DbContactDataAccessObject contactDataAccessObject = new DbContactDataAccessObject(connection);
                final List<Contact> contacts = new ArrayList<>();
                contactDataAccessObject.updateUserContacts(user, contacts);
                user.setContacts(contacts);
                final List<String> friendRequests = new ArrayList<>();
                contactDataAccessObject.updateUserFriendRequests(user, friendRequests);
                user.setFriendRequests(friendRequests);
                final List<String> userChats = chatChannelDataAccessObject.getChatUrlsByUserId(user.getUserID());
                user.setUserChats(userChats);
            }
        }
        // No user found
        return user;
    }

    /**
     * Get the id of a user from their username.
     * @param username The given username to access in the database.
     * @return The id of associated username.
     * @throws SQLException whenever we can't access the database.
     */
    public int getIdFromName(String username) throws SQLException {
        final String query = "SELECT * FROM \"user\" WHERE username = ?";
        int ret = 0;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            // use String for name
            statement.setString(1, username);
            final ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                ret = rs.getInt(idString);
            }
        }
        return ret;
    }

    /**
     * Get the name of a user by their id.
     * @param id The id of the user to search for in the database.
     * @return The name of the associated user id.
     * @throws SQLException whenever we can't access the database.
     */
    public String getNameFromId(int id) throws SQLException {
        final String query = "SELECT * FROM \"user\" WHERE id = ?";
        String username = null;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            final ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                username = rs.getString(usernameString);
            }
        }
        return username;
    }

    /**
     * Search for a username in the database using some keyword.
     * @param keyword A keyword to filter by.
     * @return The users that contain the keyword.
     * @throws SQLException whenever we can't access the database.
     */
    public List<User> searchUsers(String keyword) throws SQLException {
        final List<User> users = new ArrayList<>();
        final String query = "SELECT * FROM \"user\" WHERE username LIKE ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, "%" + keyword + "%");
            final ResultSet rs = statement.executeQuery();
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
        boolean ret = false;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, password);
            final ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                ret = true;
            }
        }
        return ret;
    }

    // Edit profile methods
    @Override
    public boolean updateUsername(int userId, String newUsername) throws SQLException {
        System.out.println("entered updateUsername");
        final String query = "UPDATE \"user\" SET username = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, newUsername);
            statement.setInt(2, userId);
            statement.executeUpdate();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        return false;
    }

    @Override
    public void updatePassword(int userId, String newPassword) throws SQLException {
        final String query = "UPDATE \"user\" SET password = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, newPassword);
            statement.setInt(2, userId);
            statement.executeUpdate();
        }
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void updatePreferredLanguage(int userId, String newPreferredLanguage) throws SQLException {
        final String query = "UPDATE \"user\" SET preferred_language = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, newPreferredLanguage);
            statement.setInt(2, userId);
            statement.executeUpdate();
        }
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}

