package use_case.friend_request;

public interface FriendRequestInputBoundary {
    /**
     * executes the friend request use case (accept or decline)
     * @param friendRequestInputData the input data from the ui
     */
    void execute(FriendRequestInputData friendRequestInputData);
}
