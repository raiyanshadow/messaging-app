package use_case.search_contact;

public interface SearchContactOutputBoundary {
    void prepareSuccessView(SearchContactOutputData outputData);

    void prepareFailView(String error);
}
