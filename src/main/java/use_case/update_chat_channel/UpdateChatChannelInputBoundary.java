package use_case.update_chat_channel;

import java.sql.SQLException;

/**
 * Interface for the update chat channel use case interactor.
 */
public interface UpdateChatChannelInputBoundary {
    /**
     * Updates the chat channel with the current data held in the database.
     * @param data input data needed to update a chat channel.
     * @throws SQLException whenever we fail to read the database.
     */
    void execute(UpdateChatChannelInputData data) throws SQLException;
}
