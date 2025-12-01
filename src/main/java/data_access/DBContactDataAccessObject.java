package data_access;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entity.Contact;
import entity.User;
import use_case.add_contact.AddContactContactDataAccessInterface;
import use_case.friend_request.FriendRequestUserDataAccessInterface;

public class DBContactDataAccessObject implements ContactDataAccessObject,
        FriendRequestUserDataAccessInterface, AddContactContactDataAccessInterface {

    private final Connection conn;
    private final DBUserDataAccessObject userDao;

    public DBContactDataAccessObject(Connection conn) {
        this.conn = conn;
        this.userDao = new DBUserDataAccessObject(this.conn);
    }

    @Override
    public List<Contact> getContacts(User user) {
        final List<Contact> contacts = new ArrayList<>();
        final String query = "SELECT * FROM \"contact\"";

        try (PreparedStatement statement = conn.prepareStatement(query)) {
            final ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                final boolean isFriendRequest = rs.getBoolean("is_friend_request");

                // only include accepted contacts
                if (!isFriendRequest) {
                    if (rs.getInt("user_id") == user.getUserID()) {
                        final int contactId = rs.getInt("contact_id");
                        contacts.add(new Contact(user, userDao.getUserFromID(contactId)));
                    }
                    else if (rs.getInt("contact_id") == user.getUserID()) {
                        final int contactId = rs.getInt("user_id");
                        contacts.add(new Contact(user, userDao.getUserFromID(contactId)));
                    }
                }
            }
        }
        catch (SQLException except) {
            throw new RuntimeException(except);
        }

        return contacts;
    }

    @Override
    public void updateUserContacts(User user, List<Contact> contacts) {
        final String query = "SELECT * FROM \"contact\"";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            final ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                // go through contacts and if the user_id is equal to the current user's UserID
                // add the corresponding contact with contact_id to the current user's contacts list
                if (rs.getInt("user_id") == user.getUserID() && !rs.getBoolean("is_friend_request")) {

                    final int contactId = rs.getInt("contact_id");
                    contacts.add(new Contact(user, userDao.getUserFromID(contactId)));

                }

                // go through contacts and if the contact_id is equal to the current user's UserID
                // because it is possible another user added the current user
                // add the corresponding contact with user_id to the current user's contacts list

                // checking both because when id 1 adds id 5, user_id = 1, contact_id = 5
                // when 6 adds 1, user_id = 6, contact_id = 1
                // so for this scenario we want 6 to show up as 1's contact as well
                else if (rs.getInt("contact_id") == user.getUserID() && !rs.getBoolean("is_friend_request")) {
                    final int contactId = rs.getInt("user_id");
                    contacts.add(new Contact(user, userDao.getUserFromID(contactId)));
                }
            }
        }
        catch (SQLException except) {
            throw new RuntimeException(except);
        }
    }

    @Override
    public void updateUserFriendRequests(User user, List<String> friendRequests) {
        final String query = "SELECT * FROM \"contact\"";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            final ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                // it is only a friend request if the current user's ID is in the contact_id column
                // because if current user's ID is in the user_id column, it means the current user
                // is the one who sent out the friend request
                if (rs.getInt("contact_id") == user.getUserID() && rs.getBoolean("is_friend_request")) {
                    final int contactId = rs.getInt("user_id");
                    friendRequests.add(userDao.getNameFromID(contactId));
                }
            }
        }
        catch (SQLException except) {
            throw new RuntimeException(except);
        }
    }

    @Override
    public void acceptRequest(User accepter, String accepted_username) {
        final String query = "UPDATE \"contact\" SET is_friend_request = ? WHERE user_id = ? AND contact_id = ?";
        try (PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setBoolean(1, false);

            // System.out.println(userDAO.getIDFromName(accepted_username));
            // System.out.println(acceptee.getUserID());

            statement.setInt(2, userDao.getIDFromName(accepted_username));

            // where contact_id = accepter's user ID because the accepter was the one who got added by accepted
            statement.setInt(3, accepter.getUserID());

            statement.executeUpdate();

        }
        catch (SQLException except) {
            throw new RuntimeException(except);
        }
    }

    @Override
    public void deleteRequest(User accepter, String accepted_username) {

        System.out.println("deleting request...");
        final String query = "DELETE FROM \"contact\" WHERE user_id = ? AND contact_id = ?";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, userDao.getIDFromName(accepted_username));
            // where contact_id = accepter's user ID because the accepter was the one who got added by accepted
            statement.setInt(2, userDao.getIDFromName(accepter.getUsername()));
            statement.executeUpdate();

        }
        catch (SQLException except) {
            throw new RuntimeException(except);
        }

    }
}
