package interface_adapter.add_chat_channel;

import interface_adapter.ViewManagerModel;
import interface_adapter.base_UI.baseUIController;
import interface_adapter.chat_channel.ChatChannelViewModel;
import use_case.add_chat_channel.AddChatChannelOutputBoundary;
import use_case.add_chat_channel.AddChatChannelOutputData;

import java.sql.SQLException;

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
    public void PresentChat(AddChatChannelOutputData response) throws SQLException {
        if (!response.isNewChat()) {
            AddChatChannelState state = viewModel2.getState();
            state.setErrorMessage("You already have a chat with this user.");
            state.setCreationSuccess(false); // Ensure success is false
            viewModel2.firePropertyChange();
        } else {

            AddChatChannelState successState = new AddChatChannelState();
            successState.setErrorMessage(null);
            successState.setCreationSuccess(true);

            viewModel2.setState(successState);
            viewModel2.firePropertyChange();
        }
    }
}

