package use_case.baseUI;

import entity.Contact;
import entity.User;

import java.sql.SQLException;
import java.util.List;

/**
 * The contact data access interface containing methods needed by the base UI use case's interactor.
 */
public interface BaseUiContactDataAccessInterface {
    /**
     * Get a list of contact entities for the given user.
     * @param user User entity to search contacts for.
     * @return list of contact entities.
     */
    List<Contact> getContacts(User user);

    /**
     * Update the contacts of the user and mutate the given list of contact entities to update accordingly.
     * @param user the user to update contacts for.
     * @param contacts the list of contact entities to mutate.
     */
    void updateUserContacts(User user, List<Contact> contacts);

    /**
     * Update the friend requests of the user and mutate the given list of friend requests to update accordingly.
     * @param user the user to update friend requests for.
     * @param friendRequests the list of friends requests to mutate.
     */
    void updateUserFriendRequests(User user, List<String> friendRequests);
}
