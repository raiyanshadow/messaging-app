package interface_adapter.friend_request;

import entity.User;
import interface_adapter.ViewManagerModel;
import interface_adapter.base_UI.baseUIState;
import interface_adapter.base_UI.baseUIViewModel;
import use_case.friend_request.FriendRequestOutputBoundary;
import use_case.friend_request.FriendRequestOutputData;

public class FriendRequestPresenter implements FriendRequestOutputBoundary {
    private final FriendRequestViewModel friendRequestViewModel;
    private final ViewManagerModel viewManagerModel;
    private final baseUIViewModel baseUIViewModel;

    public FriendRequestPresenter(FriendRequestViewModel friendRequestViewModel, ViewManagerModel viewManagerModel, baseUIViewModel baseUIViewModel) {
        this.friendRequestViewModel = friendRequestViewModel;
        this.viewManagerModel = viewManagerModel;
        this.baseUIViewModel = baseUIViewModel;
    }


    @Override
    public void prepareSuccessView(FriendRequestOutputData friendRequestOutputData) {
        final FriendRequestState state = friendRequestViewModel.getState();
        state.setFriendRequestError(null);
        state.setSuccess_message("accepted " + friendRequestOutputData.getAcceptedUsername() + " as a contact");
        friendRequestViewModel.firePropertyChange();

        // Use the updated contact list fetched from DAO
        baseUIState baseState = baseUIViewModel.getState();
        baseState.setContacts(friendRequestOutputData.getUpdatedContactList());
        baseUIViewModel.firePropertyChange("contacts_updated");
    }



    @Override
    public void prepareFailView(String errorMessage) {
        final FriendRequestState state = friendRequestViewModel.getState();
        state.setSuccess_message(null);
        state.setFriendRequestError(errorMessage);
        friendRequestViewModel.firePropertyChange();

    }

}
