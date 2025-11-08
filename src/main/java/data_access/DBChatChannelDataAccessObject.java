package data_access;

import entity.DirectChatChannel;
import entity.DirectChatChannelFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBChatChannelDataAccessObject {
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
        String query = "SELECT * FROM direct_chat_channel WHERE channel_url = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, channelURL);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return DirectChatChannelFactory.createDirectChatChannel(
                        resultSet.getInt("chat_id"),
                        userDAO.getUserFromUserId(resultSet.getInt("user1_id")),
                        userDAO.getUserFromUserId(resultSet.getInt("user2_id")),
                        messageDAO.getMessagesFromChannelURL(channelURL)
                );
            }
            else {
                return DirectChatChannelFactory.createEmptyChatChannel();
            }
        }
    }
    public DirectChatChannel getDirectChatChannelByID(int channelID) throws SQLException{
        String query = "SELECT * FROM direct_chat_channel WHERE channel_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, channelID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String channelURL = resultSet.getString("channel_url");
                return DirectChatChannelFactory.createDirectChatChannel(
                        channelID,
                        resultSet.getInt("chat_id"),
                        userDAO.getUserFromUserId(resultSet.getInt("user1_id")),
                        userDAO.getUserFromUserId(resultSet.getInt("user2_id")),
                        messageDAO.getMessagesFromChannelURL(channelURL)
                );
            }
            else {
                return DirectChatChannelFactory.createEmptyChatChannel();
            }
        }
    }
    public int addChat(DirectChatChannel chat) throws SQLException {
        String query = "INSERT INTO direct_chat_channel (user1_id, user2_id, channel_url) " +
                "VALUES (?, ?, ?) RETURNING chat_id";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, chat.getChannelURL());
            preparedStatement.setInt(2, chat.getUsers().get(0)); // user 1
            preparedStatement.getInt(3, chat.getUsers.get(1)); // user 2
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("chat_id");
            }
        }
        throw new SQLException("Could not add chat");
    }
}
