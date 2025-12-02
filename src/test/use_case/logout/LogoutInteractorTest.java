package use_case.logout;

import entity.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LogoutInteractorTest {

    @Test
    void successTest() {
        User user = new User(1, "TestUser", "password", "English");
        LogoutInputData inputData = new LogoutInputData(user);

        LogoutOutputBoundary successPresenter = new LogoutOutputBoundary() {
            @Override
            public void prepareSuccessView(LogoutOutputData outputData) {
                assertEquals(user, outputData.getUser());
                assertEquals("TestUser", outputData.getUser().getUsername());
            }
        };

        LogoutInputBoundary interactor = new LogoutInteractor(successPresenter);
        interactor.logOut(inputData);
    }
}
