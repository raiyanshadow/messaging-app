package interface_adapter.chat_channel;

import interface_adapter.ViewModel;
import interface_adapter.add_chat_channel.ChatChannelState;

public class ChatChannelViewModel extends ViewModel<ChatChannelState> {
    public ChatChannelViewModel(String chatName) {
        super(chatName);
        setState(new ChatChannelState());
    }
}
