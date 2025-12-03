package interface_adapter.update_chat_channel;

import interface_adapter.ViewModel;

/**
 * View model of the update chat channel use case.
 */
public class UpdateChatChannelViewModel extends ViewModel<UpdateChatChannelState> {
    public UpdateChatChannelViewModel() {
        super("update chat channel");
        setState(new UpdateChatChannelState());
    }
}
