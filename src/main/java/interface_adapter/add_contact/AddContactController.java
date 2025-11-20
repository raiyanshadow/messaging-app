package interface_adapter.add_contact;

import entity.User;
import use_case.add_contact.AddContactInputBoundary;
import use_case.add_contact.AddContactInputData;

import java.sql.SQLException;

public class AddContactController {
    private final AddContactInputBoundary addContactUseCaseInteractor;

    public AddContactController(AddContactInputBoundary addContactUseCaseInteractor) {
        this.addContactUseCaseInteractor = addContactUseCaseInteractor;
    }

    public void execute(User sender, String receiver_username) throws SQLException {
        AddContactInputData addContactInputData = new AddContactInputData(sender, receiver_username);
        addContactUseCaseInteractor.execute(addContactInputData);
    }

    public void switchToContactsView() {
        addContactUseCaseInteractor.switchToContactsView();
    }
}
