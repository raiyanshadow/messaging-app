package interface_adapter.update_chat_channel;

import session.SessionManager;
import use_case.update_chat_channel.UpdateChatChannelOutputBoundary;
import use_case.update_chat_channel.UpdateChatChannelOutputData;
import interface_adapter.chat_channel.MessageViewModel;
import entity.Message; // TODO: Check if this breaks CA

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
        for (Message message : outputData.getMessages()) {
            MessageViewModel messageViewModel = new MessageViewModel();
            messageViewModel.getState().setChannelURL(message.getChannelUrl());
            messageViewModel.getState().setContent((String) message.getContent()); // TODO: Might change if message type changes
            messageViewModel.getState().setChannelURL(message.getChannelUrl());
            messageViewModel.getState().setSenderID(message.getSenderId());
            messageViewModel.getState().setReceiverID(message.getReceiverId());
            messageViewModel.getState().setTimestamp(message.getTimestamp());
            if (message.getSenderId().equals(outputData.getUser1().getUserID())) {
                messageViewModel.getState().setSenderName(outputData.getUser1().getUsername());
            } else {
                messageViewModel.getState().setSenderName(outputData.getUser2().getUsername());
            }
            messageViewModels.add(messageViewModel);
        }
        Integer senderID;
        Integer receiverID;
        String senderUsername;
        String receiverUsername;
        if (outputData.getUser1().getUsername().equals(sessionManager.getMainUser().getUsername())) {
            senderID = sessionManager.getMainUser().getUserID();
            receiverID = outputData.getUser2().getUserID();
            senderUsername = sessionManager.getMainUser().getUsername();
            receiverUsername = outputData.getUser2().getUsername();
        }
        else {
            senderID = sessionManager.getMainUser().getUserID();
            receiverID = outputData.getUser1().getUserID();
            senderUsername = sessionManager.getMainUser().getUsername();
            receiverUsername = outputData.getUser1().getUsername();
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
