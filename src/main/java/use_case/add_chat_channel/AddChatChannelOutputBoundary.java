package use_case.add_chat_channel;

import java.sql.SQLException;

public interface AddChatChannelOutputBoundary {
    void PresentChat(AddChatChannelOutputData createChatResponeModel) throws SQLException;
}
