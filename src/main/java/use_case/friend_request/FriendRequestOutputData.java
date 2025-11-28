package use_case.friend_request;

import entity.Contact;
import entity.User;

import java.util.List;

public class FriendRequestOutputData {
    // user who accepted the request
    private final User acceptee;
    private final String acceptedUsername;
    // username of the newly accepted friend

    private List<Contact> updatedContactList;

    public FriendRequestOutputData(User acceptee, String acceptedUsername) {
        this.acceptee = acceptee;
        this.acceptedUsername = acceptedUsername;
    }

    public User getAcceptee() { return acceptee; }
    public String getAcceptedUsername() { return acceptedUsername; }

    public List<Contact> getUpdatedContactList() { return updatedContactList; }
    public void setUpdatedContactList(List<Contact> updatedContactList) {
        this.updatedContactList = updatedContactList;
    }
}
