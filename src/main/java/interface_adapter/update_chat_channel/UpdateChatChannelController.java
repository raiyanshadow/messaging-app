package interface_adapter.update_chat_channel;

import java.sql.SQLException;

import use_case.update_chat_channel.UpdateChatChannelInputBoundary;
import use_case.update_chat_channel.UpdateChatChannelInputData;

/**
 * Controller for the update chat channel use case.
 */
public class UpdateChatChannelController {
    private final UpdateChatChannelInputBoundary updateChatChannelUseCaseInteractor;

    public UpdateChatChannelController(UpdateChatChannelInputBoundary updateChatChannelUseCaseInteractor) {
        this.updateChatChannelUseCaseInteractor = updateChatChannelUseCaseInteractor;
    }

    /**
     * Calls the interactor for the update chat channel use case.
     * @param chatUrl url to update the chat for.
     * @throws SQLException whenever we can't access or modify the database.
     */
    public void execute(String chatUrl) throws SQLException {
        final UpdateChatChannelInputData updateChatInputData = new UpdateChatChannelInputData(chatUrl);
        updateChatChannelUseCaseInteractor.execute(updateChatInputData);
    }
}
