package use_case.update_chat_channel;

import java.util.List;

public class UpdateChatChannelOutputData {
    private String chatName;
    private User user1;
    private User user2;
    private String chatURL;
    private List<Message<String>> messages;

    public UpdateChatChannelOutputData(String chatName, String chatURL, User user1, User user2, List<Message<String>> messages) {
        this.chatName = chatName;
        this.user1Username = user1Username;
        this.user2Username = user2Username;
        this.user1ID = user1ID;
        this.user2ID = user2ID;
        this.chatURL = chatURL;
        this.messages = messages;
    }

    public String getChatName() {
        return chatName;
    }
    public String getUser1Username() {
        return user1Username;
    }
    public String getUser2Username() {
        return user2Username;
    }
    public int getUser1ID() {
        return user1ID;
    }
    public int getUser2ID() {
        return user2ID;
    }
    public String getChatURL() {
        return chatURL;
    }
    public List<Message<String>> getMessages() {
        return messages;
    }
}
