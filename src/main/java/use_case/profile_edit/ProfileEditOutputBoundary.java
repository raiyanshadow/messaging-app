package use_case.profile_edit;

public interface ProfileEditOutputBoundary {
    /**
     * Called when signup is successful.
     * @param signupOutputData the data to present to the user
     */
    void prepareSuccessView(ProfileEditOutputData signupOutputData);
}
