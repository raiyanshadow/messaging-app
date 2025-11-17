package interface_adapter.add_contact;

import entity.User;
import use_case.add_contact.AddContactInputBoundary;
import use_case.add_contact.AddContactInputData;
import use_case.add_contact.AddContactOutputBoundary;

public class AddContactController {

    private final AddContactInputBoundary addContactUseCaseInteractor;

    public AddContactController(AddContactInputBoundary addContactUseCaseInteractor) {
        this.addContactUseCaseInteractor = addContactUseCaseInteractor;
    }

    public void execute(User user1, User user2) {
        AddContactInputData addContactInputData = new AddContactInputData(user1, user2);
        addContactUseCaseInteractor.execute(addContactInputData);
    }

    public void switchToContactsView() {
        addContactUseCaseInteractor.switchToContactsView();
    }
}
