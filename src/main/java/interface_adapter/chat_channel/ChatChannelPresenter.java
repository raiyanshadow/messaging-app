package interface_adapter.chat_channel;

import use_case.send_message.SendMessageOutputBoundary;
import use_case.send_message.SendMessageOutputData;

public class ChatChannelPresenter implements SendMessageOutputBoundary {
    private final ChatChannelViewModel chatChannelViewModel;

    public ChatChannelPresenter(ChatChannelViewModel chatChannelViewModel) {
        this.chatChannelViewModel = chatChannelViewModel;
    }

    @Override
    public void prepareSendMessageSuccessView(SendMessageOutputData outputData) {
        ChatChannelState chatChannelState = chatChannelViewModel.getState();
        chatChannelState.setMessages(outputData.getMessage());
        chatChannelViewModel.firePropertyChange();
    }

    @Override
    public void prepareSendMessageFailView(String error) {
        ChatChannelState chatChannelState = chatChannelViewModel.getState();
        chatChannelState.setError(error);
        chatChannelViewModel.firePropertyChange();
    }

    public void prepareReturnToHome() {
        ChatChannelState chatChannelState = chatChannelViewModel.getState();
        chatChannelState.setShouldGoHome(true);
        chatChannelViewModel.firePropertyChange();
    }
}
