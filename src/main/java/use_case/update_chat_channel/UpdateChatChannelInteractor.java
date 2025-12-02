package use_case.update_chat_channel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.DirectChatChannel;
import entity.Message;

public class UpdateChatChannelInteractor implements UpdateChatChannelInputBoundary {
    private final UpdateChatChannelUserDataAccessInterface updateChatChannelUserDataAccess;
    private final UpdateChatChannelOutputBoundary updateChatChannelPresenter;

    public UpdateChatChannelInteractor(UpdateChatChannelUserDataAccessInterface userDataAccessInterface,
                                       UpdateChatChannelOutputBoundary updateChatChannelOutputBoundary) {
        this.updateChatChannelUserDataAccess = userDataAccessInterface;
        this.updateChatChannelPresenter = updateChatChannelOutputBoundary;
    }

    @Override
    public void execute(UpdateChatChannelInputData inputData) throws SQLException {
        final String chatUrl = inputData.getChatUrl();
        if (chatUrl == null) {
            updateChatChannelPresenter.prepareFailView("Chat URL is null");
        }
        else {
            final DirectChatChannel chat = updateChatChannelUserDataAccess.getDirectChatChannelByURL(chatUrl);
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
        final List<MessageDto> messageDtos = new ArrayList<>();
        for (Message message : chat.getMessages()) {
            // NOTE: We currently assume message is a textMessage
            final MessageDto messageDto = new MessageDto(message.getChannelUrl(), message.getSenderId(),
                    message.getReceiverId(), message.getTimestamp(), (String) message.getContent());
            messageDtos.add(messageDto);
        }
        return new UpdateChatChannelOutputData(chat.getChatName(),
                chat.getChatUrl(), chat.getUser1().getUsername(), chat.getUser1().getUserID(),
                chat.getUser2().getUsername(), chat.getUser2().getUserID(), messageDtos);
    }
}
