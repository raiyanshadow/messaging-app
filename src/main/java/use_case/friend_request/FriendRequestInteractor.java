package use_case.friend_request;


import entity.User;
import session.SessionManager;

public class FriendRequestInteractor implements FriendRequestInputBoundary {

    private final FriendRequestUserDataAccessInterface userDataAccessObject;
    private final FriendRequestOutputBoundary userPresenter;
    private final SessionManager sessionManager;


    public FriendRequestInteractor(FriendRequestUserDataAccessInterface friendRequestUserDataAccessInterface, FriendRequestOutputBoundary friendRequestOutputBoundary, SessionManager sessionManager) {
        this.userDataAccessObject = friendRequestUserDataAccessInterface;
        this.userPresenter = friendRequestOutputBoundary;
        this.sessionManager = sessionManager;
    }

    @Override
    public void execute(FriendRequestInputData friendRequestInputData) {
        final User accepter = sessionManager.getMainUser();
        final String acceptedUsername = friendRequestInputData.getAcceptedUsername();

        // decline friend request -> delete for both users
        // did select someone to decline
        System.out.println(friendRequestInputData.getAcceptedUsername() + "HELLPPPPPPP");
        if (!friendRequestInputData.getAccept() && acceptedUsername != null){
            userDataAccessObject.deleteRequest(accepter, acceptedUsername);
            userPresenter.prepareFailView("you have declined the friend request from: " + friendRequestInputData.getAcceptedUsername());
        }

        // did not select someone to accept/decline
        else if (acceptedUsername == null) {
            userPresenter.prepareFailView("please select a friend request");
        }

        // accept friend request -> add both users to each other's contacts
        else {
            System.out.println("acceptinggggggg............");
            userDataAccessObject.acceptRequest(accepter, acceptedUsername);
            final FriendRequestOutputData friendRequestOutputData = new FriendRequestOutputData(acceptedUsername);
            userPresenter.prepareSuccessView(friendRequestOutputData);
        }
    }

}
