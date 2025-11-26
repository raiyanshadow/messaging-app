package data_access;

import entity.Message;
import entity.MessageFactory;
import java.sql.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class DBMessageDataAccessObject implements MessageDataAccessObject {
    private final Connection connection;
    public final int ERROR = -404;
    private final DBUserDataAccessObject userDAO;

    public DBMessageDataAccessObject(Connection connection) {
        this.connection = connection;
        this.userDAO = new DBUserDataAccessObject(this.connection);
    }

    public List<Message> getMessagesFromChannelURL(String channelURL) throws SQLException {
        String query = "SELECT message_id, replying_to, channel_url, sender_id, receiver_id, " +
                "status, EXTRACT(EPOCH FROM time_sent) AS epoch_time, content " +
                "FROM text_message WHERE channel_url = ? ORDER BY time_sent ASC";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, channelURL);
            ResultSet resultSet = statement.executeQuery();
            List<Message> messages = new ArrayList<>();
            while (resultSet.next()) {
                long epoch = resultSet.getLong("epoch_time");
                Instant instant = Instant.ofEpochSecond(epoch);
                messages.add(MessageFactory.createTextMessage(
                        resultSet.getLong("message_id"),
                        resultSet.getLong("replying_to"),
                        resultSet.getString("channel_url"),
                        resultSet.getInt("sender_id"),
                        resultSet.getInt("receiver_id"),
                        resultSet.getString("status"),
                        Timestamp.from(instant), // pass this instead of timestamp
                        resultSet.getString("content")
                ));
            }
            return messages;
        }

    }

    public Message getMessageFromID(Long messageID) throws SQLException {
        String query = "SELECT * FROM text_message WHERE message_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, messageID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return MessageFactory.createTextMessage(
                        messageID,
                        resultSet.getLong("replying_to"),
                        resultSet.getString("channel_url"),
                        resultSet.getInt("sender_id"),
                        resultSet.getInt("receiver_id"),
                        resultSet.getString("status"),
                        resultSet.getTimestamp("time_sent"),
                        resultSet.getString("content")
                );
            } else {
                return MessageFactory.createEmptyMessage();
            }
        }
    }

    public <T> Long addMessage(Message<T> message) throws SQLException {
        if (message.getType().equals("text"))
        {
            String query = "INSERT INTO text_message (message_id, channel_url, replying_to, sender_id, receiver_id, time_sent, status, content) " +
                    "VALUES (?, ?, ?, ?, ?, ?, 'sent', ?) RETURNING message_id";

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setLong(1, message.getMessageID());
                statement.setString(2, message.getChannelURL());
                if (message.getParentMessageID() == null) {
                    statement.setNull(3, Types.NULL);
                } else {
                    statement.setLong(3, message.getParentMessageID());
                }
                statement.setInt(4, message.getSenderId());
                statement.setInt(5, message.getReceiverId());
                statement.setTimestamp(6, message.getTimestamp());
                statement.setString(7, (String) message.getContent());

                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    return resultSet.getLong("message_id");
                }
            }
            throw new SQLException("Could not add message");
        }
        return null;
    }

    public void deleteMessage(Long messageId, String channelUrl) throws SQLException {
        String query = "DELETE FROM text_message WHERE message_id = ? AND channel_url = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, messageId);
            statement.setString(2, channelUrl);

            statement.executeQuery();
        }
    }

    public Timestamp editMessage(String newMessage, String channelUrl, Long messageId) throws SQLException {
        String timestampQuery = "SELECT time_sent FROM text_message WHERE message_id = ? AND channel_url = ?";
        String updateMessageQuery = "UPDATE text_message SET content = ? AND time_sent = ? WHERE message_id = ? and channel_url = ?";
        Timestamp oldTimestamp = null;

        try (PreparedStatement statement =  connection.prepareStatement(timestampQuery)) {
            statement.setLong(1, messageId);
            statement.setString(2, channelUrl);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                oldTimestamp = resultSet.getTimestamp("time_sent");
            }
        }

        try (PreparedStatement statement = connection.prepareStatement(updateMessageQuery)) {
            statement.setString(1, newMessage);
            statement.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            statement.setLong(3, messageId);
            statement.setString(4, channelUrl);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return oldTimestamp;
            }
        }
        return null;
    }
}
