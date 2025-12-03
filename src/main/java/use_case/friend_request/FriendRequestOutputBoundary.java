package use_case.friend_request;

/**
 * OInterface for the friend request use case's presenter.
 */
public interface FriendRequestOutputBoundary {
    /**
     * On success, prepare the friend request success view.
     * @param friendRequestOutputData the data to present
     */
    void prepareSuccessView(FriendRequestOutputData friendRequestOutputData);

    /**
     * On failure, prepare the friend request fail view.
     * @param errorMessage the message to display
     */
    void prepareFailView(String errorMessage);
}
