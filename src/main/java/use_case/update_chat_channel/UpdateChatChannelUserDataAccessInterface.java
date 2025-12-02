package use_case.update_chat_channel;

import java.sql.SQLException;

import entity.DirectChatChannel;

public interface UpdateChatChannelUserDataAccessInterface {
    /**
     * Retrieves information about a chat from the database based on a chat URL.
     * @param chatUrl the URL of the chat channel
     * @return a DirectChatChannel object representing the chat that was retrieved
     * @throws SQLException throw and exception if retrieval from the database failed
     */
    DirectChatChannel getDirectChatChannelByURL(String chatUrl) throws SQLException;
}
