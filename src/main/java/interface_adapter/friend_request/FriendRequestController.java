package interface_adapter.friend_request;

import java.sql.SQLException;

import use_case.friend_request.FriendRequestInputBoundary;
import use_case.friend_request.FriendRequestInputData;

/**
 * Controller of the friend request use case.
 */
public class FriendRequestController {
    private final FriendRequestInputBoundary friendRequestInteractor;

    public FriendRequestController(FriendRequestInputBoundary friendRequestInteractor) {
        this.friendRequestInteractor = friendRequestInteractor;
    }

    /**
     * Calls the interactor of the friend request use case.
     * @param acceptedUsername the username to accept the friend request of.
     * @param accept accepts the friend request if true, else declines.
     * @throws SQLException whenever we can't access the database.
     */
    public void execute(String acceptedUsername, boolean accept) throws SQLException {
        final FriendRequestInputData friendRequestInputData = new FriendRequestInputData(acceptedUsername, accept);
        friendRequestInteractor.execute(friendRequestInputData);
    }

}
