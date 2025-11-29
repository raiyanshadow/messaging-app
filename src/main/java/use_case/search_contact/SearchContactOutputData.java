package use_case.search_contact;

import entity.User;

import java.util.List;

public class SearchContactOutputData {
    private final List<User> results;
    private final boolean useCaseFailed;

    public SearchContactOutputData(List<User> results, boolean useCaseFailed) {
        this.results = results;
        this.useCaseFailed = useCaseFailed;
    }

    public List<User> getResults() {
        return results;
    }

    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }
}
