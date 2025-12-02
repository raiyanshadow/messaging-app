package use_case.add_contact;

import java.util.List;

import entity.Contact;
import entity.User;

public interface AddContactContactDataAccessInterface {
    /**
     * Update the given user's contacts list.
     * @param user the user who we want to update their contacts
     * @param contacts the user's contacts list
     */

    void updateUserContacts(User user, List<Contact> contacts);

    /**
     * Update the given user's friend requests list.
     * @param user the user who we want to update their friend requests list
     * @param friendRequests the user's friend requests list
     */
    void updateUserFriendRequests(User user, List<String> friendRequests);
}
