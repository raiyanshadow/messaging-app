package data_access;

import entity.Contact;
import java.util.List;
import entity.User;

public interface ContactDataAccessObject {
    /**
     * Retrieves and updates the list of accepted contacts for a specific User.
     * @param user The {@code User} entity whose contacts are being updated.
     * @param contacts The {@code List<Contact>} object to be populated with the user's
     */
    void updateUserContacts(User user, List<Contact> contacts);

    /**
     * Retrieves and updates the list of pending incoming friend requests for a specific User.
     * @param user The {@code User} entity whose incoming friend requests are being updated.
     * @param friendRequests The {@code List<String>} object to be populated with the
     */
    void updateUserFriendRequests(User user, List<String> friendRequests);

    /**
     * Retrieves the contacts of specific user provided in the parameter.
     * @param user The {@code User} entity to find contacts for
     */
    List<Contact> getContacts(User user);
}
