package data_access;

import entity.Message;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public interface MessageDataAccessObject {

    /**
     * Retrieves all messages belonging to a specific chat channel, typically ordered by time sent.
     * @param channelUrl The unique URL of the channel to query messages from.
     * @return A list of {@code Message} entities.
     * @throws SQLException If a database access error occurs.
     */
    List<Message> getMessagesFromChannelURL(String channelUrl) throws SQLException;

    /**
     * Retrieves a single message based on its unique identifier.
     * @param messageID The unique ID of the message to retrieve.
     * @return The populated {@code Message} entity, or an empty message if not found.
     * @throws SQLException If a database access error occurs.
     */
    Message getMessageFromID(Long messageID) throws SQLException;

    /**
     * Inserts a new message record into the database.
     * @param <T> The type of content the message holds.
     * @param message The {@code Message} entity to be added.
     * @return The auto-generated {@code Long} message ID.
     * @throws SQLException If a database access error occurs.
     */
    <T> Long addMessage(Message<T> message) throws SQLException;

    /**
     * Permanently deletes a message record from the database.
     * @param messageId The ID of the message to delete.
     * @param channelUrl The URL of the channel where the message resides.
     * @throws SQLException If a database access error occurs.
     */
    void deleteMessage(Long messageId, String channelUrl) throws SQLException;

    /**
     * Updates the content of an existing message.
     * @param newMessage The new text content for the message.
     * @param channelUrl The URL of the channel where the message resides.
     * @param messageId The ID of the message to edit.
     * @return The original {@code Timestamp} of the message before the edit, or {@code null} if the message was not found.
     * @throws SQLException If a database access error occurs.
     */
    Timestamp editMessage(String newMessage, String channelUrl, Long messageId) throws SQLException;
}
