package use_case.friend_request;


import entity.User;
import use_case.add_contact.AddContactOutputData;

public class FriendRequestInteractor implements FriendRequestInputBoundary {

    private final FriendRequestUserDataAccessInterface userDataAccessObject;
    private final FriendRequestOutputBoundary userPresenter;


    public FriendRequestInteractor(FriendRequestUserDataAccessInterface friendRequestUserDataAccessInterface, FriendRequestOutputBoundary friendRequestOutputBoundary) {
        this.userDataAccessObject = friendRequestUserDataAccessInterface;
        this.userPresenter = friendRequestOutputBoundary;
    }

    @Override
    public void execute(FriendRequestInputData friendRequestInputData) {
        final User acceptee = friendRequestInputData.getAcceptee();
        final String accepted_username = friendRequestInputData.getAccepted_username();

        // decline friend request -> delete for both users
        if (!friendRequestInputData.getAccept()){
            userDataAccessObject.deleteRequest(acceptee, accepted_username);
            userPresenter.prepareFailView("you have declined the friend request");
        }
        // accept friend request -> add both users to each other's contacts
        else {
            userDataAccessObject.acceptRequest(acceptee, accepted_username);
            final FriendRequestOutputData friendRequestOutputData = new FriendRequestOutputData(acceptee, accepted_username);
            userPresenter.prepareSuccessView(friendRequestOutputData);
        }
    }

    @Override
    public void switchToContactsView() {
        userPresenter.switchToContactsView();
    }
}
