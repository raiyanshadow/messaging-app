package use_case.update_chat_channel;

import entity.DirectChatChannel;
import entity.Message;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UpdateChatChannelInteractor implements UpdateChatChannelInputBoundary{
    private final UpdateChatChannelUserDataAccessInterface updateChatChannelUserDataAccess;
    private final UpdateChatChannelOutputBoundary updateChatChannelPresenter;
    public UpdateChatChannelInteractor(UpdateChatChannelUserDataAccessInterface userDataAccessInterface,
                                       UpdateChatChannelOutputBoundary updateChatChannelOutputBoundary) {
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
            else if (chat.getChatUrl().isEmpty()) {
                updateChatChannelPresenter.prepareFailView("Chat URL is empty");
            }
            else {
                // Create MessageDTO
                final UpdateChatChannelOutputData outputData = getUpdateChatChannelOutputData(chat);
                updateChatChannelPresenter.prepareSuccessView(outputData);
            }
        }
    }

    private static UpdateChatChannelOutputData getUpdateChatChannelOutputData(DirectChatChannel chat) {
        List<MessageDTO> messageDTOs = new ArrayList<>();
        for (Message message : chat.getMessages()) {
            MessageDTO messageDto = new MessageDTO(message.getChannelUrl(), message.getSenderId(),
                    message.getReceiverId(), message.getTimestamp(), (String) message.getContent()); // We assume message is a textMessage
            messageDTOs.add(messageDto);
        }
        return new UpdateChatChannelOutputData(chat.getChatName(),
                chat.getChatUrl(), chat.getUser1().getUsername(), chat.getUser1().getUserID(),
                chat.getUser2().getUsername(), chat.getUser2().getUserID(), messageDTOs);
    }
}
