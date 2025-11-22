package interface_adapter.friend_request;

import entity.User;

public class FriendRequestState {
    private String friendRequestError;
    private User acceptee;
    private String accepted_username;

    public String getFriendRequestError() {
        return friendRequestError;
    }

    public void setFriendRequestError(String friendRequestError) {
        this.friendRequestError = friendRequestError;
    }

    public User getAcceptee() {
        return acceptee;
    }

    public String getAccepted_username() {
        return accepted_username;
    }
}
