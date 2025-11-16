package use_case.logout;

import entity.User;

public class LogoutInteractor implements LogoutInputBoundary {
    private final LogoutOutputBoundary userPresenter;

    public LogoutInteractor(LogoutOutputBoundary userPresenter) {
        this.userPresenter = userPresenter;
    }

    @Override
    public void logOut(LogoutInputData inputData) {
        User user = inputData.getUser();
        userPresenter.prepareSuccessView(new LogoutOutputData(user));
    }
}
