package use_case.search_contact;

import entity.User;

import java.util.List;

public class SearchContactOutputData {
    private final List<String> matchingUsernames;
    private final boolean useCaseFailed;

    public SearchContactOutputData(List<String> matchingUsernames, boolean useCaseFailed) {
        this.matchingUsernames = matchingUsernames;
        this.useCaseFailed = useCaseFailed;
    }

    public List<String> getMatchingUsernames() {
        return matchingUsernames;
    }

    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }
}
