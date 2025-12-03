package use_case.search_contact;

/**
 * Input data for search contact use case.
 */
public class SearchContactInputData {
    private final String query;

    public SearchContactInputData(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
