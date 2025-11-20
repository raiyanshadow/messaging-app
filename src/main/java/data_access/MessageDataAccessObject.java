package data_access;

import entity.Message;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public interface MessageDataAccessObject{
    List<Message> getMessagesFromChannelURL(String channelURL) throws SQLException;
    public Message getMessageFromID(Long messageID) throws SQLException;
    public <T> Long addMessage(Message<T> message) throws SQLException;
    public void deleteMessage(Long messageId, String channelUrl) throws SQLException;
    public Timestamp editMessage(String newMessage, String channelUrl, Long messageId) throws SQLException;
}
