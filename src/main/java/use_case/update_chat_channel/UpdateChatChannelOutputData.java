package use_case.update_chat_channel;

import entity.Message;
import entity.User;

import java.util.List;
import java.util.ArrayList;

public class UpdateChatChannelOutputData {
    private String chatName;
    private User user1;
    private User user2;
    private String chatURL;
    private List<Message> messages;

    public UpdateChatChannelOutputData(String chatName, String chatURL, User user1, User user2, List<Message> messages) {
        this.chatName = chatName;
        this.user1 = user1;
        this.user2 = user2;
        this.chatURL = chatURL;
        this.messages = messages;
    }

    public String getChatName() {
        return chatName;
    }
    public User getUser1() {
        return user1;
    }
    public User getUser2() {
        return user2;
    }
    public String getChatURL() {
        return chatURL;
    }
    public List<Message> getMessages() {
        return messages;
    }
}
