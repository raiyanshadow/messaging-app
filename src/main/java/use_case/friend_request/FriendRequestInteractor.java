package use_case.friend_request;


import entity.Contact;
import entity.User;
import session.SessionManager;
import use_case.add_contact.AddContactOutputData;

import java.util.List;

public class FriendRequestInteractor implements FriendRequestInputBoundary {

    private final FriendRequestUserDataAccessInterface userDataAccessObject;
    private final FriendRequestOutputBoundary userPresenter;
    private SessionManager sessionManager;


    public FriendRequestInteractor(FriendRequestUserDataAccessInterface friendRequestUserDataAccessInterface, FriendRequestOutputBoundary friendRequestOutputBoundary,
                                   SessionManager sessionManager) {
        this.userDataAccessObject = friendRequestUserDataAccessInterface;
        this.userPresenter = friendRequestOutputBoundary;
        this.sessionManager = sessionManager;
    }

    @Override
    public void execute(FriendRequestInputData friendRequestInputData) {
        final User acceptee = friendRequestInputData.getAcceptee();
        final String accepted_username = friendRequestInputData.getAccepted_username();

        // decline friend request -> delete for both users
        // did select someone to decline
        if (!friendRequestInputData.getAccept() && friendRequestInputData.getAccepted_username() != null){
            userDataAccessObject.deleteRequest(acceptee, accepted_username);
            userPresenter.prepareFailView("you have declined the friend request from: " + friendRequestInputData.getAccepted_username());
        }

        // did not select someone to accept/decline
        else if (friendRequestInputData.getAccepted_username() == null) {
            userPresenter.prepareFailView("please select a friend request");
        }

        // accept friend request -> add both users to each other's contacts
        else {
            System.out.println("acceptinggggggg............");

            // accept the friend request in the DAO
            userDataAccessObject.acceptRequest(acceptee, accepted_username);

            // fetch the updated contact list from the user
            List<Contact> updatedContacts = userDataAccessObject.getContacts(acceptee);

            // create output data including full contact list
            final FriendRequestOutputData friendRequestOutputData = new FriendRequestOutputData(acceptee, accepted_username);
            friendRequestOutputData.setUpdatedContactList(updatedContacts);

            sessionManager.getMainUser().setContacts(updatedContacts);

            // send to presenter
            userPresenter.prepareSuccessView(friendRequestOutputData);
        }

    }

}
