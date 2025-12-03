package interface_adapter.chat_channel;

import interface_adapter.ViewModel;

/**
 * View model for each message in a chat channel.
 */
public class MessageViewModel extends ViewModel<MessageState> {
    public MessageViewModel() {
        super("message view model");
        setState(new MessageState());
    }
}
