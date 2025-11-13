package data_access;

import entity.DirectChatChannel;

import java.sql.SQLException;
import java.util.List;

public interface ChatChannelAccessObject {
    DirectChatChannel getDirectChatChannelByURL(String channelURL) throws SQLException;
    DirectChatChannel getDirectChatChannelByID(int channelID) throws SQLException;
    int addChat(DirectChatChannel chat) throws SQLException;
    public List<String> getChatURLsByUserId(int userId) throws SQLException;
}
