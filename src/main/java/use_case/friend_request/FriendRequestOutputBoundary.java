package use_case.friend_request;

public interface FriendRequestOutputBoundary {

    /**
     * Prepares the success view.
     * @param friendRequestOutputData the data to present
     */
    void prepareSuccessView(FriendRequestOutputData friendRequestOutputData);


    /**
     * Prepares the fail view.
     * @param errorMessage the message to display
     */
    void prepareFailView(String errorMessage);
}
