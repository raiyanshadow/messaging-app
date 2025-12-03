package data.access;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.DirectChatChannel;
import use_case.baseUI.BaseUiChatChannelDataAccessInterface;
import use_case.update_chat_channel.UpdateChatChannelUserDataAccessInterface;

/**
 * An in memory data access object to represent the chat table in our database.
 */
public class InMemoryChatDao implements UpdateChatChannelUserDataAccessInterface,
        BaseUiChatChannelDataAccessInterface {
    private final List<DirectChatChannel> chats = new ArrayList<>();

    @Override
    public List<String> getChatUrlsByUserId(int userId) throws SQLException {
        final List<String> urls = new ArrayList<>();
        for (DirectChatChannel dc : chats) {
            if (dc.getUser1().getUserID() == userId || dc.getUser2().getUserID() == userId) {
                urls.add(dc.getChatUrl());
            }
        }
        return urls;
    }

    @Override
    public DirectChatChannel getDirectChatChannelByUrl(String chatUrl) throws SQLException {
        DirectChatChannel dc = null;
        for (DirectChatChannel chat : chats) {
            if (chat.getChatUrl().equals(chatUrl)) {
                dc = chat;
                break;
            }
        }
        return dc;
    }

    /**
     * Add the chat channel the chats List.
     * @param chat the direct chat channel entity to add to chats
     */
    public void addChat(DirectChatChannel chat) {
        chats.add(chat);
    }
}
