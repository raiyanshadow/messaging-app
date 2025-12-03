package use_case.add_chat_channel;

import java.sql.SQLException;

/**
 * Interface for the add chat channel use case's presenter.
 */
public interface AddChatChannelOutputBoundary {
    /**
     * Presents the updated state after adding the chat channel.
     * @param createChatResponeModel the output data that the interactor gives.
     * @throws SQLException whenever we can't present the new state.
     */
    void presentChat(AddChatChannelOutputData createChatResponeModel) throws SQLException;
}
