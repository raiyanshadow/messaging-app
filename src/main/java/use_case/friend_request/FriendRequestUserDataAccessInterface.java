package use_case.friend_request;

import entity.Contact;
import entity.User;
import java.util.List;

public interface FriendRequestUserDataAccessInterface {

    /**
     * Accept a friend request
     * @param accepter person who is accepting the request
     * @param acceptedUsername person who got accepted
     */
    void acceptRequest(User accepter, String acceptedUsername);

    /**
     * Decline a friend request
     * @param decliner user who is declining request
     * @param acceptedUsername person whose request got declined
     */
    void deleteRequest(User decliner, String acceptedUsername);

    /**
     * Fetch the updated contacts for a user
     * @param user the user whose contacts are needed
     * @return list of Contact objects
     */
    List<Contact> getContacts(User user);
}
