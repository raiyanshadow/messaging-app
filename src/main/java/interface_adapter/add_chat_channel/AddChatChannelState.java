package interface_adapter.add_chat_channel;

import entity.User;

import java.util.List;

public class AddChatChannelState {
    private List<Integer> contactIDs;

    public AddChatChannelState(List<Integer> contactIDs) {
        this.contactIDs = contactIDs;
    }

    public List<Integer> getContacts() {
        return contactIDs;
    }
    public void setContacts(List<Integer> contacts) {
        this.contactIDs = contactIDs;
    }
}
