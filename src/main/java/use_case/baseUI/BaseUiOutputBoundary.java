package use_case.baseUI;

/**
 * The interface for base UI use case's presenter.
 */
public interface BaseUiOutputBoundary {
    /**
     * Displays the base UI.
     * @param response response to display the UI with relevant information.
     */
    void displayUi(BaseUiOutputData response);

    /**
     * Displays the add chat UI.
     */
    void displayAddChat();

    /**
     * Displays the friend request UI.
     */
    void displayFriends();

    /**
     * Displays the add contact UI.
     */
    void displayAddContact();

    /**
     * Displays the profile edit UI.
     */
    void displayProfileEditView();
}
