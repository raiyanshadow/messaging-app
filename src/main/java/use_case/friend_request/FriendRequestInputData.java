package use_case.friend_request;

import entity.User;

public class FriendRequestInputData {
    // user1 is the user who sends out add contact request
    private final User user1;
    // user2 is the user who receives add contact request
    private final User user2;
    // true if user2 accepts the request
    private final boolean accept;

    public FriendRequestInputData(User user1, User user2, boolean accept) {
        this.user1 = user1;
        this.user2 = user2;
        this.accept = accept;
    }

    User getUser1() {
        return user1;
    }
    User getUser2() {
        return user2;
    }
    boolean getAccept() { return accept; }
}
