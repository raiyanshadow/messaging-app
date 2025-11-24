package interface_adapter.friend_request;

import entity.User;
import use_case.friend_request.FriendRequestInputBoundary;
import use_case.friend_request.FriendRequestInputData;

import java.sql.SQLException;

public class FriendRequestController {
    private final FriendRequestInputBoundary friendRequestInteractor;

    public FriendRequestController(FriendRequestInputBoundary friendRequestInteractor) {
        this.friendRequestInteractor = friendRequestInteractor;
    }

    public void execute(User acceptee, String accepted_username, boolean accept) throws SQLException {
        FriendRequestInputData friendRequestInputData = new FriendRequestInputData(acceptee, accepted_username, accept);
        friendRequestInteractor.execute(friendRequestInputData);
    }

}
