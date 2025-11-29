package interface_adapter.add_contact;


public class AddContactState {
    private String usernameInput;
    private String addContactError;
    private String successMessage;

    public String getUsernameInput() {
        return usernameInput;
    }

    public void setUsername(String usernameInput) {
        this.usernameInput = usernameInput;
    }

    public String getAddContactError() {
        return addContactError;
    }

    public void setSuccessMessage(String successMessage) { this.successMessage = successMessage; }

    public String getSuccessMessage() { return successMessage; }

    public void setAddContactError(String addContactError) {
        this.addContactError = addContactError;
    }
}
