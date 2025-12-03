package use_case.search_contact;

import java.sql.SQLException;

/**
 * The interface for the search contact use case's interactor.
 */
public interface SearchContactInputBoundary {
    /**
     * Searches the contact table in the database with some keyword to filter the result set and display it accordingly
     * to the user.
     * @param inputData input data needed to search.
     * @throws SQLException whenever we can't read the database.
     */
    void execute(SearchContactInputData inputData) throws SQLException;
}
