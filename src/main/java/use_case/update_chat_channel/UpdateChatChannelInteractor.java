package use_case.update_chat_channel;

import entity.DirectChatChannel;

import java.sql.SQLException;

public class UpdateChatChannelInteractor implements UpdateChatChannelInputBoundary{
    private final UpdateChatChannelUserDataAccessInterface updateChatChannelUserDataAccess;
    private final UpdateChatChannelOutputBoundary updateChatChannelPresenter;
    public UpdateChatChannelInteractor(UpdateChatChannelUserDataAccessInterface userDataAccessInterface, UpdateChatChannelOutputBoundary updateChatChannelOutputBoundary) {
        this.updateChatChannelUserDataAccess = userDataAccessInterface;
        this.updateChatChannelPresenter = updateChatChannelOutputBoundary;
    }

    @Override
    public void execute(UpdateChatChannelInputData inputData) throws SQLException {
        final String chatURL = inputData.getChatURL();
        if (chatURL == null) {
            updateChatChannelPresenter.prepareFailView("Chat URL is null");
        }
        else {
            final DirectChatChannel chat = updateChatChannelUserDataAccess.getDirectChatChannelByURL(chatURL);
            if (chat == null) {
                updateChatChannelPresenter.prepareFailView("Chat not found");
            }
            else {
                final UpdateChatChannelOutputData outputData = new UpdateChatChannelOutputData(chat.getChatName(), chat.getChatURL(), chat.getUser1(), chat.getUser2(), chat.getMessages());
                updateChatChannelPresenter.prepareSuccessView(outputData);
            }
        }
    }
}
