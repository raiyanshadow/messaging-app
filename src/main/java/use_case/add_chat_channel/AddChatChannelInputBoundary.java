package use_case.add_chat_channel;

import java.sql.SQLException;

public interface AddChatChannelInputBoundary {
    void CreateChannel(CreateChatRequestModel request) throws SQLException;
}
