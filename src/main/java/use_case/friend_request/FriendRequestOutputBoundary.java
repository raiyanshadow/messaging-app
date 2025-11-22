package use_case.friend_request;

public interface FriendRequestOutputBoundary {

    /**
     *
     * @param friendRequestOutputData the data to present
     */
    void prepareSuccessView(FriendRequestOutputData friendRequestOutputData);


    /**
     *
     * @param errorMessage the message to display
     */
    void prepareFailView(String errorMessage);
    void switchToContactsView();
}
