package use_case.add_chat_channel;

import entity.DirectChatChannel;

import java.sql.SQLException;
import java.util.List;

/**
 * Interface for methods needed by the add chat channel use case's interactor in order to access/modify the database.
 */
public interface AddChatChannelDataAccessInterface {
    /**
     * Retrieves a list of all chat channel URLs associated with a given user ID.
     * @param userId The integer ID of the user whose chats are being queried.
     * @return A {@code List<String>} containing the channel URLs for all chats
     * @throws SQLException If a database access error occurs.
     */
    List<String> getChatUrlsByUserId(int userId) throws SQLException;

    /**
     * Inserts a new DirectChatChannel record into the database.
     * @param chat The DirectChatChannel entity containing user IDs, URL, and name.
     * @return The auto-generated integer ID (chat_id) of the newly added chat channel.
     * @throws SQLException If a database access error occurs or if the chat could not be added.
     */
    int addChat(DirectChatChannel chat) throws SQLException;
}
