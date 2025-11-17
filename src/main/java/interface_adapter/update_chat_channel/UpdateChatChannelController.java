package interface_adapter.update_chat_channel;

import use_case.update_chat_channel.UpdateChatChannelInputBoundary;
import use_case.update_chat_channel.UpdateChatChannelInputData;

import java.sql.SQLException;

public class UpdateChatChannelController {
    private final UpdateChatChannelInputBoundary updateChatChannelUseCaseInteractor;

    public UpdateChatChannelController(UpdateChatChannelInputBoundary updateChatChannelUseCaseInteractor) {
        this.updateChatChannelUseCaseInteractor = updateChatChannelUseCaseInteractor;
    }
    public void execute(String chatURL) throws SQLException {
        final UpdateChatChannelInputData updateChatInputData = new UpdateChatChannelInputData(chatURL);
        updateChatChannelUseCaseInteractor.execute(updateChatInputData);
    }
}
