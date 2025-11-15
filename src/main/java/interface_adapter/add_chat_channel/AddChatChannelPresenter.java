package interface_adapter.add_chat_channel;

import interface_adapter.ViewManagerModel;
import interface_adapter.chat_channel.ChatChannelViewModel;
import use_case.add_chat_channel.AddChatChannelOutputBoundary;
import use_case.add_chat_channel.AddChatChannelOutputData;

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
    public void PresentChat(AddChatChannelOutputData response) {
        if (!response.isNewChat()) {
            // Update the AddChatChannelViewModel with an error message
            AddChatChannelState state = viewModel2.getState();
            state.setErrorMessage("You already have a chat with this user.");
            viewModel2.firePropertyChange();
            return;
        } else {
            final interface_adapter.chat_channel.ChatChannelState chatChannelState = viewModel.getState();
            chatChannelState.setChatName(response.getChatName());
            this.viewModel.firePropertyChange();

            this.viewModel2.setState(new AddChatChannelState());

            this.viewManagerModel.setState(viewModel.getViewName());
            this.viewManagerModel.firePropertyChange();
        }
    }
}

