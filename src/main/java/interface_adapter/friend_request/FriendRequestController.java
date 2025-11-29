package interface_adapter.friend_request;

import use_case.friend_request.FriendRequestInputBoundary;
import use_case.friend_request.FriendRequestInputData;

import java.sql.SQLException;

public class FriendRequestController {
    private final FriendRequestInputBoundary friendRequestInteractor;

    public FriendRequestController(FriendRequestInputBoundary friendRequestInteractor) {
        this.friendRequestInteractor = friendRequestInteractor;
    }

    public void execute(String acceptedUsername, boolean accept) throws SQLException {
        FriendRequestInputData friendRequestInputData = new FriendRequestInputData(acceptedUsername, accept);
        friendRequestInteractor.execute(friendRequestInputData);
    }

}
