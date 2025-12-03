package data.access;

import java.sql.SQLException;

import entity.AbstractMessage;
import use_case.send_message.SendMessageDataAccessInterface;

/**
 * An in memory data access object to represent the message table in our database.
 */
public class InMemoryMessageDao implements SendMessageDataAccessInterface {
    private AbstractMessage message;

    @Override
    public <T> Long addMessage(AbstractMessage<T> addMessage) throws SQLException {
        this.message = addMessage;
        return 0L;
    }
}
