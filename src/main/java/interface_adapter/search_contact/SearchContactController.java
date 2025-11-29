package interface_adapter.search_contact;

import use_case.search_contact.SearchContactInputBoundary;
import use_case.search_contact.SearchContactInputData;

import java.sql.SQLException;

public class SearchContactController {
    final SearchContactInputBoundary searchContactUseCaseInteractor;

    public SearchContactController(SearchContactInputBoundary searchContactUseCaseInteractor) {
        this.searchContactUseCaseInteractor = searchContactUseCaseInteractor;
    }

    public void execute(String query) throws SQLException {
        SearchContactInputData searchContactInputData = new SearchContactInputData(query);
        searchContactUseCaseInteractor.execute(searchContactInputData);
    }
}
