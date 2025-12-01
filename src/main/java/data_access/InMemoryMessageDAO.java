package data_access;

import entity.Message;
import use_case.send_message.SendMessageDataAccessInterface;

import java.sql.SQLException;

public class InMemoryMessageDAO implements SendMessageDataAccessInterface {
    Message message;

    @Override
    public <T> Long addMessage(Message<T> message) throws SQLException {
        this.message = message;
        return 0L;
    }
}
