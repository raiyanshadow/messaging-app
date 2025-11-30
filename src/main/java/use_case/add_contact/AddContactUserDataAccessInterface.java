package use_case.add_contact;

import entity.User;

import java.sql.SQLException;

public interface AddContactUserDataAccessInterface {

    /**
     *
     * @param username the username who we want to add to our contacts
     * @return true if the given user exists; meaning it is possible to add to contacts
     */
    boolean existsByName(String username) throws SQLException;

    /**
     * sender send a contact request to receiver
     * @param sender the user who wants to add a contact (sends out add contact request)
     * @param receiver_username the user to be added (receives add contact request)
     */
    void sendRequest(User sender, String receiver_username);

    /**
     * get the user of the given username
     * @param username the username of the user we want to get
     * @return the user of the corresponding username
     */
    User getUserFromName(String username) throws SQLException;
}
