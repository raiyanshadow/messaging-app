package use_case.reply_message;

import java.sql.SQLException;

import entity.AbstractMessage;

/**
 * The message data access interface containing methods used by the reply message use case's interactor.
 */
public interface ReplyMessageDataAccessInterface {
    /**
     * Inserts a new message record into the database.
     * @param <T> The type of content the message holds.
     * @param message The {@code AbstractMessage} entity to be added.
     * @return The auto-generated {@code Long} message ID.
     * @throws SQLException If a database access error occurs.
     */
    <T> Long addMessage(AbstractMessage<T> message) throws SQLException;
}
