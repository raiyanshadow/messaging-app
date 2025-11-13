package interface_adapter.add_chat_channel;

import interface_adapter.ViewManagerModel;
import interface_adapter.add_contact.AddContactState;
import interface_adapter.chat_channel.ChatChannelViewModel;
import use_case.add_chat_channel.AddChatChannelInteractor;
import use_case.add_chat_channel.AddChatChannelOutputBoundary;
import use_case.add_chat_channel.CreateChatResponeModel;

public class AddChatChannelPresenter implements AddChatChannelOutputBoundary {
    private final ChatChannelViewModel  viewModel;
    private final AddChatChannelViewModel viewModel2;
    private final ViewManagerModel viewManagerModel;

    public AddChatChannelPresenter(ChatChannelViewModel viewModel, AddChatChannelViewModel viewmodel2,
                                   ViewManagerModel viewManagerModel) {
        this.viewModel = viewModel;
        this.viewModel2 = viewmodel2;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void PresentChat(CreateChatResponeModel response) {
        if (response.chatName == null) {
            System.out.println("hi");
        } else {
            final interface_adapter.add_chat_channel.ChatChannelState chatChannelState = viewModel.getState();
            chatChannelState.setChatName(response.chatName);
            this.viewModel.firePropertyChange();

            this.viewModel2.setState(new AddChatChannelState());

            this.viewManagerModel.setState(viewModel.getViewName());
            this.viewManagerModel.firePropertyChange();

        }
    }
}

