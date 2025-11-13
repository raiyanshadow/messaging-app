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
        final User user1 = friendRequestInputData.getUser1();
        final User user2 = friendRequestInputData.getUser2();

        // decline friend request -> delete for both users
        if (!friendRequestInputData.getAccept()){
            userDataAccessObject.deleteRequest(user1, user2);
            userPresenter.prepareFailView("you have declined the friend request");
        }
        // accept friend request -> add both users to each other's contacts
        else {
            userDataAccessObject.acceptRequest(user1, user2);
            final FriendRequestOutputData friendRequestOutputData = new FriendRequestOutputData(user1, user2);
            userPresenter.prepareSuccessView(friendRequestOutputData);
        }
    }
}
