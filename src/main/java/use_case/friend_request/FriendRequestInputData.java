package use_case.friend_request;

public class FriendRequestInputData {
    // the username of the person who got accepted/declined
    private final String acceptedUsername;
    // true if the accepter accepts the request
    private final boolean accept;

    public FriendRequestInputData(String acceptedUsername, boolean accept) {
        this.acceptedUsername = acceptedUsername;
        this.accept = accept;
    }

    String getAcceptedUsername() { return acceptedUsername; }
    boolean getAccept() { return accept; }
}
