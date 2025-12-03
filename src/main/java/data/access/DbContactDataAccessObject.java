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
import use_case.add_contact.AddContactContactDataAccessInterface;
import use_case.baseUI.BaseUiContactDataAccessInterface;
import use_case.friend_request.FriendRequestContactDataAccessInterface;

/**
 * A data access object that contains methods to extract data from the contact table in our database.
 */
public class DbContactDataAccessObject implements ContactDataAccessObject, FriendRequestContactDataAccessInterface,
        AddContactContactDataAccessInterface, BaseUiContactDataAccessInterface {

    private final Connection conn;
    private final DbUserDataAccessObject userDao;
    private final String query;
    private final String isFriendRequestString;
    private final String userIdString;
    private final String contactIdString;

    public DbContactDataAccessObject(Connection conn) {
        this.conn = conn;
        this.userDao = new DbUserDataAccessObject(this.conn);
        this.query = "SELECT * FROM \"contact\"";
        this.isFriendRequestString = "is_friend_request";
        this.userIdString = "user_id";
        this.contactIdString = "contact_id";
    }

    @Override
    public List<Contact> getContacts(User user) {
        final List<Contact> contacts = new ArrayList<>();

        try (PreparedStatement statement = conn.prepareStatement(query)) {
            final ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                final boolean isFriendRequest = rs.getBoolean(isFriendRequestString);

                // only include accepted contacts
                if (!isFriendRequest) {
                    if (rs.getInt(userIdString) == user.getUserID()) {
                        final int contactId = rs.getInt(this.contactIdString);
                        contacts.add(new Contact(user, userDao.getUserFromId(contactId)));
                    }
                    else if (rs.getInt(contactIdString) == user.getUserID()) {
                        final int contactId = rs.getInt(userIdString);
                        contacts.add(new Contact(user, userDao.getUserFromId(contactId)));
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
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            final ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                // go through contacts and if the user_id is equal to the current user's UserID
                // add the corresponding contact with contact_id to the current user's contacts list
                if (rs.getInt(userIdString) == user.getUserID() && !rs.getBoolean(isFriendRequestString)) {

                    final int contactId = rs.getInt(contactIdString);
                    contacts.add(new Contact(user, userDao.getUserFromId(contactId)));

                }

                // go through contacts and if the contact_id is equal to the current user's UserID
                // because it is possible another user added the current user
                // add the corresponding contact with user_id to the current user's contacts list

                // checking both because when id 1 adds id 5, user_id = 1, contact_id = 5
                // when 6 adds 1, user_id = 6, contact_id = 1
                // so for this scenario we want 6 to show up as 1's contact as well
                else if (rs.getInt(contactIdString) == user.getUserID()
                        && !rs.getBoolean(isFriendRequestString)) {
                    final int contactId = rs.getInt(userIdString);
                    contacts.add(new Contact(user, userDao.getUserFromId(contactId)));
                }
            }
        }
        catch (SQLException except) {
            throw new RuntimeException(except);
        }
    }

    @Override
    public void updateUserFriendRequests(User user, List<String> friendRequests) {
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            final ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                // it is only a friend request if the current user's ID is in the contact_id column
                // because if current user's ID is in the user_id column, it means the current user is
                // the one who sent out the friend request
                if (rs.getInt(contactIdString) == user.getUserID() && rs.getBoolean(isFriendRequestString)) {
                    final int contactId = rs.getInt(userIdString);
                    friendRequests.add(userDao.getNameFromId(contactId));
                }
            }
        }
        catch (SQLException except) {
            throw new RuntimeException(except);
        }
    }

    @Override
    public void acceptRequest(User accepter, String accepted_username) {
        final String acceptRequestQuery =
                "UPDATE \"contact\" SET is_friend_request = ? WHERE user_id = ? AND contact_id = ?";
        try (PreparedStatement statement = conn.prepareStatement(acceptRequestQuery, Statement.RETURN_GENERATED_KEYS)) {
            statement.setBoolean(1, false);

            statement.setInt(2, userDao.getIdFromName(accepted_username));

            final int userIdPosition = 3;
            // where contact_id = accepter's user ID because the accepter was the one who got added by accepted
            statement.setInt(userIdPosition, accepter.getUserID());

            statement.executeUpdate();

        }
        catch (SQLException except) {
            throw new RuntimeException(except);
        }
    }

    @Override
    public void deleteRequest(User accepter, String accepted_username) {
        final String deleteRequestQuery = "DELETE FROM \"contact\" WHERE user_id = ? AND contact_id = ?";
        try (PreparedStatement statement = conn.prepareStatement(deleteRequestQuery)) {
            statement.setInt(1, userDao.getIdFromName(accepted_username));
            // where contact_id = accepter's user ID because the accepter was the one who got added by accepted
            statement.setInt(2, userDao.getIdFromName(accepter.getUsername()));
            statement.executeUpdate();

        }
        catch (SQLException except) {
            throw new RuntimeException(except);
        }

    }
}
