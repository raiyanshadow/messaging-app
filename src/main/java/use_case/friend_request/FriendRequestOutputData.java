package use_case.friend_request;

public class FriendRequestOutputData {
    // the username of the person who got accepted/declined
    private final String acceptedUsername;

    public FriendRequestOutputData(String accepted_username) {
        this.acceptedUsername = accepted_username;
    }

    public String getAcceptedUsername() { return acceptedUsername; }
}
