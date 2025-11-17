package use_case.add_contact;

public interface AddContactOutputBoundary {
    /**
     * successfully sent contact request
     * @param addContactOutputData the data to present
     */
    void prepareSuccessView(AddContactOutputData addContactOutputData);

    /**
     * failed to send add contact request
     * @param errorMessage the error message to display when failed to add contact
     */
    void prepareFailView(String errorMessage);

    void switchToContactsView();
}
