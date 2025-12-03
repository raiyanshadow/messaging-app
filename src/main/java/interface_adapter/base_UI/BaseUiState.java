package interface_adapter.base_UI;

import java.util.ArrayList;
import java.util.List;

import entity.Contact;
import entity.DirectChatChannel;

/**
 * State for the base UI use case.
 */
public class BaseUiState {
    private List<DirectChatChannel> chatEntities = new ArrayList<>();
    private List<String> chatnames = new ArrayList<>();
    private String errorMessage;
    private List<Contact> contacts = new ArrayList<>();
    private List<String> friendRequests = new ArrayList<>();

    public List<DirectChatChannel> getChatEntities() {
        return chatEntities;
    }

    public void setChatEntities(List<DirectChatChannel> chatEntities) {
        this.chatEntities = chatEntities;
    }

    public List<String> getChatnames() {
        return chatnames;
    }

    public void setChatnames(List<String> chatnames) {
        this.chatnames = chatnames;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public List<String> getFriendRequests() {
        return friendRequests;
    }

    public void setFriendRequests(List<String> friendRequests) {
        this.friendRequests = friendRequests;
    }
}
