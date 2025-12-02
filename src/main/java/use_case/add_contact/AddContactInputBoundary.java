package use_case.add_contact;

import java.sql.SQLException;

public interface AddContactInputBoundary {
    /**
     * Executes the add contact use case (sends contact request).
     * @param addContactInputData the input data from ui
     */
    void execute(AddContactInputData addContactInputData) throws SQLException;

}
