package interface_adapter.add_chat_channel;

import use_case.add_chat_channel.AddChatChannelInteractor;
import use_case.add_chat_channel.AddChatChannelOutputBoundary;
import use_case.add_chat_channel.CreateChatResponeModel;

public class AddChatChannelPresenter implements AddChatChannelOutputBoundary {
    AddChatChannelViewModel viewModel;

    public AddChatChannelPresenter(AddChatChannelViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void PresentChat(CreateChatResponeModel response) {
        if (response.chatName == null) {
            viewModel.setError("Please enter chat name");
        } else {
            String msg = "Succesfully created chat " + response.chatName;
            viewModel.setMessage(msg);
        }
    }
}

