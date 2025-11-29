package entity;

import java.util.ArrayList;
import java.util.List;

public class DirectChatChannel {
    private String chatName;
    private User sender;
    private User receiver;
    private String chatUrl;
    private List<Message> messages;

    public DirectChatChannel(String chatName, User sender, User receiver, String chatUrl, List<Message> messages) {
        this.chatName = chatName;
        this.sender = sender;
        this.receiver = receiver;
        this.messages = messages;
        this.chatUrl = chatUrl;
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

    public void setUser2(User recieve) {
        this.receiver = recieve;
    }

    public String getChatUrl() {
        return chatUrl;
    }

    public void setChatUrl(String chatUrl) {
        this.chatUrl = chatUrl;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public List<Long> getMessageIds() {
        final List<Long> messageIds = new ArrayList<>();
        for (Message message : messages) {
            messageIds.add(message.getMessageID());
        }
        return messageIds;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

}
