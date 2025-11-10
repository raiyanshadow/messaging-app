package use_case.add_contact;

public interface AddContactOutputBoundary {
    // prepare success view for the add contact user case
    void prepareSuccessView(AddContactOutputData addContactOutputData);

    // prepare fail view for the add contact user case
    void prepareFailView(String errorMessage);
}
