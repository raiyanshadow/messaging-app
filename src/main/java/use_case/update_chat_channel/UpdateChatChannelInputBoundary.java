package use_case.update_chat_channel;

import java.sql.SQLException;

public interface UpdateChatChannelInputBoundary {
    void execute(UpdateChatChannelInputData data) throws SQLException;
}
