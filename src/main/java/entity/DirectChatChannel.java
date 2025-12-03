package entity;

import java.util.List;

/**
 * DirectChatChannel entity to represent a chat channel between two User entities containing a
 * list of AbstractMessage entities.
 */
public class DirectChatChannel {
    private String chatName;
    private final User sender;
    private final User receiver;
    private String chatUrl;
    private List<AbstractMessage> messages;

    public DirectChatChannel(String chatName, User sender, User receiver, String chatUrl,
                             List<AbstractMessage> messages) {
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

    public User getUser2() {
        return receiver;
    }

    public String getChatUrl() {
        return chatUrl;
    }

    public void setChatUrl(String chatUrl) {
        this.chatUrl = chatUrl;
    }

    public List<AbstractMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<AbstractMessage> messages) {
        this.messages = messages;
    }

}
