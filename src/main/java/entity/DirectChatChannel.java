package entity;

import java.util.ArrayList;
import java.util.List;


public class DirectChatChannel {
    private Integer chatID;
    private String chatName;
    private User user1;
    private User user2;
    private String chatURL;
    private List<Message> messages;

    public DirectChatChannel(Integer chatID, String chatName, User user1, User user2) {
        this.chatID = chatID;
        this.chatName = chatName;
        this.user1 = user1;
        this.user2 = user2;
        this.messages = messages;
    }

    public Integer getChatID() {
        return chatID;
    }
    public void setChatID(Integer chatID) {
        this.chatID = chatID;
    }
    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public User getUser2() {
        return user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
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
