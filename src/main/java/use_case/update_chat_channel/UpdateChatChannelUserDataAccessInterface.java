package use_case.update_chat_channel;

import java.sql.SQLException;

import entity.DirectChatChannel;

/**
 * User data access interface containing methods used by update chat channel use case's interactor.
 */
public interface UpdateChatChannelUserDataAccessInterface {
    /**
     * Retrieves a DirectChatChannel entity from the database based on its unique channel URL.
     * @param chatUrl The unique URL identifying the chat channel.
     * @return The populated DirectChatChannel entity, or an empty DirectChatChannel
     * @throws SQLException If a database access error occurs.
     */
    DirectChatChannel getDirectChatChannelByUrl(String chatUrl) throws SQLException;
}
