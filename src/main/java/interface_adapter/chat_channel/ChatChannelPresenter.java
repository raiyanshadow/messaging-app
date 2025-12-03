package interface_adapter.chat_channel;

import use_case.send_message.SendMessageOutputBoundary;
import use_case.send_message.SendMessageOutputData;

/**
 * Presenter for the associated use cases of chat channel.
 */
public class ChatChannelPresenter implements SendMessageOutputBoundary {

    private final MessageViewModel messageViewModel;

    public ChatChannelPresenter(MessageViewModel messageViewModel) {
        this.messageViewModel = messageViewModel;
    }

    @Override
    public void prepareSendMessageSuccessView(SendMessageOutputData outputData) {
        final MessageState messageState = messageViewModel.getState();
        messageState.setChannelUrl(outputData.getChannelUrl());
        messageState.setContent(outputData.getContent());
        messageState.setSenderID(outputData.getSenderID());
        messageState.setReceiverID(outputData.getReceiverID());
        messageState.setTimestamp(outputData.getTimestamp());
        messageViewModel.firePropertyChange();
    }

    @Override
    public void prepareSendMessageFailView(String error) {
        final MessageState messageState = messageViewModel.getState();
        messageState.setError(error);
        messageViewModel.firePropertyChange();
    }
}
