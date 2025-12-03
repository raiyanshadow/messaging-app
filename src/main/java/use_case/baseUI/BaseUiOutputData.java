package use_case.baseUI;

import java.util.List;

import entity.Contact;
import entity.DirectChatChannel;

/**
 * Output data of the base UI use case.
 */
public class BaseUiOutputData {
    private List<String> chatNames;
    private List<DirectChatChannel> directChatChannels;
    private List<String> friendRequests;
    private List<Contact> contacts;

    public BaseUiOutputData(List<String> chatNames, List<DirectChatChannel> directChatChannels) {
        this.chatNames = chatNames;
        this.directChatChannels = directChatChannels;
    }

    public List<String> getChatNames() {
        return chatNames;
    }

    public void setChatNames(List<String> chatNames) {
        this.chatNames = chatNames;
    }

    public List<DirectChatChannel> getDirectChatChannels() {
        return directChatChannels;
    }

    public void setDirectChatChannels(List<DirectChatChannel> directChatChannels) {
        this.directChatChannels = directChatChannels;
    }

    public List<String> getFriendRequests() {
        return friendRequests;
    }

    public void setFriendRequests(List<String> friendRequests) {
        this.friendRequests = friendRequests;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }
}
