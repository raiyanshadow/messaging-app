package interface_adapter.search_contact;

import use_case.search_contact.SearchContactInputBoundary;
import use_case.search_contact.SearchContactInputData;

import java.sql.SQLException;

/**
 * Controller of the search contact use case.
 */
public class SearchContactController {
    private final SearchContactInputBoundary searchContactUseCaseInteractor;

    public SearchContactController(SearchContactInputBoundary searchContactUseCaseInteractor) {
        this.searchContactUseCaseInteractor = searchContactUseCaseInteractor;
    }

    /**
     * Calls the interactor for the search contact use case.
     * @param query the query to search by.
     * @throws SQLException whenever we can't access the database.
     */
    public void execute(String query) throws SQLException {
        SearchContactInputData searchContactInputData = new SearchContactInputData(query);
        searchContactUseCaseInteractor.execute(searchContactInputData);
    }
}
