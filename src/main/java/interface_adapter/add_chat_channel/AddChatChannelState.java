package interface_adapter.add_chat_channel;

import entity.User;

import java.util.List;

public class AddChatChannelState {
    private List<String> contacts;

    public AddChatChannelState(List<String> contacts) {
        this.contacts = contacts;
    }

    public List<String> getContacts() {
        return contacts;
    }
    public void setContacts(List<String> contacts) {
        this.contacts = contacts;
    }
}
