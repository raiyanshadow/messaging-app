package interface_adapter.friend_request;

import interface_adapter.ViewModel;

public class FriendRequestViewModel extends ViewModel<FriendRequestState> {
    public FriendRequestViewModel() {
        super("friend request");
        setState(new FriendRequestState());
    }
}
