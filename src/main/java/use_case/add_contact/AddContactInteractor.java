package use_case.add_contact;

import java.sql.SQLException;

import entity.Contact;
import entity.User;
import session.Session;

/**
 * Interactor of the add contact use case.
 */
public class AddContactInteractor implements AddContactInputBoundary {

    private final AddContactUserDataAccessInterface userDataAccessObject;
    private final AddContactContactDataAccessInterface contactDataAccessObject;
    private final AddContactOutputBoundary userPresenter;
    private final Session sessionManager;

    public AddContactInteractor(AddContactUserDataAccessInterface userDataAccessObject,
                                AddContactContactDataAccessInterface contactDataAccessObject,
                                AddContactOutputBoundary addContactOutputBoundary, Session sessionManager) {
        this.userDataAccessObject = userDataAccessObject;
        this.contactDataAccessObject = contactDataAccessObject;
        this.userPresenter = addContactOutputBoundary;
        this.sessionManager = sessionManager;
    }

    @Override
    public void execute(AddContactInputData addContactInputData) throws SQLException {
        final User sender = this.sessionManager.getMainUser();
        final String receiverUsername = addContactInputData.getReceiverUsername();
        final User receiver = userDataAccessObject.getUserFromName(receiverUsername);
        boolean inContacts = false;

        contactDataAccessObject.updateUserContacts(sender, sender.getContacts());
        contactDataAccessObject.updateUserFriendRequests(sender, sender.getFriendRequests());

        for (Contact contact: sender.getContacts()) {
            if (contact.getContact().getUsername().equals(receiverUsername)) {
                System.out.println(contact.getContact().getUsername());
                inContacts = true;
                break;
            }
        }

        boolean sentRequest = false;
        boolean receivedRequest = false;
        for (String friendRequest: sender.getFriendRequests()) {
            System.out.println(friendRequest);
            if (friendRequest.equals(receiverUsername)) {
                receivedRequest = true;
                break;
            }
        }

        if (receiverUsername != null && userDataAccessObject.existsByName(receiverUsername)) {
            contactDataAccessObject.updateUserFriendRequests(receiver, receiver.getFriendRequests());
            for (String friendRequest : receiver.getFriendRequests()) {
                if (friendRequest.equals(sender.getUsername())) {
                    sentRequest = true;
                    break;
                }
            }
        }

        // did not enter a username
        if (receiverUsername == null) {
            userPresenter.prepareFailView("Please enter in a username");
        }

        else if (receiverUsername.equals(sender.getUsername())) {
            userPresenter.prepareFailView("Can not send request to yourself");
        }

        // receiver (user who should receive add contact request) does not exist
        else if (!userDataAccessObject.existsByName(receiverUsername)) {
            userPresenter.prepareFailView("The user you want to add does not exist");
        }

        // receiverUsername is already a contact
        else if (inContacts) {
            userPresenter.prepareFailView(receiverUsername + " is already a contact");
        }

        // sender has already sent a friend request
        else if (sentRequest) {
            userPresenter.prepareFailView("You have already sent " + receiverUsername + " a friend request");
        }

        // receiver has already sent sender a friend request
        else if (receivedRequest) {
            userPresenter.prepareFailView(receiverUsername
                    + " has sent you a friend request, please go and accept their "
                    + "friend request to add them as a contact");
        }

        else {
            // receiver does exist
            // send request in DAO
            userDataAccessObject.sendRequest(sender, receiverUsername);
            // prepare output
            final AddContactOutputData addContactOutputData = new AddContactOutputData(receiverUsername);
            userPresenter.prepareSuccessView(addContactOutputData);
        }

    }

}
