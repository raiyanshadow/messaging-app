package interface_adapter.friend_request;

import interface_adapter.base_UI.BaseUiState;
import interface_adapter.base_UI.BaseUiViewModel;
import session.SessionManager;
import use_case.friend_request.FriendRequestOutputBoundary;
import use_case.friend_request.FriendRequestOutputData;

/**
 * Presenter of the friend request use case.
 */
public class FriendRequestPresenter implements FriendRequestOutputBoundary {
    private final FriendRequestViewModel friendRequestViewModel;
    private final BaseUiViewModel baseUiViewModel;
    private final SessionManager sessionManager;

    public FriendRequestPresenter(FriendRequestViewModel friendRequestViewModel, BaseUiViewModel baseUiViewModel,
                                  SessionManager sessionManager) {
        this.friendRequestViewModel = friendRequestViewModel;
        this.baseUiViewModel = baseUiViewModel;
        this.sessionManager = sessionManager;
    }

    @Override
    public void prepareSuccessView(FriendRequestOutputData friendRequestOutputData) {
        final FriendRequestState state = friendRequestViewModel.getState();
        state.setFriendRequestError(null);
        state.setSuccessMessage("accepted " + friendRequestOutputData.getAcceptedUsername() + " as a contact");
        friendRequestViewModel.firePropertyChange();

        // Use the updated contact list fetched from DAO
        final BaseUiState baseState = baseUiViewModel.getState();
        baseState.setContacts(sessionManager.getMainUser().getContacts());
        baseUiViewModel.firePropertyChange("contacts_updated");
    }

    @Override
    public void prepareFailView(String errorMessage) {
        final FriendRequestState state = friendRequestViewModel.getState();
        state.setSuccessMessage(null);
        state.setFriendRequestError(errorMessage);
        friendRequestViewModel.firePropertyChange();

    }
}
