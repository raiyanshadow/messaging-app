package interface_adapter.friend_request;

import interface_adapter.ViewManagerModel;
import use_case.friend_request.FriendRequestOutputBoundary;
import use_case.friend_request.FriendRequestOutputData;

public class FriendRequestPresenter implements FriendRequestOutputBoundary {
    private final FriendRequestViewModel friendRequestViewModel;
    private final ViewManagerModel viewManagerModel;

    public FriendRequestPresenter(FriendRequestViewModel friendRequestViewModel, ViewManagerModel viewManagerModel) {
        this.friendRequestViewModel = friendRequestViewModel;
        this.viewManagerModel = viewManagerModel;
    }


    @Override
    public void prepareSuccessView(FriendRequestOutputData friendRequestOutputData) {
        final FriendRequestState state = friendRequestViewModel.getState();
        state.setFriendRequestError(null);
        state.setSuccess_message("accepted " + state.getAccepted_username() + " as a contact");
        friendRequestViewModel.firePropertyChange();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        final FriendRequestState state = friendRequestViewModel.getState();
        state.setSuccess_message(null);
        state.setFriendRequestError(errorMessage);
        friendRequestViewModel.firePropertyChange();

    }

    @Override
    public void switchToContactsView() {
        viewManagerModel.setState(friendRequestViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }
}
