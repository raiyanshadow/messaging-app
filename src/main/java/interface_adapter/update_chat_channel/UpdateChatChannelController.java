package interface_adapter.update_chat_channel;

import java.sql.SQLException;

import use_case.update_chat_channel.UpdateChatChannelInputBoundary;
import use_case.update_chat_channel.UpdateChatChannelInputData;

public class UpdateChatChannelController {
    private final UpdateChatChannelInputBoundary updateChatChannelUseCaseInteractor;

    public UpdateChatChannelController(UpdateChatChannelInputBoundary updateChatChannelUseCaseInteractor) {
        this.updateChatChannelUseCaseInteractor = updateChatChannelUseCaseInteractor;
    }

    /**
     * Executes the interactor's method with the given input data retrieved from the view.
     * @param chatUrl takes in a chat URL to pass to the interactor
     * @throws SQLException throw an exception if the program failed to retrieve from the database
     */
    public void execute(String chatUrl) throws SQLException {
        final UpdateChatChannelInputData updateChatInputData = new UpdateChatChannelInputData(chatUrl);
        updateChatChannelUseCaseInteractor.execute(updateChatInputData);
    }
}
