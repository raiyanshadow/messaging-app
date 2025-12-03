package interface_adapter.add_chat_channel;

import java.sql.SQLException;

import use_case.add_chat_channel.AddChatChannelOutputBoundary;
import use_case.add_chat_channel.AddChatChannelOutputData;

/**
 * Presenter for adding chat channel use case.
 */
public class AddChatChannelPresenter implements AddChatChannelOutputBoundary {
    private final AddChatChannelViewModel viewModel;

    public AddChatChannelPresenter(AddChatChannelViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void presentChat(AddChatChannelOutputData response) throws SQLException {
        if (!response.isNewChat()) {
            final AddChatChannelState state = viewModel.getState();
            state.setErrorMessage("You already have a chat with this user.");
            state.setCreationSuccess(false);
            viewModel.firePropertyChange();
        }
        else {
            final AddChatChannelState successState = new AddChatChannelState();
            successState.setErrorMessage(null);
            successState.setCreationSuccess(true);

            viewModel.setState(successState);
            viewModel.firePropertyChange();
        }
    }
}

