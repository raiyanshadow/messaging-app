package interface_adapter.search_contact;

import interface_adapter.ViewManagerModel;
import use_case.search_contact.SearchContactOutputBoundary;
import use_case.search_contact.SearchContactOutputData;

public class SearchContactPresenter implements SearchContactOutputBoundary {

    private final SearchContactViewModel searchContactViewModel;
    private final ViewManagerModel viewManagerModel;

    public SearchContactPresenter(SearchContactViewModel searchContactViewModel, ViewManagerModel viewManagerModel) {
        this.searchContactViewModel = searchContactViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(SearchContactOutputData response) {
        SearchContactState searchContactState = searchContactViewModel.getState();
        searchContactState.setResults(response.getResults());
        searchContactState.setError(null);
        this.searchContactViewModel.setState(searchContactState);
        this.searchContactViewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        SearchContactState searchContactState = searchContactViewModel.getState();
        searchContactState.setError(error);
        searchContactViewModel.setState(searchContactState);
        searchContactViewModel.firePropertyChanged();
    }
}
