package interface_adapter.chat_channel;

import interface_adapter.ViewModel;

public class ChatChannelViewModel extends ViewModel<ChatChannelState> {
    public ChatChannelViewModel(String chatName) {
        super(chatName);
        setState(new ChatChannelState());
    }
}
