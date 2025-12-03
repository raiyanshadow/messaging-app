package data.access;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import entity.AbstractMessage;
import entity.MessageFactory;
import use_case.send_message.SendMessageDataAccessInterface;

/**
 * The data access object containing methods to extract data from the message table in our database.
 */
public class DbMessageDataAccessObject implements MessageDataAccessObject,
        SendMessageDataAccessInterface {
    private final Connection connection;
    private String timeSentString;

    public DbMessageDataAccessObject(Connection connection) {

        this.connection = connection;
        this.timeSentString = "time_sent";
    }

    /**
     * Retrieves all messages belonging to a specific chat channel, ordered chronologically.
     * @param channelUrl The unique URL of the channel to query messages from.
     * @return A {@code List} of {@code AbstractMessage} entities, ordered by time sent (ascending).
     * @throws SQLException If a database access error occurs.
     */
    public List<AbstractMessage> getMessagesFromChannelUrl(String channelUrl) throws SQLException {
        final String query = "SELECT * FROM text_message WHERE channel_url = ? ORDER BY time_sent ASC";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, channelUrl);
            final ResultSet resultSet = statement.executeQuery();
            final List<AbstractMessage> messages = new ArrayList<>();
            while (resultSet.next()) {
                messages.add(MessageFactory.createTextMessage(
                        resultSet.getLong("message_id"),
                        resultSet.getLong("replying_to"),
                        resultSet.getString("channel_url"),
                        resultSet.getInt("sender_id"),
                        resultSet.getInt("receiver_id"),
                        resultSet.getString("status"),
                        resultSet.getTimestamp(timeSentString),
                        resultSet.getString("content")
                ));
            }
            return messages;
        }

    }

    /**
     * Retrieves a single {@code AbstractMessage} entity based on its unique message ID.
     * @param messageID The unique ID of the message to retrieve.
     * @return The populated {@code AbstractMessage} entity, or an empty message entity if the ID is not found.
     * @throws SQLException If a database access error occurs.
     */
    public AbstractMessage<String> getMessageFromID(Long messageID) throws SQLException {
        final String query = "SELECT * FROM text_message WHERE message_id = ?";
        AbstractMessage<String> message = MessageFactory.createEmptyMessage();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, messageID);
            final ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                message = MessageFactory.createTextMessage(
                        messageID,
                        resultSet.getLong("replying_to"),
                        resultSet.getString("channel_url"),
                        resultSet.getInt("sender_id"),
                        resultSet.getInt("receiver_id"),
                        resultSet.getString("status"),
                        resultSet.getTimestamp(timeSentString),
                        resultSet.getString("content")
                );
            }
            else {
                message = MessageFactory.createEmptyMessage();
            }
        }
        return message;
    }

    /**
     * Inserts a new message record into the database. This implementation currently supports
     * messages of type "text".
     *
     * @param <T>     The type of content the message holds (e.g., String for text).
     * @param message The {@code AbstractMessage} entity to be added.
     * @return the generated id from adding the message.
     * @throws SQLException If a database access error occurs or the message could not be added.
     */
    public <T> Long addMessage(AbstractMessage<T> message) throws SQLException {
        Long returnId = null;
        if (message.getType().equals("text")) {
            final String query = "INSERT INTO text_message "
                    + "(message_id, channel_url, replying_to, sender_id, receiver_id, time_sent, status, content) "
                    + "VALUES (?, ?, ?, ?, ?, ?, 'sent', ?) RETURNING message_id";

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setLong(1, message.getMessageID());
                statement.setString(2, message.getChannelUrl());
                final int parentMessageIdPosition = 3;
                if (message.getParentMessageID() == null) {
                    statement.setNull(parentMessageIdPosition, Types.NULL);
                }
                else {
                    statement.setLong(parentMessageIdPosition, message.getParentMessageID());
                }

                final int senderIdPosition = 4;
                final int receiverIdPosition = 5;
                final int timestampPosition = 6;
                final int contentPosition = 7;
                statement.setInt(senderIdPosition, message.getSenderId());
                statement.setInt(receiverIdPosition, message.getReceiverId());
                statement.setTimestamp(timestampPosition, message.getTimestamp());
                statement.setString(contentPosition, (String) message.getContent());

                final ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    returnId = resultSet.getLong("message_id");
                }
            }
            throw new SQLException("Could not add message");
        }
        return returnId;
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
     * @return The original {@code Timestamp} of the message before the edit, or {@code null} if the message was not
     *         found.
     * @throws SQLException If a database access error occurs.
     */
    public Timestamp editMessage(String newMessage, String channelUrl, Long messageId) throws SQLException {
        final String timestampQuery = "SELECT time_sent FROM text_message WHERE message_id = ? AND channel_url = ?";
        final String updateMessageQuery = "UPDATE text_message SET content = ? AND time_sent = ? "
               + "WHERE message_id = ? and channel_url = ?";
        Timestamp oldTimestamp = null;
        Timestamp returnTimestamp = null;

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

            final int messageIdPosition = 3;
            final int channelUrlPosition = 4;
            statement.setLong(messageIdPosition, messageId);
            statement.setString(channelUrlPosition, channelUrl);

            final ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                returnTimestamp = oldTimestamp;
            }
        }
        return returnTimestamp;
    }
}
