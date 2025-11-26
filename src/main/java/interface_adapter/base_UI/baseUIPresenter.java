package interface_adapter.base_UI;

import interface_adapter.ViewManagerModel;
import interface_adapter.add_chat_channel.AddChatChannelState;
import interface_adapter.add_chat_channel.AddChatChannelViewModel;
import interface_adapter.add_contact.AddContactViewModel;
import interface_adapter.friend_request.FriendRequestViewModel;
import use_case.baseUI.BaseUIOutputBoundary;
import use_case.baseUI.BaseUIOutputData;
import view.AddContactView;

import java.util.Collections;

public class baseUIPresenter implements BaseUIOutputBoundary {
    private final baseUIViewModel viewModelbase;
    private final ViewManagerModel viewManagerModel;
    private final AddChatChannelViewModel addChatChannelViewModel;
    private final FriendRequestViewModel friendRequestViewModel;
    private final AddContactViewModel addContactViewModel;

    public baseUIPresenter(baseUIViewModel viewmodel, ViewManagerModel viewManagerModel,
                           AddChatChannelViewModel addChatChannelViewModel, FriendRequestViewModel friendRequestViewModel, AddContactViewModel addContactViewModel) {
        this.viewModelbase = viewmodel;
        this.viewManagerModel = viewManagerModel;
        this.addChatChannelViewModel = addChatChannelViewModel;
        this.friendRequestViewModel = friendRequestViewModel;
        this.addContactViewModel = addContactViewModel;
    }

    @Override
    public void DisplayUI(BaseUIOutputData response) {
        final baseUIState baseUIState = viewModelbase.getState();
        if (response.getDirectChatChannels() != null){
            baseUIState.setChatEntities(response.getDirectChatChannels());}
        if (response.getChatNames() != null) {
            baseUIState.setChatnames(response.getChatNames());
        } else {
            baseUIState.setChatnames(Collections.emptyList());
        }
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

    @Override
    public void DisplayFriends(BaseUIOutputData response) {
        friendRequestViewModel.firePropertyChange();
        viewManagerModel.setState(friendRequestViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }

    @Override
    public void DisplayAddContact(BaseUIOutputData response) {
        addContactViewModel.firePropertyChange();
        viewManagerModel.setState(addContactViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }

}