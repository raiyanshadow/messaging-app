package use_case.friend_request;

import entity.User;

public interface FriendRequestUserDataAccessInterface {

    /**
     *
     * @param acceptee person who is accepting the request
     * @param accepted_username person who got accepted
     */
    void acceptRequest(User acceptee, String accepted_username);

    /**
     *
     * @param acceptee person who is declining request
     * @param accepted_username person whose request got declined
     */
    void deleteRequest(User acceptee, String accepted_username);

}
