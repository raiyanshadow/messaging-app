package use_case.add_contact;

public class AddContactInteractor implements AddContactInputBoundary {

    private final AddContactUserDataAccessInterface userDataAccessObject;
    private final AddContactOutputBoundary userPresenter;


    public AddContactInteractor(AddContactUserDataAccessInterface addContactUserDataAccessInterface, AddContactOutputBoundary addContactOutputBoundary) {
        this.userDataAccessObject = addContactUserDataAccessInterface;
        this.userPresenter = addContactOutputBoundary;
    }

    @Override
    public void execute(AddContactInputData addContactInputData) {
        // user exists so we can add them as a contact
        if (userDataAccessObject.exists(addContactInputData.getUser())) {
            userDataAccessObject.addContact(addContactInputData.getUser());

            // final AddContactOutputData addContactOutputData = new AddContactOutputData(user)
            // userPresenter.prepareSuccessView(ad);
        }
        else {
            userPresenter.prepareFailView("User does not exist, unable to add as a contact");
        }
    }
}
