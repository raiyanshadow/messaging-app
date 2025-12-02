package use_case.update_chat_channel;

import java.sql.SQLException;

public interface UpdateChatChannelInputBoundary {
    /**
     * Retrieves information about a given chat to update the chat on the view.
     * @param data Input data containing a chatUrl required to update a chat
     * @throws SQLException throws an SQLException if database cannot be connected to
     */
    void execute(UpdateChatChannelInputData data) throws SQLException;
}
