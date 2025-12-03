package use_case.search_contact;

/**
 * Interface for the search contact use case's presenter.
 */
public interface SearchContactOutputBoundary {
    /**
     * On success, prepare the search contact success view.
     * @param outputData output data needed to show the view.
     */
    void prepareSuccessView(SearchContactOutputData outputData);

    /**
     * On failure, prepare the search contact fail view.
     * @param error error message.
     */
    void prepareFailView(String error);
}
