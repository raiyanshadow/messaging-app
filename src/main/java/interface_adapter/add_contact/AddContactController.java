package interface_adapter.add_contact;

import use_case.add_contact.AddContactInputBoundary;
import use_case.add_contact.AddContactInputData;

import java.sql.SQLException;

public class AddContactController {
    private final AddContactInputBoundary addContactUseCaseInteractor;

    public AddContactController(AddContactInputBoundary addContactUseCaseInteractor) {
        this.addContactUseCaseInteractor = addContactUseCaseInteractor;
    }

    public void execute(String receiverUsername) throws SQLException {
        AddContactInputData addContactInputData = new AddContactInputData(receiverUsername);
        addContactUseCaseInteractor.execute(addContactInputData);
    }

}
