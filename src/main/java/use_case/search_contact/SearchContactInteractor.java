package use_case.search_contact;

import entity.User;

import java.sql.SQLException;
import java.util.List;

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

        List<User> users = userDataAccessObject.searchUsers(inputData.getQuery());
        if (users.isEmpty()) {
            userPresenter.prepareFailView("No users found.");
        } else {
            SearchContactOutputData outputData = new SearchContactOutputData(users, false);
            userPresenter.prepareSuccessView(outputData);
        }
    }
}
