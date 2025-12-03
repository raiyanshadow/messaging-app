package use_case.delete_message;

import java.sql.SQLException;

/**
 * The data access interface for the delete message use case's interactor.
 */
public interface DeleteMessageDataAccessInterface {
    /**
     * Permanently deletes a message record from the database.
     * @param messageId The ID of the message to delete.
     * @param channelUrl The URL of the channel where the message resides.
     * @throws SQLException If a database access error occurs.
     */
    void deleteMessage(Long messageId, String channelUrl) throws SQLException;
}
