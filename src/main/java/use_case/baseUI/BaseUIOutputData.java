package use_case.baseUI;

import entity.Contact;
import entity.DirectChatChannel;

import java.io.LineNumberInputStream;
import java.util.List;

public class BaseUIOutputData  {
    private List<String> chatNames;
    private List<DirectChatChannel> directChatChannels;
    private List<String> friendRequests;
    private List<Contact> contacts;

    public BaseUIOutputData(List<String> chatNames, List<DirectChatChannel> directChatChannels) {
        this.chatNames = chatNames;
        this.directChatChannels = directChatChannels;
    }

    public BaseUIOutputData(){};

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
