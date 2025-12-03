package use_case.edit_message;

import java.sql.SQLException;
import java.sql.Timestamp;

import entity.AbstractMessage;

/**
 * Data access interface for edit message use case's interactor.
 */
public interface EditMessageDataAccessInterface {
    /**
     * Retrieves a single message based on its unique identifier.
     * @param messageID The unique ID of the message to retrieve.
     * @return The populated {@code AbstractMessage} entity, or an empty message if not found.
     * @throws SQLException If a database access error occurs.
     */
    AbstractMessage<String> getMessageFromID(Long messageID) throws SQLException;

    /**
     * Updates the content of an existing message.
     * @param newMessage The new text content for the message.
     * @param channelUrl The URL of the channel where the message resides.
     * @param messageId The ID of the message to edit.
     * @return The original {@code Timestamp} of the message before the edit,
     *         or {@code null} if the message was not found.
     * @throws SQLException If a database access error occurs.
     */
    Timestamp editMessage(String newMessage, String channelUrl, Long messageId) throws SQLException;
}
