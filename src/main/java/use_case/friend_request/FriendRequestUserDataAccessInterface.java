package use_case.friend_request;

import entity.User;

public interface FriendRequestUserDataAccessInterface {

    /**
     * accepts the request and both users are added to each other's contact lists
     * @param user1 user who sent out request
     * @param user2 user who received request
     */
    void acceptRequest(User user1, User user2);

    /**
     * declines the request and the request is removed from both users' lists
     * @param user1 user who sent out request
     * @param user2 user who received request
     */
    void deleteRequest(User user1, User user2);

}
