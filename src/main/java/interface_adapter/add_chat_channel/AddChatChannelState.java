package interface_adapter.add_chat_channel;

import java.util.ArrayList;
import java.util.List;

/**
 * State for the add chat channel use case.
 */
public class AddChatChannelState {
    private List<Integer> contactIds;
    private String errorMessage;
    private boolean creationSuccess;

    public AddChatChannelState() {
        this.contactIds = new ArrayList<>();
        errorMessage = null;
        creationSuccess = false;
    }

    public AddChatChannelState(List<Integer> contactIds) {
        this.contactIds = contactIds;
    }

    public List<Integer> getContacts() {
        return contactIds;
    }

    public void setContacts(List<Integer> newContactIds) {
        this.contactIds = newContactIds;
    }

    public void setErrorMessage(String error) {
        this.errorMessage = error;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public boolean isCreationSuccess() {
        return creationSuccess;
    }

    public void setCreationSuccess(boolean creationSuccess) {
        this.creationSuccess = creationSuccess;
    }
}
