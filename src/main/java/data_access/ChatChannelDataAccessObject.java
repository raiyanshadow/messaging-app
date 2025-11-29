package data_access;

import entity.DirectChatChannel;
import entity.Message;
import java.sql.SQLException;
import java.util.List;

public interface ChatChannelDataAccessObject {
    /**
     * Retrieves a {@code DirectChatChannel} entity based on its unique channel URL.
     * @param channelUrl The unique URL identifying the chat channel.
     * @return The populated {@code DirectChatChannel} entity.
     * @throws SQLException If a database access error occurs or the channel is not found.
     */
    DirectChatChannel getDirectChatChannelByURL(String channelUrl) throws SQLException;

    /**
     * Retrieves a {@code DirectChatChannel} entity based on its internal database ID.
     * @param channelID The internal integer ID of the chat channel.
     * @return The populated {@code DirectChatChannel} entity.
     * @throws SQLException If a database access error occurs or the channel is not found.
     */
    DirectChatChannel getDirectChatChannelByID(int channelID) throws SQLException;

    /**
     * Inserts a new chat channel record into the persistent storage.
     * @param chat The {@code DirectChatChannel} entity to be added.
     * @return The auto-generated integer ID of the new chat channel record.
     * @throws SQLException If a database access error occurs.
     */
    int addChat(DirectChatChannel chat) throws SQLException;

    /**
     * Retrieves a list of all chat channel URLs associated with a given user ID.
     *
     * @param userId The integer ID of the user whose chats are being queried.
     * @return A list of unique channel URLs (Strings).
     * @throws SQLException If a database access error occurs.
     */
    List<String> getChatURLsByUserId(int userId) throws SQLException;

    /**
     * Retrieves the most recently sent message for a specific chat channel.
     *
     * @param channelUrl The unique URL of the chat channel.
     * @return The latest {@code Message} entity, or {@code null} if the channel has no messages.
     * @throws SQLException If a database access error occurs.
     */
    Message getLastMessage(String channelUrl) throws SQLException;
}
