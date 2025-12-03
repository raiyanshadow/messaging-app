package use_case.friend_request;

/**
 * Interface for the friend request use case's interactor.
 */
public interface FriendRequestInputBoundary {
    /**
     * Executes the friend request use case (accept or decline).
     * @param friendRequestInputData the input data from the ui
     */
    void execute(FriendRequestInputData friendRequestInputData);

}
