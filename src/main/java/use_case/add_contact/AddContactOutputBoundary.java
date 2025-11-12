package use_case.add_contact;

public interface AddContactOutputBoundary {
    /**
     * successfully added contact
     * @param addContactOutputData the data to present
     */
    void prepareSuccessView(AddContactOutputData addContactOutputData);

    /**
     * failed to add contact
     * @param errorMessage the error message to display when failed to add contact
     */
    void prepareFailView(String errorMessage);
}
