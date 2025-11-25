package data_access;

import entity.DirectChatChannel;
import entity.DirectChatChannelFactory;
import entity.Message;
import entity.MessageFactory;
import use_case.update_chat_channel.UpdateChatChannelUserDataAccessInterface;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBChatChannelDataAccessObject implements UpdateChatChannelUserDataAccessInterface, ChatChannelDataAccessObject {
    private final Connection connection;
    private final int ERROR_CODE = -404;
    private final DBUserDataAccessObject userDAO;
    private final DBMessageDataAccessObject messageDAO;

    public DBChatChannelDataAccessObject(Connection connection) {
        this.connection = connection;
        this.userDAO = new DBUserDataAccessObject(this.connection);
        this.messageDAO = new DBMessageDataAccessObject(this.connection);
    }

    public DirectChatChannel getDirectChatChannelByURL(String channelURL) throws SQLException{
        String query = "SELECT * FROM chat_channel WHERE channel_url = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, channelURL);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return DirectChatChannelFactory.createDirectChatChannel(
                        resultSet.getString("name"),
                        userDAO.getUserFromID(resultSet.getInt("user1_id")),
                        userDAO.getUserFromID(resultSet.getInt("user2_id")),
                        resultSet.getString("channel_url"),
                        messageDAO.getMessagesFromChannelURL(channelURL)
                );
            }
            else {
                return DirectChatChannelFactory.createEmptyChatChannel();
            }
        }
    }
    public DirectChatChannel getDirectChatChannelByID(int channelID) throws SQLException{
        String query = "SELECT * FROM chat_channel WHERE chat_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, channelID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String channelURL = resultSet.getString("channel_url");
                DirectChatChannel chatChannel = DirectChatChannelFactory.createDirectChatChannel(
                        resultSet.getString("name"),
                        userDAO.getUserFromID(resultSet.getInt("user1_id")),
                        userDAO.getUserFromID(resultSet.getInt("user2_id")),
                        resultSet.getString("channel_url"),
                        messageDAO.getMessagesFromChannelURL(channelURL));
                chatChannel.setChatURL(channelURL);
                return chatChannel;
            }
            else {
                return DirectChatChannelFactory.createEmptyChatChannel();
            }
        }
    }
    public int addChat(DirectChatChannel chat) throws SQLException {
        String query = "INSERT INTO chat_channel (user1_id, user2_id, channel_url, name) " +
                "VALUES (?, ?, ?, ?) RETURNING chat_id";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, chat.getUser1().getUserID()); // user 1
            preparedStatement.setInt(2, chat.getUser2().getUserID()); // user 2
            preparedStatement.setString(3, chat.getChatURL());
            preparedStatement.setString(4, chat.getChatName());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("chat_id");
            }
        }
        throw new SQLException("Could not add chat");
    }

    public Message getLastMessage(String channelUrl) throws SQLException {
        String query = "SELECT * FROM text_message WHERE channel_url = ? ORDER BY time_sent DESC LIMIT 1";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, channelUrl);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return MessageFactory.createTextMessage (
                        rs.getLong("message_id"),
                        rs.getLong("replying_to"),
                        rs.getString("channel_url"),
                        userDAO.getUserFromID(rs.getInt("sender_id")),
                        userDAO.getUserFromID(rs.getInt("receiver_id")),
                        rs.getString("status"),
                        rs.getTimestamp("time_sent"),
                        rs.getString("content")
                );
            } else {
                return null; // no messages found
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    public List<String> getChatURLsByUserId(int userId) throws SQLException {
        String query = "SELECT channel_url FROM chat_channel WHERE user1_id = ? OR user2_id = ?";


        List<String> chatUrls = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, userId);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                chatUrls.add(resultSet.getString("channel_url"));
            }
        }

        return chatUrls;
    }
}
