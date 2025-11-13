package use_case.friend_request;

import entity.User;

public class FriendRequestOutputData {
    // user1 is the user who sends out add contact request
    private final User user1;
    // user2 is the user who receives add contact request
    private final User user2;

    public FriendRequestOutputData(User user1, User user2) {
        this.user1 = user1;
        this.user2 = user2;
    }

    User getUser1() {
        return user1;
    }
    User getUser2() {
        return user2;
    }
}
