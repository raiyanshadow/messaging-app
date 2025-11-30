package interface_adapter.search_contact;

import interface_adapter.ViewManagerModel;
import interface_adapter.add_contact.AddContactState;
import interface_adapter.add_contact.AddContactViewModel;
import use_case.search_contact.SearchContactOutputBoundary;
import use_case.search_contact.SearchContactOutputData;

public class SearchContactPresenter implements SearchContactOutputBoundary {

    private final AddContactViewModel addContactViewModel;
    private final ViewManagerModel viewManagerModel;

    public SearchContactPresenter(AddContactViewModel addContactViewModel, ViewManagerModel viewManagerModel) {
        this.addContactViewModel = addContactViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(SearchContactOutputData response) {
        AddContactState addContactState = new AddContactState();
        addContactState.setMatchingUsernames(response.getMatchingUsernames());
        addContactViewModel.setState(addContactState);
        addContactViewModel.firePropertyChange();

        this.viewManagerModel.setState(addContactViewModel.getViewName());
        this.viewManagerModel.firePropertyChange();

        /*
        SearchContactState searchContactState = searchContactViewModel.getState();
        searchContactState.setResults(response.getMatchingUsernames());
        searchContactState.setError(null);
        this.searchContactViewModel.setState(searchContactState);
        this.searchContactViewModel.firePropertyChanged();
         */
    }

    @Override
    public void prepareFailView(String error) {
        /*
        SearchContactState searchContactState = searchContactViewModel.getState();
        searchContactState.setError(error);
        searchContactViewModel.setState(searchContactState);
        searchContactViewModel.firePropertyChanged();
         */
    }
}
