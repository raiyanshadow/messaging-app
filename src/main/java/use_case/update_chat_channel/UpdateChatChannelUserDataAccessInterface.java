package use_case.update_chat_channel;

import entity.DirectChatChannel;

import java.sql.SQLException;

public interface UpdateChatChannelUserDataAccessInterface {
    DirectChatChannel getDirectChatChannelByID(int chatID) throws SQLException;
}
