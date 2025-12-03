package use_case.update_chat_channel;

import java.util.List;

/**
 * Output data of update chat channel use case.
 */
public class UpdateChatChannelOutputData {
    private final String chatName;
    private final String user1Username;
    private final String user2Username;
    private final int user1ID;
    private final int user2ID;
    private final String chatUrl;
    private final List<MessageDto> messages;

    public UpdateChatChannelOutputData(String chatName, String chatUrl, String user1Username, int user1ID,
                                       String user2Username, int user2ID, List<MessageDto> messages) {
        this.chatName = chatName;
        this.user1Username = user1Username;
        this.user2Username = user2Username;
        this.user1ID = user1ID;
        this.user2ID = user2ID;
        this.chatUrl = chatUrl;
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

    public String getChatUrl() {
        return chatUrl;
    }

    public List<MessageDto> getMessages() {
        return messages;
    }
}
