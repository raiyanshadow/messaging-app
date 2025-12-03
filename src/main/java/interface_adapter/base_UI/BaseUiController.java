package interface_adapter.base_UI;

import java.sql.SQLException;

import use_case.baseUI.BaseUiInputBoundary;
import use_case.baseUI.BaseUiInputData;
import use_case.baseUI.BaseUiInteractor;

/**
 * Controller for the base UI use case.
 */
public class BaseUiController {
    private final BaseUiInputBoundary baseUiInteractor;

    public BaseUiController(BaseUiInteractor baseUiInteractor) {
        this.baseUiInteractor = baseUiInteractor;
    }

    /**
     * Calls the interactor to gather information in order to display the UI.
     * @throws SQLException whenever we can't access the database.
     */
    public void displayUi() throws SQLException {
        final BaseUiInputData request = new BaseUiInputData();
        baseUiInteractor.getUserChats(request);
    }

    /**
     * Calls the interactor for switching to the add chat channel UI.
     * @throws SQLException whenever we can't access the database.
     */
    public void newChat() throws SQLException {
        final BaseUiInputData request = new BaseUiInputData();
        baseUiInteractor.displayAddChat(request);
    }

    /**
     * Calls the interactor for switching to the friend request UI.
     * @throws SQLException whenever we can't access the database.
     */
    public void switchToFriendRequestView() throws SQLException {
        final BaseUiInputData request = new BaseUiInputData();
        baseUiInteractor.switchToFriendRequestView(request);
    }

    /**
     * Calls the interactor for switching to the add contact UI.
     * @throws SQLException whenever we can't access the database.
     */
    public void switchToAddContact() throws SQLException {
        final BaseUiInputData request = new BaseUiInputData();
        baseUiInteractor.switchToAddContact(request);
    }

    /**
     * Calls the interactor for switching to the profile edit UI.
     * @throws SQLException whenever we can't access the database.
     */
    public void switchToProfileEdit() throws SQLException {
        final BaseUiInputData request = new BaseUiInputData();
        baseUiInteractor.switchToProfileEdit(request);
    }

}
