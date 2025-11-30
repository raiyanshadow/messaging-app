package data_access;

import entity.DirectChatChannel;
import use_case.update_chat_channel.UpdateChatChannelUserDataAccessInterface;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InMemoryChatDAO implements UpdateChatChannelUserDataAccessInterface {
    private final List<DirectChatChannel> chats = new ArrayList<>();

    @Override
    public DirectChatChannel getDirectChatChannelByURL(String chatUrl) throws SQLException {
        for (DirectChatChannel chat : chats) {
            if (chat.getChatUrl().equals(chatUrl)) {
                return chat;
            }
        }
        return null;
    }

    /**
     * Add the chat channel the chats List.
     * @param chat the direct chat channel entity to add to chats
     */
    public void addChat(DirectChatChannel chat) {
        chats.add(chat);
    }
}
