package interface_adapter.chat_channel;

import interface_adapter.ViewModel;

public class ChatChannelViewModel extends ViewModel<ChatChannelState> {
    public ChatChannelViewModel(ChatChannelState chatChannelState) {
        super("chat channel");
        setState(chatChannelState);
    }
}
