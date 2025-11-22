package data_access;

import entity.Contact;
import entity.User;
import use_case.friend_request.FriendRequestUserDataAccessInterface;

import java.sql.*;
import java.util.List;

/**
 * TODO: Implement updateContacts(List<Contact> contact)
 */
public class DBContactDataAccessObject implements ContactDataAccessObject, FriendRequestUserDataAccessInterface {

    private final Connection conn;
    private final DBUserDataAccessObject userDAO;

    public DBContactDataAccessObject(Connection conn) {
        this.conn = conn;
        this.userDAO = new DBUserDataAccessObject(this.conn);
    }

    @Override
    public void updateUserContacts(User user, List<Contact> contacts) {
        String query = "SELECT * FROM \"contact\"";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                // go through contacts and if the user_id is equal to the current user's UserID
                // add the corresponding contact with contact_id to the current user's contacts list
                if (rs.getInt("user_id") == user.getUserID() && !rs.getBoolean("is_friend_request")) {

                    int contact_id = rs.getInt("contact_id");
                    contacts.add(new Contact(user, userDAO.getUserFromID(contact_id)));

                }

                // go through contacts and if the contact_id is equal to the current user's UserID
                // because it is possible another user added the current user
                // add the corresponding contact with user_id to the current user's contacts list

                // checking both because when id 1 adds id 5, user_id = 1, contact_id = 5
                // when 6 adds 1, user_id = 6, contact_id = 1
                // so for this scenario we want 6 to show up as 1's contact as well
                else if (rs.getInt("contact_id") == user.getUserID() && !rs.getBoolean("is_friend_request")) {
                    int contact_id = rs.getInt("user_id");
                    contacts.add(new Contact(user, userDAO.getUserFromID(contact_id)));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateUserFriendRequests(User user, List<String> friendRequests) {
        String query = "SELECT * FROM \"contact\"";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                // go through contacts and if the user_id is equal to the current user's UserID
                // add the corresponding contact with contact_id to the current user's contacts list
                if (rs.getInt("user_id") == user.getUserID() && rs.getBoolean("is_friend_request")) {

                    int contact_id = rs.getInt("contact_id");
                    friendRequests.add(userDAO.getNameFromID(contact_id));
                }

                // go through contacts and if the contact_id is equal to the current user's UserID
                // because it is possible another user added the current user
                // add the corresponding contact with user_id to the current user's contacts list

                // checking both because when id 1 adds id 5, user_id = 1, contact_id = 5
                // when 6 adds 1, user_id = 6, contact_id = 1
                // so for this scenario we want 6 to show up as 1's contact as well
                else if (rs.getInt("contact_id") == user.getUserID() && rs.getBoolean("is_friend_request")) {
                    int contact_id = rs.getInt("user_id");
                    friendRequests.add(userDAO.getNameFromID(contact_id));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void acceptRequest(User acceptee, String accepted_username) {
        String query = "UPDATE \"contact\" SET is_friend_request = ? WHERE user_id = ? AND contact_id = ?";
        try (PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setBoolean(1, false);
            statement.setInt(2, acceptee.getUserID());
            statement.setInt(3, userDAO.getIDFromName(accepted_username));
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteRequest(User accepter, String accepted_username) {
        // maybe need a DBFriendRequestDAO that is similar to DBContactDAO (?)
        // that stores all the friend requests in the same way (?)
        System.out.println("deleting request...");
        String query = "DELETE FROM \"contact\" WHERE user_id = ? AND contact_id = ?";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, userDAO.getIDFromName(accepter.getUsername()));
            statement.setInt(2, userDAO.getIDFromName(accepted_username));
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
