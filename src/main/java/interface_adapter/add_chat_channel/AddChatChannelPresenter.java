package interface_adapter.add_chat_channel;

import use_case.add_chat_channel.AddChatChannelInteractor;
import use_case.add_chat_channel.AddChatChannelOutputBoundary;

public class AddChatChannelPresenter implements AddChatChannelOutputBoundary {
    AddChatChannelViewModel viewModel;
    public AddChatChannelPresenter(AddChatChannelViewModel viewModel) {
        this.viewModel = viewModel;
    }


}
