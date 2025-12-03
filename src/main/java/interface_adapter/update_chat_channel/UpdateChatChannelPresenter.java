package interface_adapter.update_chat_channel;

import java.util.ArrayList;
import java.util.List;

import interface_adapter.chat_channel.MessageViewModel;
import session.SessionManager;
import use_case.update_chat_channel.MessageDto;
import use_case.update_chat_channel.UpdateChatChannelOutputBoundary;
import use_case.update_chat_channel.UpdateChatChannelOutputData;

/**
 * Presenter for the update chat channel use case.
 */
public class UpdateChatChannelPresenter implements UpdateChatChannelOutputBoundary {
    private final UpdateChatChannelViewModel updateChatChannelViewModel;
    private final SessionManager sessionManager;

    public UpdateChatChannelPresenter(UpdateChatChannelViewModel updateChatChannelViewModel,
                                      SessionManager sessionManager) {
        this.updateChatChannelViewModel = updateChatChannelViewModel;
        this.sessionManager = sessionManager;
    }

    @Override
    public void prepareSuccessView(UpdateChatChannelOutputData outputData) {
        final UpdateChatChannelState updateChatChannelState = updateChatChannelViewModel.getState();
        final List<MessageViewModel> messageViewModels = new ArrayList<>();
        for (MessageDto message : outputData.getMessages()) {
            final MessageViewModel messageViewModel = new MessageViewModel();
            messageViewModel.getState().setChannelUrl(message.getChannelUrl());
            messageViewModel.getState().setContent(message.getContent());
            messageViewModel.getState().setChannelUrl(message.getChannelUrl());
            messageViewModel.getState().setSenderID(message.getSenderID());
            messageViewModel.getState().setReceiverID(message.getReceiverID());
            messageViewModel.getState().setTimestamp(message.getTimestamp());
            if (message.getSenderID().equals(outputData.getUser1ID())) {
                messageViewModel.getState().setSenderName(outputData.getUser1Username());
            }
            else {
                messageViewModel.getState().setSenderName(outputData.getUser2Username());
            }
            messageViewModels.add(messageViewModel);
        }
        final int senderID;
        final int receiverID;
        final String senderUsername;
        final String receiverUsername;
        if (outputData.getUser1Username().equals(sessionManager.getMainUser().getUsername())) {
            senderID = outputData.getUser1ID();
            receiverID = outputData.getUser2ID();
            senderUsername = outputData.getUser1Username();
            receiverUsername = outputData.getUser2Username();
        }
        else {
            senderID = outputData.getUser2ID();
            receiverID = outputData.getUser1ID();
            senderUsername = outputData.getUser2Username();
            receiverUsername = outputData.getUser1Username();
        }
        updateChatChannelState.setChatUrl(outputData.getChatUrl());
        updateChatChannelState.setChatChannelName(outputData.getChatName());
        updateChatChannelState.setUser1Name(senderUsername);
        updateChatChannelState.setUser2Name(receiverUsername);
        updateChatChannelState.setUser1ID(senderID);
        updateChatChannelState.setUser2ID(receiverID);
        updateChatChannelState.setMessages(messageViewModels);
        updateChatChannelState.setError(null);
        updateChatChannelViewModel.firePropertyChange();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        final UpdateChatChannelState updateChatChannelState = updateChatChannelViewModel.getState();
        updateChatChannelState.setError(errorMessage);
        updateChatChannelViewModel.firePropertyChange();
    }
}
