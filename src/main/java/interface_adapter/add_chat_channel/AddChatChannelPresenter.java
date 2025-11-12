package interface_adapter.add_chat_channel;

import interface_adapter.add_contact.AddContactState;
import interface_adapter.chat_channel.ChatChannelState;
import interface_adapter.chat_channel.ChatChannelViewModel;
import use_case.add_chat_channel.AddChatChannelInteractor;
import use_case.add_chat_channel.AddChatChannelOutputBoundary;
import use_case.add_chat_channel.CreateChatResponeModel;

public class AddChatChannelPresenter implements AddChatChannelOutputBoundary {
    private final ChatChannelViewModel  viewModel;

    public AddChatChannelPresenter(ChatChannelViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void PresentChat(CreateChatResponeModel response) {
        if (response.chatName == null) {

        } else {
            final ChatChannelState chatChannelState = viewModel.getState();

            viewModel
        }
    }
}

