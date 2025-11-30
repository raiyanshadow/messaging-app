package use_case.update_chat_channel;

import java.util.List;

public class UpdateChatChannelOutputData {
    private final String chatName;
    private final String user1Username;
    private final String user2Username;
    private final int user1ID;
    private final int user2ID;
    private final String chatURL;
    private final List<MessageDTO> messages;

    public UpdateChatChannelOutputData(String chatName, String chatURL, String user1Username, int user1ID,
                                       String user2Username, int user2ID, List<MessageDTO> messages) {
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
    public List<MessageDTO> getMessages() {
        return messages;
    }
}