package use_case.friend_request;

import entity.User;

public class FriendRequestOutputData {
    // user1 is the user who sends out add contact request
    private final User acceptee;
    // user2 is the user who receives add contact request
    private final String accepted_username;

    public FriendRequestOutputData(User acceptee, String accepted_username) {
        this.acceptee = acceptee;
        this.accepted_username = accepted_username;
    }

    User getAcceptee() { return acceptee; }
    String getAccepted_username() { return accepted_username; }
}
