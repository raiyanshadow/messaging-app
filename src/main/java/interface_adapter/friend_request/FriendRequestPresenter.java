package interface_adapter.friend_request;


import interface_adapter.ViewManagerModel;
import interface_adapter.base_UI.baseUIState;
import interface_adapter.base_UI.baseUIViewModel;
import session.SessionManager;
import use_case.friend_request.FriendRequestOutputBoundary;
import use_case.friend_request.FriendRequestOutputData;

public class FriendRequestPresenter implements FriendRequestOutputBoundary {
    private final FriendRequestViewModel friendRequestViewModel;
    private final ViewManagerModel viewManagerModel;
    private final baseUIViewModel baseUIViewModel;
    private final SessionManager sessionManager;

    public FriendRequestPresenter(FriendRequestViewModel friendRequestViewModel, ViewManagerModel viewManagerModel, baseUIViewModel baseUIViewModel, SessionManager sessionManager) {
        this.friendRequestViewModel = friendRequestViewModel;
        this.viewManagerModel = viewManagerModel;
        this.baseUIViewModel = baseUIViewModel;
        this.sessionManager = sessionManager;
    }


    @Override
    public void prepareSuccessView(FriendRequestOutputData friendRequestOutputData) {
        final FriendRequestState state = friendRequestViewModel.getState();
        state.setFriendRequestError(null);
        state.setSuccessMessage("accepted " + friendRequestOutputData.getAcceptedUsername() + " as a contact");
        friendRequestViewModel.firePropertyChange();

        // Use the updated contact list fetched from DAO
        baseUIState baseState = baseUIViewModel.getState();
        //baseState.setContacts(friendRequestOutputData.getUpdatedContactList());
        baseState.setContacts(sessionManager.getMainUser().getContacts());
        baseUIViewModel.firePropertyChange("contacts_updated");
    }



    @Override
    public void prepareFailView(String errorMessage) {
        final FriendRequestState state = friendRequestViewModel.getState();
        state.setSuccessMessage(null);
        state.setFriendRequestError(errorMessage);
        friendRequestViewModel.firePropertyChange();

    }

}
