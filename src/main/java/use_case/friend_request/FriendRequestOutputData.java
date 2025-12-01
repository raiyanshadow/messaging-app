package use_case.friend_request;

public class FriendRequestOutputData {
    // username of the newly accepted friend
    private final String acceptedUsername;

    // private List<Contact> updatedContactList;

    public FriendRequestOutputData(String acceptedUsername) {
        this.acceptedUsername = acceptedUsername;
    }

    public String getAcceptedUsername() {
        return acceptedUsername;
    }

    /*
    public List<Contact> getUpdatedContactList() { return updatedContactList; }
    public void setUpdatedContactList(List<Contact> updatedContactList) {
        this.updatedContactList = updatedContactList;
    }
     */
}
