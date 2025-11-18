package interface_adapter.chat_channel;

import use_case.send_message.SendMessageOutputBoundary;
import use_case.send_message.SendMessageOutputData;

public class ChatChannelPresenter implements SendMessageOutputBoundary {
    private final MessageViewModel messageViewModel;

    public ChatChannelPresenter(MessageViewModel messageViewModel) {
        this.messageViewModel = messageViewModel;
    }

    @Override
    public void prepareSendMessageSuccessView(SendMessageOutputData outputData) {
        MessageState messageState = messageViewModel.getState();
//        messageState.setMessages(outputData.getMessage());
        messageState.setChannelURL(outputData.getChannelUrl());
        messageState.setContent(outputData.getContent());
        messageState.setSenderID(outputData.getSenderID());
        messageState.setReceiverID(outputData.getReceiverID());
        messageViewModel.firePropertyChange();
    }

    @Override
    public void prepareSendMessageFailView(String error) {
        MessageState messageState = messageViewModel.getState();
        messageState.setError(error);
        messageViewModel.firePropertyChange();
    }

    public void prepareReturnToHome() {
        MessageState messageState = messageViewModel.getState();
        messageState.setShouldGoHome(true);
        messageViewModel.firePropertyChange();
    }
}
