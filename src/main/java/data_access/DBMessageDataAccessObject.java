package data_access;

import entity.Message;
import entity.MessageFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBMessageDataAccessObject implements MessageDataAccessObject {
    private final Connection connection;
    private final int error = -404;
    private final DBUserDataAccessObject userDao;

    public DBMessageDataAccessObject(Connection connection) {
        this.connection = connection;
        this.userDao = new DBUserDataAccessObject(this.connection);
    }

    /**
     * Retrieves all messages belonging to a specific chat channel, ordered chronologically.
     * @param channelUrl The unique URL of the channel to query messages from.
     * @return A {@code List} of {@code Message} entities, ordered by time sent (ascending).
     * @throws SQLException If a database access error occurs.
     */
    public List<Message> getMessagesFromChannelURL(String channelUrl) throws SQLException {
        final String query = "SELECT * FROM text_message WHERE channel_url = ? ORDER BY time_sent ASC";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, channelUrl);
            final ResultSet resultSet = statement.executeQuery();
            final List<Message> messages = new ArrayList<>();
            while (resultSet.next()) {
                messages.add(MessageFactory.createTextMessage(
                        resultSet.getLong("message_id"),
                        resultSet.getLong("replying_to"),
                        resultSet.getString("channel_url"),
                        resultSet.getInt("sender_id"),
                        resultSet.getInt("receiver_id"),
                        resultSet.getString("status"),
                        resultSet.getTimestamp("time_sent"),
                        resultSet.getString("content")
                ));
            }
            return messages;
        }

    }

    /**
     * Retrieves a single {@code Message} entity based on its unique message ID.
     * @param messageID The unique ID of the message to retrieve.
     * @return The populated {@code Message} entity, or an empty message entity if the ID is not found.
     * @throws SQLException If a database access error occurs.
     */
    public Message getMessageFromID(Long messageID) throws SQLException {
        final String query = "SELECT * FROM text_message WHERE message_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, messageID);
            final ResultSet resultSet = statement.executeQuery();
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
            }
            else {
                return MessageFactory.createEmptyMessage();
            }
        }
    }

    /**
     * Inserts a new message record into the database. This implementation currently supports
     * messages of type "text".
     * @param <T> The type of content the message holds (e.g., String for text).
     * @param message The {@code Message} entity to be added.
     * @return The auto-generated {@code Long} message ID if successful, or {@code null} if the
     * @throws SQLException If a database access error occurs or the message could not be added.
     */
    public <T> Long addMessage(Message<T> message) throws SQLException {
        if (message.getType().equals("text")) {
            final String query = "INSERT INTO text_message "
                    + "(message_id, channel_url, replying_to, sender_id, receiver_id, time_sent, status, content) "
                    + "VALUES (?, ?, ?, ?, ?, ?, 'sent', ?) RETURNING message_id";

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setLong(1, message.getMessageID());
                statement.setString(2, message.getChannelUrl());
                if (message.getParentMessageID() == null) {
                    statement.setNull(3, Types.NULL);
                }
                else {
                    statement.setLong(3, message.getParentMessageID());
                }

                statement.setInt(4, message.getSenderId());
                statement.setInt(5, message.getReceiverId());
                statement.setTimestamp(6, message.getTimestamp());
                statement.setString(7, (String) message.getContent());

                final ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    return resultSet.getLong("message_id");
                }
            }
            throw new SQLException("Could not add message");
        }
        return null;
    }

    /**
     * Permanently deletes a message record from the database.
     * @param messageId The ID of the message to delete.
     * @param channelUrl The URL of the channel where the message resides.
     * @throws SQLException If a database access error occurs.
     */
    public void deleteMessage(Long messageId, String channelUrl) throws SQLException {
        final String query = "DELETE FROM text_message WHERE message_id = ? AND channel_url = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, messageId);
            statement.setString(2, channelUrl);

            statement.executeQuery();
        }
    }

    /**
     * Updates the content of an existing message in the database.
     * @param newMessage The new text content for the message.
     * @param channelUrl The URL of the channel where the message resides.
     * @param messageId The ID of the message to edit.
     * @return The original {@code Timestamp} of the message before the edit, or {@code null} if the message was not found.
     * @throws SQLException If a database access error occurs.
     */
    public Timestamp editMessage(String newMessage, String channelUrl, Long messageId) throws SQLException {
        final String timestampQuery = "SELECT time_sent FROM text_message WHERE message_id = ? AND channel_url = ?";
        final String updateMessageQuery = "UPDATE text_message SET content = ? AND time_sent = ? "
               + "WHERE message_id = ? and channel_url = ?";
        Timestamp oldTimestamp = null;

        try (PreparedStatement statement = connection.prepareStatement(timestampQuery)) {
            statement.setLong(1, messageId);
            statement.setString(2, channelUrl);

            final ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                oldTimestamp = resultSet.getTimestamp("time_sent");
            }
        }

        try (PreparedStatement statement = connection.prepareStatement(updateMessageQuery)) {
            statement.setString(1, newMessage);
            statement.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            statement.setLong(3, messageId);
            statement.setString(4, channelUrl);

            final ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return oldTimestamp;
            }
        }
        return null;
    }
}
