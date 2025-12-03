package use_case.add_contact;

import java.sql.SQLException;

/**
 * The interface for the add contact use case's interactor.
 */
public interface AddContactInputBoundary {
    /**
     * Executes the add contact use case (sends contact request).
     * @param addContactInputData the input data from ui
     * @throws SQLException whenever we can't modify the database or access the API.
     */
    void execute(AddContactInputData addContactInputData) throws SQLException;

}
