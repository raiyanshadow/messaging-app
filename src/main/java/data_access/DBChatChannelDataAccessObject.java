package data_access;

import entity.DirectChatChannel;
import entity.DirectChatChannelFactory;
import entity.Message;
import entity.MessageFactory;
import use_case.update_chat_channel.UpdateChatChannelUserDataAccessInterface;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBChatChannelDataAccessObject implements UpdateChatChannelUserDataAccessInterface,
        ChatChannelDataAccessObject {
    private final Connection connection;
    private final int errorCode = -404;
    private final DBUserDataAccessObject userDao;
    private final DBMessageDataAccessObject messageDao;

    public DBChatChannelDataAccessObject(Connection connection) {
        this.connection = connection;
        this.userDao = new DBUserDataAccessObject(this.connection);
        this.messageDao = new DBMessageDataAccessObject(this.connection);
    }

    /**
     * Retrieves a DirectChatChannel entity from the database based on its unique channel URL.
     * @param channelUrl The unique URL identifying the chat channel.
     * @return The populated DirectChatChannel entity, or an empty DirectChatChannel
     * @throws SQLException If a database access error occurs.
     */
    public DirectChatChannel getDirectChatChannelByURL(String channelUrl) throws SQLException {
        final String query = "SELECT * FROM chat_channel WHERE channel_url = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, channelUrl);
            final ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return DirectChatChannelFactory.createDirectChatChannel(
                        resultSet.getString("name"),
                        userDao.getUserFromID(resultSet.getInt("user1_id")),
                        userDao.getUserFromID(resultSet.getInt("user2_id")),
                        resultSet.getString("channel_url"),
                        messageDao.getMessagesFromChannelURL(channelUrl)
                );
            }
            else {
                return DirectChatChannelFactory.createEmptyChatChannel(); // May change to null
            }
        }
    }

    /**
     * Retrieves a DirectChatChannel entity from the database based on its internal chat ID.
     * @param channelID The internal integer ID of the chat channel.
     * @return The populated DirectChatChannel entity, or an empty DirectChatChannel
     * @throws SQLException If a database access error occurs.
     */
    public DirectChatChannel getDirectChatChannelByID(int channelID) throws SQLException {
        final String query = "SELECT * FROM chat_channel WHERE chat_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, channelID);
            final ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                final String channelUrl = resultSet.getString("channel_url");
                final DirectChatChannel chatChannel = DirectChatChannelFactory.createDirectChatChannel(
                        resultSet.getString("name"),
                        userDao.getUserFromID(resultSet.getInt("user1_id")),
                        userDao.getUserFromID(resultSet.getInt("user2_id")),
                        resultSet.getString("channel_url"),
                        messageDao.getMessagesFromChannelURL(channelUrl));
                chatChannel.setChatUrl(channelUrl);
                return chatChannel;
            }
            else {
                return DirectChatChannelFactory.createEmptyChatChannel();
            }
        }
    }

    /**
     * Inserts a new DirectChatChannel record into the database.
     * @param chat The DirectChatChannel entity containing user IDs, URL, and name.
     * @return The auto-generated integer ID (chat_id) of the newly added chat channel.
     * @throws SQLException If a database access error occurs or if the chat could not be added.
     */
    public int addChat(DirectChatChannel chat) throws SQLException {
        final String query = "INSERT INTO chat_channel (user1_id, user2_id, channel_url, name) "
                + "VALUES (?, ?, ?, ?) RETURNING chat_id";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            // User1
            preparedStatement.setInt(1, chat.getUser1().getUserID());
            // User2
            preparedStatement.setInt(2, chat.getUser2().getUserID());
            preparedStatement.setString(3, chat.getChatUrl());
            preparedStatement.setString(4, chat.getChatName());
            final ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("chat_id");
            }
        }
        throw new SQLException("Could not add chat");
    }

    /**
     * Retrieves the most recently sent message for a specific chat channel.
     * @param channelUrl The unique URL of the chat channel.
     * @return The Message entity that was sent last, or {@code null} if the channel has no messages.
     * @throws SQLException If a database access error occurs.
     */
    public Message getLastMessage(String channelUrl) throws SQLException {
        final String query = "SELECT * FROM text_message WHERE channel_url = ? ORDER BY time_sent DESC LIMIT 1";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, channelUrl);
            final ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return MessageFactory.createTextMessage(
                        rs.getLong("message_id"),
                        rs.getLong("replying_to"),
                        rs.getString("channel_url"),
                        rs.getInt("sender_id"),
                        rs.getInt("receiver_id"),
                        rs.getString("status"),
                        rs.getTimestamp("time_sent"),
                        rs.getString("content")
                );
            }
            else {
                // No message Found
                return null;
            }
        }
        catch (SQLException except) {
            System.out.println(except.getMessage());
            throw except;
        }
    }

    /**
     * Retrieves a list of all chat channel URLs associated with a given user ID.
     * @param userId The integer ID of the user whose chats are being queried.
     * @return A {@code List<String>} containing the channel URLs for all chats
     * @throws SQLException If a database access error occurs.
     */
    public List<String> getChatURLsByUserId(int userId) throws SQLException {
        final String query = "SELECT channel_url FROM chat_channel "
               + "WHERE user1_id = ? OR user2_id = ?";

        final List<String> chatUrls = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, userId);

            final ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                chatUrls.add(resultSet.getString("channel_url"));
            }
        }

        return chatUrls;
    }
}
