package interface_adapter.add_contact;

import java.sql.SQLException;

import use_case.add_contact.AddContactInputBoundary;
import use_case.add_contact.AddContactInputData;

public class AddContactController {
    private final AddContactInputBoundary addContactUseCaseInteractor;

    public AddContactController(AddContactInputBoundary addContactUseCaseInteractor) {
        this.addContactUseCaseInteractor = addContactUseCaseInteractor;
    }

    public void execute(String receiverUsername) throws SQLException {
        final AddContactInputData addContactInputData = new AddContactInputData(receiverUsername);
        addContactUseCaseInteractor.execute(addContactInputData);
    }

}
