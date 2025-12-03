package use_case.friend_request;

import java.util.List;

import entity.Contact;
import entity.User;
import session.SessionManager;

/**
 * Interactor of the friend request use case.
 */
public class FriendRequestInteractor implements FriendRequestInputBoundary {

    private final FriendRequestContactDataAccessInterface userDataAccessObject;
    private final FriendRequestOutputBoundary userPresenter;
    private final SessionManager sessionManager;

    public FriendRequestInteractor(FriendRequestContactDataAccessInterface friendRequestContactDataAccessInterface,
                                   FriendRequestOutputBoundary friendRequestOutputBoundary,
                                   SessionManager sessionManager) {
        this.userDataAccessObject = friendRequestContactDataAccessInterface;
        this.userPresenter = friendRequestOutputBoundary;
        this.sessionManager = sessionManager;
    }

    @Override
    public void execute(FriendRequestInputData friendRequestInputData) {
        final User acceptee = sessionManager.getMainUser();
        final String acceptedUsername = friendRequestInputData.getAcceptedUsername();

        // decline friend request -> delete for both users
        // did select someone to decline
        if (!friendRequestInputData.getAccept() && acceptedUsername != null) {
            userDataAccessObject.deleteRequest(acceptee, acceptedUsername);
            userPresenter.prepareFailView("you have declined the friend request from: " + acceptedUsername);
        }

        // did not select someone to accept/decline
        else if (acceptedUsername == null) {
            userPresenter.prepareFailView("please select a friend request");
        }

        // accept friend request -> add both users to each other's contacts
        else {
            // accept the friend request in the DAO
            userDataAccessObject.acceptRequest(acceptee, acceptedUsername);

            // fetch the updated contact list from the user
            final List<Contact> updatedContacts = userDataAccessObject.getContacts(acceptee);

            // create output data including full contact list
            final FriendRequestOutputData friendRequestOutputData = new FriendRequestOutputData(acceptedUsername);

            sessionManager.getMainUser().setContacts(updatedContacts);

            // send to presenter
            userPresenter.prepareSuccessView(friendRequestOutputData);
        }
    }
}
