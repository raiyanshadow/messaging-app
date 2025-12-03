package use_case.logout;

import entity.User;

/**
 * Interactor for the logout use case.
 */
public class LogoutInteractor implements LogoutInputBoundary {
    private final LogoutOutputBoundary userPresenter;

    public LogoutInteractor(LogoutOutputBoundary userPresenter) {
        this.userPresenter = userPresenter;
    }

    @Override
    public void logOut(LogoutInputData inputData) {
        final User user = inputData.getUser();
        userPresenter.prepareSuccessView(new LogoutOutputData(user));
    }
}
