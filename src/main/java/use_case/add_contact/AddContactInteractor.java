package use_case.add_contact;

import entity.User;

public class AddContactInteractor implements AddContactInputBoundary {

    private final AddContactUserDataAccessInterface userDataAccessObject;
    private final AddContactOutputBoundary userPresenter;


    public AddContactInteractor(AddContactUserDataAccessInterface addContactUserDataAccessInterface, AddContactOutputBoundary addContactOutputBoundary) {
        this.userDataAccessObject = addContactUserDataAccessInterface;
        this.userPresenter = addContactOutputBoundary;
    }

    @Override
    public void execute(AddContactInputData addContactInputData) {
        final User user1 = addContactInputData.getUser1();
        final User user2 = addContactInputData.getUser2();
        // user2 (user who should receive add contact request) does not exist
        if (!userDataAccessObject.exists(user2)) {
            userPresenter.prepareFailView("The user you want to add does not exist");
        }

        else { // user2 does exist

            // user1 sends user2 a add contact request
            userDataAccessObject.sendRequest(user1, user2);
            // prepare output
            final AddContactOutputData addContactOutputData = new AddContactOutputData(user1, user2);
            userPresenter.prepareSuccessView(addContactOutputData);
        }

    }
}
