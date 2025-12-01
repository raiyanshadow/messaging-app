package use_case.profile_edit;

public interface ProfileEditOutputBoundary {
    /**
     * Called when profile edit is successful.
     * @param profileEditOutputData the data to present to the user
     */
    void prepareSuccessView(ProfileEditOutputData profileEditOutputData);

    /**
     * Called when profile edit is not successful.
     * @param message the error message
     */
    void prepareFailView(String message);
//    void switchToHomePageView();
}
