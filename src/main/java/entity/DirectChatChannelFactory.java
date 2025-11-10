package entity;

public class DirectChatChannelFactory {
    public static DirectChatChannel createDirectChatChannel(Integer chatID, int chatId, User user1, User user2, String channelURL, String chatName) {
        if (user1 == null || user2 == null) {
            throw new IllegalArgumentException("Users must not be null");
        }
        return new DirectChatChannel(chatID, chatName, channelURL, user1, user2);
    }
    public static DirectChatChannel createEmptyChatChannel() {
        return new DirectChatChannel(-1, "", "", new User(-1, "", "", ""), new User(-1, "", "", ""));
    }
}
