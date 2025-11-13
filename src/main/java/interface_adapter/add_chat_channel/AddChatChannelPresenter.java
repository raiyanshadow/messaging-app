package interface_adapter.add_chat_channel;

import interface_adapter.add_contact.AddContactState;
import use_case.add_chat_channel.AddChatChannelInputData;
import use_case.add_chat_channel.AddChatChannelInteractor;
import use_case.add_chat_channel.AddChatChannelOutputBoundary;
import use_case.add_chat_channel.AddChatChannelOutputData;

public class AddChatChannelPresenter implements AddChatChannelOutputBoundary {
    AddChatChannelViewModel viewModel;

    public AddChatChannelPresenter(AddChatChannelViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void PresentChat(AddChatChannelOutputData response) {
        if (response.getChatName() == null) {
            viewModel.setError("Please enter chat name");
        } else {
            final AddChatChannelState addChatChannelState = viewModel.getState();
            addChatChannelState.setContacts(response.getContactIDs());
            viewModel.setMessage("");
        }
    }
}

