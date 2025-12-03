package use_case.add_contact;

/**
 * Interface for the add contact use case's presenter.
 */
public interface AddContactOutputBoundary {
    /**
     * Successfully sent contact request.
     * @param addContactOutputData the data to present
     */
    void prepareSuccessView(AddContactOutputData addContactOutputData);

    /**
     * Failed to send add contact request.
     * @param errorMessage the error message to display when failed to add contact
     */
    void prepareFailView(String errorMessage);
}
