package interface_adapter.chat_channel;

import interface_adapter.ViewModel;

public class ChatChannelViewModel extends ViewModel<ChatChannelState> {

    private final String chatName = "chatChannelView";
    public ChatChannelViewModel(String chatName) {
        super(chatName);
        setState(new ChatChannelState());
    }
    public String getChatName() {
        return chatName;
    }
}
