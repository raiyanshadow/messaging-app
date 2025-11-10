package use_case.search_contact;

public interface SearchContactOutputBoundary {

    // prepare success view for search contact use case
    void prepareSuccessView(SearchContactOutputData searchContactOutputData);

    // prepare fail view for search contact use case
    void prepareFailView(String errorMessage);
}
