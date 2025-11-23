package data_access;

import entity.DirectChatChannel;
import entity.Message;
import use_case.update_chat_channel.UpdateChatChannelUserDataAccessInterface;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InMemoryChatDAO implements UpdateChatChannelUserDataAccessInterface {
    private final List<DirectChatChannel> chats = new ArrayList<DirectChatChannel>();

    @Override
    public DirectChatChannel getDirectChatChannelByURL(String chatURL) throws SQLException {
        for (DirectChatChannel chat : chats) {
            if (chat.getChatURL().equals(chatURL)) {
                return chat;
            }
        }
        return null;
    }

    public void addChat(DirectChatChannel chat) {
        chats.add(chat);
    }
}
