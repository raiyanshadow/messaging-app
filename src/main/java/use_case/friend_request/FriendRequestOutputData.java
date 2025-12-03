package use_case.friend_request;

/**
 * Output data for the friend request use case.
 */
public class FriendRequestOutputData {
    // username of the newly accepted friend
    private final String acceptedUsername;

    public FriendRequestOutputData(String acceptedUsername) {
        this.acceptedUsername = acceptedUsername;
    }

    public String getAcceptedUsername() {
        return acceptedUsername;
    }
}
