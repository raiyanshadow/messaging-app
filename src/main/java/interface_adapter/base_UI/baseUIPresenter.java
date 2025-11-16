package interface_adapter.base_UI;

import interface_adapter.ViewManagerModel;
import interface_adapter.add_chat_channel.AddChatChannelState;
import interface_adapter.add_chat_channel.AddChatChannelViewModel;
import interface_adapter.chat_channel.ChatChannelViewModel;
import use_case.baseUI.BaseUIOutputBoundary;
import use_case.baseUI.BaseUIOutputData;

public class baseUIPresenter implements BaseUIOutputBoundary {
    private final baseUIViewModel viewModel;
    private final ViewManagerModel viewManagerModel;

    public baseUIPresenter(baseUIViewModel viewmodel, ViewManagerModel viewManagerModel) {
        this.viewModel = viewmodel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void DisplayUI(BaseUIOutputData response) {
        final baseUIState baseUIState = viewModel.getState();
        baseUIState.setChatEntities(response.getDirectChatChannels());
        baseUIState.setChatnames(response.getChatNames());
        this.viewModel.firePropertyChange();

        this.viewManagerModel.setState(viewModel.getViewName());
        this.viewManagerModel.firePropertyChange();
    }
}
