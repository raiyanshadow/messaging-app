package data_access;

import entity.DirectChatChannel;
import entity.DirectChatChannelFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBChatChannelDataAccessObject {
    private final Connection connection;
    private final int ERROR_CODE = -404;
    public DBChatChannelDataAccessObject(Connection connection) {
        this.connection = connection;
    }

    public DirectChatChannel getDirectChatChannelByURL(String channelURL) throws SQLException{
        String query = "SELECT * FROM direct_chat_channel WHERE channel_url = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, channelURL);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                DirectChatChannelFactory.createDirectChatChannel(
                        resultSet.getInt("chatID"),
                        resultSet.get
                )
            }
        }
    }
}
