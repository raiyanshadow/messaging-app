package interface_adapter.base_UI;

import java.util.Collections;

import interface_adapter.add_chat_channel.AddChatChannelViewModel;
import interface_adapter.add_contact.AddContactViewModel;
import interface_adapter.friend_request.FriendRequestViewModel;
import interface_adapter.profile_edit.ProfileEditViewModel;
import use_case.baseUI.BaseUiOutputBoundary;
import use_case.baseUI.BaseUiOutputData;

/**
 * Presenter for the base UI use case.
 */
public class BaseUiPresenter implements BaseUiOutputBoundary {
    private final BaseUiViewModel viewModelbase;
    private final interface_adapter.ViewManagerModel viewManagerModel;
    private final AddChatChannelViewModel addChatChannelViewModel;
    private final FriendRequestViewModel friendRequestViewModel;
    private final AddContactViewModel addContactViewModel;
    private final ProfileEditViewModel profileEditViewModel;

    public BaseUiPresenter(BaseUiViewModel viewmodel, interface_adapter.ViewManagerModel viewManagerModel,
                           AddChatChannelViewModel addChatChannelViewModel,
                           FriendRequestViewModel friendRequestViewModel, AddContactViewModel addContactViewModel,
                           ProfileEditViewModel profileEditViewModel) {
        this.viewModelbase = viewmodel;
        this.viewManagerModel = viewManagerModel;
        this.addChatChannelViewModel = addChatChannelViewModel;
        this.friendRequestViewModel = friendRequestViewModel;
        this.addContactViewModel = addContactViewModel;
        this.profileEditViewModel = profileEditViewModel;
    }

    @Override
    public void displayUi(BaseUiOutputData response) {
        final BaseUiState baseUiState = viewModelbase.getState();
        if (response.getDirectChatChannels() != null) {
            baseUiState.setChatEntities(response.getDirectChatChannels());
        }
        if (response.getChatNames() != null) {
            baseUiState.setChatnames(response.getChatNames());
        }
        else {
            baseUiState.setChatnames(Collections.emptyList());
        }
        baseUiState.setFriendRequests(response.getFriendRequests());
        this.viewModelbase.firePropertyChange();
        this.viewManagerModel.setState(viewModelbase.getViewName());
        this.viewManagerModel.firePropertyChange();
    }

    @Override
    public void displayAddChat() {
        addChatChannelViewModel.firePropertyChange();
        viewManagerModel.setState(addChatChannelViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }

    @Override
    public void displayFriends() {
        friendRequestViewModel.firePropertyChange();
        viewManagerModel.setState(friendRequestViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }

    @Override
    public void displayAddContact() {
        addContactViewModel.firePropertyChange();
        viewManagerModel.setState(addContactViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }

    @Override
    public void displayProfileEditView() {
        profileEditViewModel.firePropertyChange();
        viewManagerModel.setState(profileEditViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }

}
