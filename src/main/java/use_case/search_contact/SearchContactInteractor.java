package use_case.search_contact;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.User;

/**
 * Interactor for search contact use case.
 */
public class SearchContactInteractor implements SearchContactInputBoundary {
    private final SearchContactUserDataAccessInterface userDataAccessObject;
    private final SearchContactOutputBoundary userPresenter;

    public SearchContactInteractor(SearchContactUserDataAccessInterface userDataAccessInterface,
                                   SearchContactOutputBoundary searchContactOutputBoundary) {
        this.userDataAccessObject = userDataAccessInterface;
        this.userPresenter = searchContactOutputBoundary;
    }

    @Override
    public void execute(SearchContactInputData inputData) throws SQLException {
        if (inputData.getQuery() == null || inputData.getQuery().trim().isEmpty()) {
            userPresenter.prepareFailView("Please enter a username to search.");
            return;
        }

        final List<User> users = userDataAccessObject.searchUsers(inputData.getQuery());

        if (users.isEmpty()) {
            userPresenter.prepareFailView("No users found.");
        }
        else {
            final List<String> matchingUsernames = new ArrayList<>();
            for (User user : users) {
                matchingUsernames.add(user.getUsername());
            }
            final SearchContactOutputData outputData = new SearchContactOutputData(matchingUsernames, false);
            userPresenter.prepareSuccessView(outputData);
        }
    }
}

