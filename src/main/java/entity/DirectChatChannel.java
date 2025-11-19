package entity;

import java.util.ArrayList;
import java.util.List;


public class DirectChatChannel {
    private String chatName;
    private User sender;
    private User receiver;
    private String chatURL;
    private List<Message> messages;

    public DirectChatChannel(String chatName, User sender, User receiver, String chatURL, List<Message> messages) {
        this.chatName = chatName;
        this.sender = sender;
        this.receiver = receiver;
        this.messages = messages;
        this.chatURL = chatURL;
    }

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public User getUser1() {
        return sender;
    }

    public void setUser1(User user1) {
        this.sender = user1;
    }

    public User getUser2() {
        return receiver;
    }

    public void setUser2(User receiver) {
        this.receiver = receiver;
    }

    public String getChatURL() {
        return chatURL;
    }

    public void setChatURL(String chatURL) {
        this.chatURL = chatURL;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public List<Integer> getMessageIDs() {
        List<Integer> messageIDs = new ArrayList<>();
        for  (Message message : messages) {
            messageIDs.add(message.getMessageID());
        }
        return messageIDs;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

}
