package use_case.send_message;

import entity.Message;

import java.sql.SQLException;

public interface SendMessageDataAccessInterface {
    /**
     * Inserts a new message record into the database.
     *
     * @param <T>     The type of content the message holds.
     * @param message The {@code Message} entity to be added.
     * @throws SQLException If a database access error occurs.
     */
    <T> Long addMessage(Message<T> message) throws SQLException;
}
