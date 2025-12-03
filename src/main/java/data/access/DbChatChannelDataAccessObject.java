package data.access;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.AbstractMessage;
import entity.DirectChatChannel;
import entity.DirectChatChannelFactory;
import entity.MessageFactory;
import use_case.add_chat_channel.AddChatChannelDataAccessInterface;
import use_case.baseUI.BaseUiChatChannelDataAccessInterface;
import use_case.login.LoginChatChannelDataAccessInterface;
import use_case.update_chat_channel.UpdateChatChannelUserDataAccessInterface;

/**
 * A data access object containing methods to extract information from chat_channel table in the SQL database.
 */
public class DbChatChannelDataAccessObject implements UpdateChatChannelUserDataAccessInterface,
        ChatChannelDataAccessObject, AddChatChannelDataAccessInterface, BaseUiChatChannelDataAccessInterface,
        LoginChatChannelDataAccessInterface {
    private final Connection connection;
    private final DbUserDataAccessObject userDao;
    private final DbMessageDataAccessObject messageDao;
    private final String channelUrlString;

    public DbChatChannelDataAccessObject(Connection connection) {
        this.connection = connection;
        this.userDao = new DbUserDataAccessObject(this.connection);
        this.messageDao = new DbMessageDataAccessObject(this.connection);
        channelUrlString = "channel_url";
    }

    @Override
    public DirectChatChannel getDirectChatChannelByUrl(String channelUrl) throws SQLException {
        final String query = "SELECT * FROM chat_channel WHERE channel_url = ?";
        DirectChatChannel chatChannel = DirectChatChannelFactory.createEmptyChatChannel();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, channelUrl);
            final ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                chatChannel = DirectChatChannelFactory.createDirectChatChannel(
                        resultSet.getString("name"),
                        userDao.getUserFromID(resultSet.getInt("user1_id")),
                        userDao.getUserFromID(resultSet.getInt("user2_id")),
                        resultSet.getString(this.channelUrlString),
                        messageDao.getMessagesFromChannelUrl(channelUrl)
                );
            }
        }
        return chatChannel;
    }

    /**
     * Retrieves a DirectChatChannel entity from the database based on its internal chat ID.
     * @param channelID The internal integer ID of the chat channel.
     * @return The populated DirectChatChannel entity, or an empty DirectChatChannel
     * @throws SQLException If a database access error occurs.
     */
    public DirectChatChannel getDirectChatChannelByID(int channelID) throws SQLException {
        final String query = "SELECT * FROM chat_channel WHERE chat_id = ?";
        DirectChatChannel chatChannel = DirectChatChannelFactory.createEmptyChatChannel();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, channelID);
            final ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                final String channelUrl = resultSet.getString(this.channelUrlString);
                chatChannel = DirectChatChannelFactory.createDirectChatChannel(
                        resultSet.getString("name"),
                        userDao.getUserFromID(resultSet.getInt("user1_id")),
                        userDao.getUserFromID(resultSet.getInt("user2_id")),
                        resultSet.getString(this.channelUrlString),
                        messageDao.getMessagesFromChannelUrl(channelUrl));
                chatChannel.setChatUrl(channelUrl);
            }
        }
        return chatChannel;
    }

    @Override
    public int addChat(DirectChatChannel chat) throws SQLException {
        final String query = "INSERT INTO chat_channel (user1_id, user2_id, channel_url, name) "
                + "VALUES (?, ?, ?, ?) RETURNING chat_id";
        final int chatUrlPosition = 3;
        final int chatNamePosition = 4;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            // User1
            preparedStatement.setInt(1, chat.getUser1().getUserID());
            // User2
            preparedStatement.setInt(2, chat.getUser2().getUserID());
            preparedStatement.setString(chatUrlPosition, chat.getChatUrl());
            preparedStatement.setString(chatNamePosition, chat.getChatName());
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
     * @return The AbstractMessage entity that was sent last, or {@code null} if the channel has no messages.
     * @throws SQLException If a database access error occurs.
     */
    public AbstractMessage getLastMessage(String channelUrl) throws SQLException {
        final String query = "SELECT * FROM text_message WHERE channel_url = ? ORDER BY time_sent DESC LIMIT 1";
        AbstractMessage message = MessageFactory.createEmptyMessage();
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, channelUrl);
            final ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                message = MessageFactory.createTextMessage(
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
        }
        catch (SQLException except) {
            System.out.println(except.getMessage());
            throw except;
        }
        return message;
    }

    @Override
    public List<String> getChatUrlsByUserId(int userId) throws SQLException {
        final String query = "SELECT channel_url FROM chat_channel "
               + "WHERE user1_id = ? OR user2_id = ?";

        final List<String> chatUrls = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, userId);

            final ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                chatUrls.add(resultSet.getString(this.channelUrlString));
            }
        }

        return chatUrls;
    }
}
