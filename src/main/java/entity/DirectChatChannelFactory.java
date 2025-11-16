package entity;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

public class DirectChatChannelFactory {
    public static DirectChatChannel createDirectChatChannel(String chatName, User user1, User user2, String channelURL, List<Message> messages) {
        if (user1 == null || user2 == null) {
            throw new IllegalArgumentException("Users must not be null");
        }
        return new DirectChatChannel(chatName, user1, user2, messages);
    }
    public static DirectChatChannel createEmptyChatChannel() {
        return new DirectChatChannel("", new User(-1, "","", ""), new User(-1, "","", ""), new ArrayList<>());
    }
}
