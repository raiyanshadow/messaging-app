package interface_adapter.friend_request;

import entity.User;

public class FriendRequestState {
    private String friendRequestError;
    private User acceptee;
    private String accepted_username;
    private Boolean acceptornot;

    public String getFriendRequestError() {
        return friendRequestError;
    }

    public void setFriendRequestError(String friendRequestError) {
        this.friendRequestError = friendRequestError;
    }

    public User getAcceptee() {
        return acceptee;
    }

    public void setAcceptee(User acceptee) {
        this.acceptee = acceptee;
    }

    public String getAccepted_username() {
        return accepted_username;
    }

    public void setAccepted_username(String accepted_username) {
        this.accepted_username = accepted_username;
    }

    public Boolean getAcceptornot() {
        return acceptornot;
    }

    public void setAcceptornot(Boolean acceptornot) {
        this.acceptornot = acceptornot;
    }
}
