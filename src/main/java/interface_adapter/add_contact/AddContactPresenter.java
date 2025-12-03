package interface_adapter.add_contact;

import interface_adapter.ViewManagerModel;
import use_case.add_contact.AddContactOutputBoundary;
import use_case.add_contact.AddContactOutputData;

/**
 * Presenter for the add contact use case.
 */
public class AddContactPresenter implements AddContactOutputBoundary {
    private final AddContactViewModel addContactViewModel;

    public AddContactPresenter(AddContactViewModel addContactViewModel) {
        this.addContactViewModel = addContactViewModel;
    }

    /**
     * Successfully sent out add contact request -> have pop up to show successfully sent out request.
     * @param response the data to present
     */
    @Override
    public void prepareSuccessView(AddContactOutputData response) {
        final AddContactState state = addContactViewModel.getState();
        state.setAddContactError(null);
        state.setSuccessMessage("sending request to " + response.getReceiverUsername() + "...");
        addContactViewModel.firePropertyChange();
    }

    /**
     * Failed to send out add contact request.
     * @param errorMessage the error message to display when failed to add contact
     */
    @Override
    public void prepareFailView(String errorMessage) {
        final AddContactState state = addContactViewModel.getState();
        state.setAddContactError(errorMessage);
        state.setSuccessMessage(null);
        addContactViewModel.firePropertyChange();
    }

}
