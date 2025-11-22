package use_case.friend_request;

import entity.User;

public class FriendRequestInputData {
    // user1 is the user who sends out add contact request
    private final User acceptee;
    // user2 is the user who receives add contact request
    private final String accepted_username;
    // true if user2 accepts the request
    private final boolean accept;

    public FriendRequestInputData(User acceptee, String accepted_username, boolean accept) {
        this.acceptee = acceptee;
        this.accepted_username = accepted_username;
        this.accept = accept;
    }

    User getAcceptee() { return acceptee; }
    String getAccepted_username() { return accepted_username; }
    boolean getAccept() { return accept; }
}
