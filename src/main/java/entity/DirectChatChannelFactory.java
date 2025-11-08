package entity;

import java.util.List;
import java.util.ArrayList;

public class DirectChatChannelFactory {
    public static DirectChatChannel createDirectChatChannel(int chatID, User user1, User user2, List<Message> messages, String chatName) {
        if (user1 == null || user2 == null) {
            throw new IllegalArgumentException("Users must not be null");
        }
        List <User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        DirectChatChannel chat = new DirectChatChannel(chatID, chatName);
        chat.setMessagesSent(messages);
        return chat;
    }
    public static DirectChatChannel createEmptyChatChannel() {
        return new DirectChatChannel(-1, "");
    }
}
