package interface_adapter.add_contact;

import entity.User;

public class AddContactState {
    private String usernameInput;
    private String addContactError;
    private User sender;
    private String receiver_username;

    public String getUsernameInput() {
        return usernameInput;
    }

    public void setUsername(String usernameInput) {
        this.usernameInput = usernameInput;
    }

    public User getSender() {
        return sender;
    }

    public String getReceiver_username() {
        return receiver_username;
    }

    public String getAddContactError() {
        return addContactError;
    }

    public void setAddContactError(String addContactError) {
        this.addContactError = addContactError;
    }
}
