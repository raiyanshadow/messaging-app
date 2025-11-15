package use_case.update_chat_channel;

import entity.DirectChatChannel;

import java.sql.SQLException;

public interface UpdateChatChannelUserDataAccessInterface {
    DirectChatChannel getDirectChatChannelByURL(String chatURL) throws SQLException;
}
