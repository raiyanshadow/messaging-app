package interface_adapter.add_contact;

import entity.User;

public class AddContactState {
    private String usernameInput;
    private String addContactError;
    private User user1;
    private User user2;

    public String getUsernameInput() {
        return usernameInput;
    }

    public void setUsername(String usernameInput) {
        this.usernameInput = usernameInput;
    }

    public User getUser1(User user1) {
        return user1;
    }

    public User getUser2(User user2) {
        return user2;
    }

    public String getAddContactError() {
        return addContactError;
    }

    public void setAddContactError(String addContactError) {
        this.addContactError = addContactError;
    }
}
