package interface_adapter.base_UI;

import interface_adapter.ViewManagerModel;
import interface_adapter.add_chat_channel.AddChatChannelState;
import interface_adapter.add_chat_channel.AddChatChannelViewModel;
import use_case.baseUI.BaseUIOutputBoundary;
import use_case.baseUI.BaseUIOutputData;

public class baseUIPresenter implements BaseUIOutputBoundary {
    private final baseUIViewModel viewModelbase;
    private final ViewManagerModel viewManagerModel;
    private final AddChatChannelViewModel addChatChannelViewModel;

    public baseUIPresenter(baseUIViewModel viewmodel, ViewManagerModel viewManagerModel,
                           AddChatChannelViewModel addChatChannelViewModel) {
        this.viewModelbase = viewmodel;
        this.viewManagerModel = viewManagerModel;
        this.addChatChannelViewModel = addChatChannelViewModel;
    }

    @Override
    public void DisplayUI(BaseUIOutputData response) {
        final baseUIState baseUIState = viewModelbase.getState();
        baseUIState.setChatEntities(response.getDirectChatChannels());
        baseUIState.setChatnames(response.getChatNames());
        this.viewModelbase.firePropertyChange();

        this.viewManagerModel.setState(viewModelbase.getViewName());
        this.viewManagerModel.firePropertyChange();
    }

    @Override
    public void DisplayAddChat(BaseUIOutputData response) {

        AddChatChannelState state = addChatChannelViewModel.getState();
        addChatChannelViewModel.firePropertyChange();
        viewManagerModel.setState(addChatChannelViewModel.getViewName());
        viewManagerModel.firePropertyChange();


    }
}