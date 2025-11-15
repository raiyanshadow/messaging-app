package interface_adapter.add_chat_channel;

import entity.User;

import java.util.ArrayList;
import java.util.List;

public class AddChatChannelState {
    private List<Integer> contactIDs;
    private String errorMessage = null;
    public AddChatChannelState() {
        this.contactIDs = new ArrayList<>();
    };
    public AddChatChannelState(List<Integer> contactIDs) {
        this.contactIDs = contactIDs;
    }

    public List<Integer> getContacts() {
        return contactIDs;
    }
    public void setContacts(List<Integer> contactIDs) {
        this.contactIDs = contactIDs;
    }

    public void setErrorMessage(String s) {
        this.errorMessage = s;
    }
    public String getErrorMessage(){
        return this.errorMessage;
    }
}
