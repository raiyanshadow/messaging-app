package use_case.baseUI;

import java.sql.SQLException;
import java.util.List;

import entity.DirectChatChannel;

/**
 * The chat channel data access interface containing methods needed by the base UI use case's interactor.
 */
public interface BaseUiChatChannelDataAccessInterface {
    /**
     * Retrieves a list of all chat channel URLs associated with a given user ID.
     * @param userId The integer ID of the user whose chats are being queried.
     * @return A {@code List<String>} containing the channel URLs for all chats
     * @throws SQLException If a database access error occurs.
     */
    List<String> getChatUrlsByUserId(int userId) throws SQLException;

    /**
     * Retrieves a DirectChatChannel entity from the database based on its unique channel URL.
     * @param channelUrl The unique URL identifying the chat channel.
     * @return The populated DirectChatChannel entity, or an empty DirectChatChannel
     * @throws SQLException If a database access error occurs.
     */
    DirectChatChannel getDirectChatChannelByUrl(String channelUrl) throws SQLException;
}
