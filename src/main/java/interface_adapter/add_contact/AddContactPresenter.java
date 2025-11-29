package interface_adapter.add_contact;

import interface_adapter.ViewManagerModel;
import use_case.add_contact.AddContactOutputBoundary;
import use_case.add_contact.AddContactOutputData;

public class AddContactPresenter implements AddContactOutputBoundary{
    private final AddContactViewModel addContactViewModel;
    private final ViewManagerModel viewManagerModel;

    public AddContactPresenter(AddContactViewModel addContactViewModel, ViewManagerModel viewManagerModel) {
        this.addContactViewModel = addContactViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    /**
     * successfully sent out add contact request -> have pop up to show successfully sent out request
     *
     * @param response the data to present
     */
    @Override
    public void prepareSuccessView(AddContactOutputData response) {
        final AddContactState state = addContactViewModel.getState();
        state.setAddContactError(null);
        state.setSuccessMessage("sending request to " + response.getReceiverUsername() + "...");
        addContactViewModel.firePropertyChange();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        final AddContactState state = addContactViewModel.getState();
        state.setAddContactError(errorMessage);
        state.setSuccessMessage(null);
        addContactViewModel.firePropertyChange();
    }

}
