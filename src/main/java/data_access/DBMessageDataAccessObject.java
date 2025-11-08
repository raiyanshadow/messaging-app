package data_access;

import entity.Message;
import entity.MessageFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBMessageDataAccessObject {
    private final Connection connection;
    public final int ERROR = -404;
    private final DBUserDataAccessObject userDAO;

    public DBMessageDataAccessObject(Connection connection) {
        this.connection = connection;
        this.userDAO = new DBUserDataAccessObject(this.connection);
    }

    public List<Message> getMessagesFromChannelURL(String channelURL) throws SQLException {
        String query = "SELECT * FROM text_message WHERE channel_url = ? ORDER BY time_sent ASC";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, channelURL);
            ResultSet resultSet = statement.executeQuery();
            List<Message> messages = new ArrayList<>();
            while (resultSet.next()) {
                messages.add(MessageFactory.createTextMessage(
                        resultSet.getInt("message_id"),
                        resultSet.getString("channel_url"),
                        userDAO.getUserFromID(resultSet.getInt("sender_id")),
                        userDAO.getUserFromID(resultSet.getInt("receiver_id")),
                        resultSet.getString("status"),
                        resultSet.getTimestamp("time_sent"),
                        resultSet.getString("content")
                ));
            }
            return messages;
        }

    }

    public Message getMessageFromID(int messageID) throws SQLException {
        String query = "SELECT * FROM text_message WHERE message_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, messageID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return MessageFactory.createTextMessage(
                        messageID,
                        resultSet.getString("channel_url"),
                        userDAO.getUserFromID(resultSet.getInt("sender_id")),
                        userDAO.getUserFromID(resultSet.getInt("receiver_id")),
                        resultSet.getString("status"),
                        resultSet.getTimestamp("time_sent"),
                        resultSet.getString("content")
                );
            } else {
                return MessageFactory.createEmptyMessage();
            }
        }
    }

    public <T> int addMessage(Message<T> message) throws SQLException {
        if (message.getType().equals("text"))
        {
            String query = "INSERT INTO text_message (channel_url, sender_id, receiver_id, time_sent, status, content) " +
                    "VALUES (?, ?, ?, ?, 'sent', ?) RETURNING message_id";

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, message.getChannelURL());
                statement.setInt(2, message.getSender().getUserID());
                statement.setInt(3, message.getReceiver().getUserID());
                statement.setTimestamp(4, message.getTimestamp());
                statement.setString(5, (String) message.getContent());

                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    return resultSet.getInt("message_id");
                }
            }
            throw new SQLException("Could not add message");
        }
        return ERROR;
    }
}
