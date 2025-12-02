import entity.User;
import org.junit.jupiter.api.Test;
import use_case.logout.LogoutInputData;
import use_case.logout.LogoutInteractor;
import use_case.logout.LogoutOutputBoundary;
import use_case.logout.LogoutOutputData;

import static org.junit.jupiter.api.Assertions.*;

class LogoutInteractorTest {

    // Mock presenter to capture calls
    static class MockPresenter implements LogoutOutputBoundary {
        boolean successCalled = false;
        LogoutOutputData receivedData = null;

        @Override
        public void prepareSuccessView(LogoutOutputData outputData) {
            successCalled = true;
            receivedData = outputData;
        }
    }

    @Test
    void testLogOutSuccess() {
        // Arrange
        MockPresenter presenter = new MockPresenter();
        LogoutInteractor interactor = new LogoutInteractor(presenter);
        User mockUser = new User(1, "john", "22", "EN");
        LogoutInputData input = new LogoutInputData(mockUser);

        // Act
        interactor.logOut(input);

        // Assert
        assertTrue(presenter.successCalled, "Presenter's success method should be called");
        assertNotNull(presenter.receivedData, "OutputData should not be null");
        assertEquals(mockUser, presenter.receivedData.getUser(), "OutputData should contain the correct user");
    }
}
