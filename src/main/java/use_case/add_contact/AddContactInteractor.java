package use_case.add_contact;

import entity.User;

import java.sql.SQLException;

public class AddContactInteractor implements AddContactInputBoundary {

    private final AddContactUserDataAccessInterface userDataAccessObject;
    private final AddContactOutputBoundary userPresenter;


    public AddContactInteractor(AddContactUserDataAccessInterface addContactUserDataAccessInterface, AddContactOutputBoundary addContactOutputBoundary) {
        this.userDataAccessObject = addContactUserDataAccessInterface;
        this.userPresenter = addContactOutputBoundary;
    }

    @Override
    public void execute(AddContactInputData addContactInputData) throws SQLException {
        final User sender = addContactInputData.getSender();
        final String receiver_username = addContactInputData.getReceiverUsername();
        // user2 (user who should receive add contact request) does not exist
        if (!userDataAccessObject.existsByName(receiver_username)) {
            userPresenter.prepareFailView("The user you want to add does not exist");
        }

        else { // user2 does exist

            // user1 sends user2 a add contact request
            userDataAccessObject.sendRequest(sender, receiver_username);
            // prepare output
            final AddContactOutputData addContactOutputData = new AddContactOutputData(sender, receiver_username);
            userPresenter.prepareSuccessView(addContactOutputData);
        }

    }

}
