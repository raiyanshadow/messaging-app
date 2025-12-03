package use_case.baseUI;

import java.sql.SQLException;

/**
 * Interface for the base UI use case's interactor.
 */
public interface BaseUiInputBoundary {
    /**
     * Get all the user's chats.
     * @param request the input data needed for the interactor.
     * @throws SQLException whenever we can't read the database.
     */
    void getUserChats(BaseUiInputData request) throws SQLException;

    /**
     * Display the add chat UI.
     * @param request request for switching to the add chat view.
     */
    void displayAddChat(BaseUiInputData request);

    /**
     * Display the friend request UI.
     * @param request request for switching to the friend request view.
     * @throws SQLException whenever we can't read the database.
     */
    void switchToFriendRequestView(BaseUiInputData request) throws SQLException;

    /**
     * Display the add contact UI.
     * @param request request for switching to the add contact view.
     * @throws SQLException whenever we can't read the database.
     */
    void switchToAddContact(BaseUiInputData request) throws SQLException;

    /**
     * Display the profile edit UI.
     * @param request request for switching to the profile edit view.
     * @throws SQLException whenever we can't read the database.
     */
    void switchToProfileEdit(BaseUiInputData request) throws SQLException;
}
