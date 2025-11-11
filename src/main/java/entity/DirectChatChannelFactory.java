package entity;

import java.sql.Timestamp;
import java.util.List;
import java.util.ArrayList;

public class DirectChatChannelFactory {
    public static DirectChatChannel createDirectChatChannel(Integer chatID, User user1, User user2, String channelURL, String chatName) {
        if (user1 == null || user2 == null) {
            throw new IllegalArgumentException("Users must not be null");
        }
        return new DirectChatChannel(chatID, chatName, channelURL, user1, user2, messages);
    }
    public static DirectChatChannel createEmptyChatChannel() {
        return new DirectChatChannel(-1, "", "", new User(-1, "", "", ""), new User(-1, "", "", ""), new ArrayList<>());
    }
}
