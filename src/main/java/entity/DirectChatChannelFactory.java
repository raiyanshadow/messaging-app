package entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Simply factory for the DirectChatChannel entity.
 */
public class DirectChatChannelFactory {
    /**
     * Returns a Direct Chat Channel Entity based on needed inputs.
     * @param chatName name of the chat channel.
     * @param user1 A user of the chat channel.
     * @param user2 The other user of the chat channel.
     * @param chatUrl The SendBird URL for the established chat channel.
     * @param messages The list of AbstractMessage entities for the chat channel.
     * @return the Direct Chat Channel entity.
     * @throws IllegalArgumentException if any of the users are null type.
     */
    public static DirectChatChannel createDirectChatChannel(String chatName, User user1, User user2,
                                                            String chatUrl, List<AbstractMessage> messages) {
        if (user1 == null || user2 == null) {
            throw new IllegalArgumentException("Users must not be null");
        }
        return new DirectChatChannel(chatName, user1, user2, chatUrl, messages);
    }

    /**
     * Returns an empty Direct Channel.
     * @return the Direct Chat Channel
     */
    public static DirectChatChannel createEmptyChatChannel() {
        return new DirectChatChannel("", new User(-1, "", "", ""),
                new User(-1, "", "", ""), "", new ArrayList<>());
    }
}
