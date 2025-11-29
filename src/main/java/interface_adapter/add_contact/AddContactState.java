package interface_adapter.add_contact;

import entity.User;

public class AddContactState {
    private String usernameInput;
    private String addContactError;
    private User sender;
    private String receiver_username;
    private String successMessage;

    public String getUsernameInput() {
        return usernameInput;
    }

    public void setUsername(String usernameInput) {
        this.usernameInput = usernameInput;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) { this.sender = sender; }

    public String getAddContactError() {
        return addContactError;
    }

    public void setSuccessMessage(String successMessage) { this.successMessage = successMessage; }

    public String getSuccessMessage() { return successMessage; }

    public void setAddContactError(String addContactError) {
        this.addContactError = addContactError;
    }
}
