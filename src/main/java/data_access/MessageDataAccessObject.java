package data_access;

import entity.Message;

import java.sql.SQLException;
import java.util.List;

public interface MessageDataAccessObject{
    List<Message> getMessagesFromChannelURL(String channelURL) throws SQLException;
    public Message getMessageFromID(int messageID) throws SQLException;
    public <T> int addMessage(Message<T> message) throws SQLException;
}
