package interface_adapter.update_chat_channel;

import use_case.update_chat_channel.UpdateChatChannelOutputBoundary;
import use_case.update_chat_channel.UpdateChatChannelOutputData;
import interface_adapter.chat_channel.MessageViewModel;
import entity.Message; // TODO: Check if this breaks CA

import java.util.ArrayList;
import java.util.List;

public class UpdateChatChannelPresenter implements UpdateChatChannelOutputBoundary {
    private final UpdateChatChannelViewModel updateChatChannelViewModel;

    public UpdateChatChannelPresenter(UpdateChatChannelViewModel updateChatChannelViewModel) {
        this.updateChatChannelViewModel = updateChatChannelViewModel;
    }

    @Override
    public void prepareSuccessView(UpdateChatChannelOutputData outputData) {
        UpdateChatChannelState updateChatChannelState = updateChatChannelViewModel.getState();
        List<MessageViewModel> messageViewModels = new ArrayList<>();
        for (Message message : outputData.getMessages()) {
            MessageViewModel messageViewModel = new MessageViewModel();
            messageViewModel.getState().setChannelURL(message.getChannelURL());
            messageViewModel.getState().setContent((String) message.getContent()); // TODO: Might change if message type changes
            messageViewModel.getState().setChannelURL(message.getChannelURL());
            messageViewModel.getState().setSenderID(message.getSender().getUserID());
            messageViewModel.getState().setReceiverID(message.getReceiver().getUserID());
            messageViewModel.getState().setTimestamp(message.getTimestamp());
            messageViewModel.getState().setSenderName(message.getSender().getUsername());
            messageViewModels.add(messageViewModel);
        }
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
