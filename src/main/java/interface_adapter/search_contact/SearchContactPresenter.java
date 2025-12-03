package interface_adapter.search_contact;

import interface_adapter.add_contact.AddContactState;
import interface_adapter.add_contact.AddContactViewModel;
import use_case.search_contact.SearchContactOutputBoundary;
import use_case.search_contact.SearchContactOutputData;

/**
 * Presenter for the search contact use case.
 */
public class SearchContactPresenter implements SearchContactOutputBoundary {

    private final AddContactViewModel addContactViewModel;
    private final interface_adapter.ViewManagerModel viewManagerModel;

    public SearchContactPresenter(AddContactViewModel addContactViewModel,
                                  interface_adapter.ViewManagerModel viewManagerModel) {
        this.addContactViewModel = addContactViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(SearchContactOutputData response) {
        final AddContactState addContactState = new AddContactState();
        addContactState.setMatchingUsernames(response.getMatchingUsernames());
        addContactViewModel.setState(addContactState);
        addContactViewModel.firePropertyChange();

        this.viewManagerModel.setState(addContactViewModel.getViewName());
        this.viewManagerModel.firePropertyChange();
    }

    @Override
    public void prepareFailView(String error) {
        // fails the view
    }
}
