package use_case.send_message;

import java.sql.SQLException;

import entity.AbstractMessage;

/**
 * Message data access interface containing methods used by the send message use case's interactor.
 */
public interface SendMessageDataAccessInterface {
    /**
     * Inserts a new message record into the database.
     * @param <T>     The type of content the message holds.
     * @param message The {@code AbstractMessage} entity to be added.
     * @return the generated id.
     * @throws SQLException If a database access error occurs.
     */
    <T> Long addMessage(AbstractMessage<T> message) throws SQLException;
}
