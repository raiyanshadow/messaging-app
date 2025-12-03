package interface_adapter.add_contact;

import java.sql.SQLException;

import use_case.add_contact.AddContactInputBoundary;
import use_case.add_contact.AddContactInputData;

/**
 * Controller for the add contact use case.
 */
public class AddContactController {
    private final AddContactInputBoundary addContactUseCaseInteractor;

    public AddContactController(AddContactInputBoundary addContactUseCaseInteractor) {
        this.addContactUseCaseInteractor = addContactUseCaseInteractor;
    }

    /**
     * Executes the interactor for the add contact use case.
     * @param receiverUsername the receivers' username.
     * @throws SQLException whenever we can't access the database.
     */
    public void execute(String receiverUsername) throws SQLException {
        final AddContactInputData addContactInputData = new AddContactInputData(receiverUsername);
        addContactUseCaseInteractor.execute(addContactInputData);
    }

}
