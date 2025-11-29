package interface_adapter.search_contact;

import entity.User;

import java.util.ArrayList;
import java.util.List;

public class SearchContactState {
    private String query = "";
    private List<User> results = new ArrayList<>();
    private String error = null;

    public SearchContactState(SearchContactState copy) {
        query = copy.query;
        results = copy.results;
        error = copy.error;
    }

    public SearchContactState() {
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public List<User> getResults() {
        return results;
    }

    public void setResults(List<User> results) {
        this.results = results;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
