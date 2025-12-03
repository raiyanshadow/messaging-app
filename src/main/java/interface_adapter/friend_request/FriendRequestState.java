package interface_adapter.friend_request;

/**
 * State of the friend request use case.
 */
public class FriendRequestState {
    private String friendRequestError;
    private String acceptedUsername;
    private String successMessage;

    public String getFriendRequestError() {
        return friendRequestError;
    }

    public void setFriendRequestError(String friendRequestError) {
        this.friendRequestError = friendRequestError;
    }

    public String getAcceptedUsername() {
        return acceptedUsername;
    }

    public void setAcceptedUsername(String acceptedUsername) {
        this.acceptedUsername = acceptedUsername;
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }
}
