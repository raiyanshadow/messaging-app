package use_case.friend_request;

import entity.Contact;
import entity.User;
import java.util.List;

public interface FriendRequestUserDataAccessInterface {

    /**
     * Accept a friend request
     * @param acceptee person who is accepting the request
     * @param accepted_username person who got accepted
     */
    void acceptRequest(User acceptee, String accepted_username);

    /**
     * Decline a friend request
     * @param acceptee person who is declining request
     * @param accepted_username person whose request got declined
     */
    void deleteRequest(User acceptee, String accepted_username);

    /**
     * Fetch the updated contacts for a user
     * @param user the user whose contacts are needed
     * @return list of Contact objects
     */
    List<Contact> getContacts(User user);
}
