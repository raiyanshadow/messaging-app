package entity;

import java.util.ArrayList;
import java.util.List;

public class DirectChatChannelFactory {

    /**
     * Returns a Direct Chat Channel Entity based on needed inputs.
     * @return the Direct Chat Channel
     * @throws IllegalArgumentException if user is null
     */
    public static DirectChatChannel createDirectChatChannel(String chatName, User user1, User user2,
                                                            String chatURL, List<Message<String>> messages) {
        if (user1 == null || user2 == null) {
            throw new IllegalArgumentException("Users must not be null");
        }
        return new DirectChatChannel(chatName, user1, user2, chatURL, messages);
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
