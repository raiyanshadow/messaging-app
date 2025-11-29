package use_case.search_contact;

public class SearchContactInputData {
    private final String query;

    public SearchContactInputData(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
