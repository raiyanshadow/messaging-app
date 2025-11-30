package interface_adapter.add_contact;


import java.util.ArrayList;
import java.util.List;

public class AddContactState {
    private String usernameInput;
    private String addContactError;
    private String successMessage;
    public List<String> matchingUsernames = new ArrayList<>();

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

    public List<String> getMatchingUsernames() {
        return matchingUsernames;
    }

    public void setMatchingUsernames(List<String> matchingUsernames) {
        this.matchingUsernames = matchingUsernames;
    }
}
