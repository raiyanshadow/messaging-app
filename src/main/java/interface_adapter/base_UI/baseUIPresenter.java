package interface_adapter.base_UI;

import interface_adapter.ViewManagerModel;
import interface_adapter.add_chat_channel.AddChatChannelViewModel;
import interface_adapter.chat_channel.ChatChannelViewModel;

public class baseUIPresenter {
    private final baseUIViewModel viewModel;
    private final ViewManagerModel viewManagerModel;

    public baseUIPresenter(baseUIViewModel viewmodel, ViewManagerModel viewManagerModel) {
        this.viewModel = viewmodel;
        this.viewManagerModel = viewManagerModel;
    }

    public void DisplayUI(){}

}
