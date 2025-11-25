package interface_adapter.base_UI;

import interface_adapter.ViewManagerModel;
import interface_adapter.add_chat_channel.AddChatChannelState;
import interface_adapter.add_chat_channel.AddChatChannelViewModel;
import interface_adapter.add_contact.AddContactViewModel;
import interface_adapter.friend_request.FriendRequestViewModel;
import use_case.baseUI.BaseUIOutputBoundary;
import use_case.baseUI.BaseUIOutputData;
import view.AddContactView;

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
        baseUIState newState = new baseUIState();

        newState.setChatEntities(response.getDirectChatChannels());
        newState.setChatnames(response.getChatNames());

        System.out.println(response.getChatNames());
        viewModelbase.setState(newState); // replace whole object
        viewModelbase.firePropertyChange();   // newValue != oldValue
        viewManagerModel.setState(viewModelbase.getViewName());
        viewManagerModel.firePropertyChange();
    }


    @Override
    public void DisplayAddChat(BaseUIOutputData response) {

        AddChatChannelState newState = new AddChatChannelState();

        // example: maybe include error message if needed
        newState.setErrorMessage("You already have a chat with this person");

        addChatChannelViewModel.setState(newState);
        addChatChannelViewModel.firePropertyChange();  // now has real state

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