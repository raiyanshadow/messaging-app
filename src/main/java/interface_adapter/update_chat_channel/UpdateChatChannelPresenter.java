package interface_adapter.update_chat_channel;

import session.SessionManager;
import use_case.update_chat_channel.MessageDTO;
import use_case.update_chat_channel.UpdateChatChannelOutputBoundary;
import use_case.update_chat_channel.UpdateChatChannelOutputData;
import interface_adapter.chat_channel.MessageViewModel;

import java.util.ArrayList;
import java.util.List;

public class UpdateChatChannelPresenter implements UpdateChatChannelOutputBoundary {
    private final UpdateChatChannelViewModel updateChatChannelViewModel;
    SessionManager sessionManager;

    public UpdateChatChannelPresenter(UpdateChatChannelViewModel updateChatChannelViewModel,
                                      SessionManager sessionManager) {
        this.updateChatChannelViewModel = updateChatChannelViewModel;
        this.sessionManager = sessionManager;
    }

    @Override
    public void prepareSuccessView(UpdateChatChannelOutputData outputData) {
        UpdateChatChannelState updateChatChannelState = updateChatChannelViewModel.getState();
        List<MessageViewModel> messageViewModels = new ArrayList<>();
        for (MessageDTO message : outputData.getMessages()) {
            MessageViewModel messageViewModel = new MessageViewModel();
            messageViewModel.getState().setChannelURL(message.getChannelURL());
            messageViewModel.getState().setContent(message.getContent());
            messageViewModel.getState().setChannelURL(message.getChannelURL());
            messageViewModel.getState().setSenderID(message.getSenderID());
            messageViewModel.getState().setReceiverID(message.getReceiverID());
            messageViewModel.getState().setTimestamp(message.getTimestamp());
            if (message.getSenderID().equals(outputData.getUser1ID())) {
                messageViewModel.getState().setSenderName(outputData.getUser1Username());
            } else {
                messageViewModel.getState().setSenderName(outputData.getUser2Username());
            }
            messageViewModels.add(messageViewModel);
        }
        int senderID;
        int receiverID;
        String senderUsername;
        String receiverUsername;
        if (outputData.getUser1Username().equals(sessionManager.getMainUser().getUsername())) {
            senderID = outputData.getUser1ID(); // OLD: sessionManager.getMainUser().getUserID()
            receiverID = outputData.getUser2ID();
            senderUsername = outputData.getUser1Username(); // OLD: sessionManager.getMainUser().getUsername()
            receiverUsername = outputData.getUser2Username();
        }
        else {
            senderID = outputData.getUser2ID(); // OLD: sessionManager.getMainUser().getUserID()
            receiverID = outputData.getUser1ID();
            senderUsername = outputData.getUser2Username(); // OLD: sessionManager.getMainUser().getUsername()
            receiverUsername = outputData.getUser1Username();
        }
        updateChatChannelState.setChatURL(outputData.getChatURL());
        updateChatChannelState.setChatChannelName(outputData.getChatName());
        updateChatChannelState.setUser1Name(senderUsername); // NOTE: Convention is user1 is the sender and user2 is the receiver
        updateChatChannelState.setUser2Name(receiverUsername);
        updateChatChannelState.setUser1ID(senderID);
        updateChatChannelState.setUser2ID(receiverID);
        updateChatChannelState.setMessages(messageViewModels);
        updateChatChannelState.setError(null);
        updateChatChannelViewModel.firePropertyChange();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        UpdateChatChannelState updateChatChannelState = updateChatChannelViewModel.getState();
        updateChatChannelState.setError(errorMessage);
        updateChatChannelViewModel.firePropertyChange();
    }
}
