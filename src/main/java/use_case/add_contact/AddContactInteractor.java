package use_case.add_contact;

import data_access.ContactDataAccessObject;
import data_access.UserDataAccessObject;
import entity.Contact;
import entity.User;

import java.sql.SQLException;

public class AddContactInteractor implements AddContactInputBoundary {

    private final UserDataAccessObject userDataAccessObject;
    private final ContactDataAccessObject contactDataAccessObject;
    private final AddContactOutputBoundary userPresenter;


    public AddContactInteractor(UserDataAccessObject userDataAccessObject, ContactDataAccessObject contactDataAccessObject, AddContactOutputBoundary addContactOutputBoundary) {
        this.userDataAccessObject = userDataAccessObject;
        this.contactDataAccessObject = contactDataAccessObject;
        this.userPresenter = addContactOutputBoundary;
    }

    @Override
    public void execute(AddContactInputData addContactInputData) throws SQLException {
        final User sender = addContactInputData.getSender();
        final String receiver_username = addContactInputData.getReceiverUsername();
        final User receiver = userDataAccessObject.getUserFromName(receiver_username);
        boolean in_contacts = false;
        boolean sent_request = false;
        boolean received_request = false;

        contactDataAccessObject.updateUserContacts(sender, sender.getContacts());
        contactDataAccessObject.updateUserFriendRequests(sender, sender.getFriendRequests());


        for (Contact contact: sender.getContacts()) {
            if (contact.getContact().getUsername().equals(receiver_username)) {
                System.out.println(contact.getContact().getUsername());
                in_contacts = true;
                break;
            }
        }



        for (String friend_request: sender.getFriendRequests()) {
            System.out.println(friend_request);
            if (friend_request.equals(receiver_username)) {
                received_request = true;
                break;
            }
        }

        if (receiver_username != null) {
            contactDataAccessObject.updateUserFriendRequests(receiver, receiver.getFriendRequests());
            for (String friend_request : receiver.getFriendRequests()) {
                if (friend_request.equals(sender.getUsername())) {
                    sent_request = true;
                    break;
                }
            }
        }

        // did not enter a username
        if (receiver_username == null){
            userPresenter.prepareFailView("Please enter in a username");
        }

        // user2 (user who should receive add contact request) does not exist
        else if (!userDataAccessObject.existsByName(receiver_username)) {

            System.out.println("The user: " + receiver_username +  " u want to add does not exist");
            userPresenter.prepareFailView("The user you want to add does not exist");
        }

        // receiver_username is already a contact
        else if (in_contacts) {
            System.out.println("The user: " + receiver_username + " is already in your contacts");
            userPresenter.prepareFailView("This user is already a contact");
        }

        // sender has already sent a friend request
        else if (sent_request) {
            System.out.println("You have already sent " + receiver_username + " a friend request");
            userPresenter.prepareFailView("You have already sent " + receiver_username + " a friend request");
        }

        // receiver has already sent sender a friend request
        else if (received_request) {
            System.out.println(receiver_username + " has sent you a friend request, plz go and accept to add as a contact");
            userPresenter.prepareFailView(receiver_username + " has sent you a friend request, please go and accept their friend request to add them as a contact");
        }


        else { // user2 does exist

            // user1 sends user2 a add contact request
            System.out.println("The user: " + receiver_username +  " u want to add exists");
            System.out.println("sending request...");
            userDataAccessObject.sendRequest(sender, receiver_username);
            // prepare output
            final AddContactOutputData addContactOutputData = new AddContactOutputData(sender, receiver_username);
            userPresenter.prepareSuccessView(addContactOutputData);
        }

    }

}
