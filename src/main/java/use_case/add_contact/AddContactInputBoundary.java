package use_case.add_contact;

public interface AddContactInputBoundary {
    /**
     * executes the add contact use case
     * @param addContactInputData the input data from ui
     */
    void execute(AddContactInputData addContactInputData);
}
