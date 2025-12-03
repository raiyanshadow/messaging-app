package use_case.profile_edit;

/**
 * Interface for the profile edit use case's presenter.
 */
public interface ProfileEditOutputBoundary {
    /**
     * Called when signup is successful.
     * @param signupOutputData the data to present to the user
     */
    void prepareSuccessView(ProfileEditOutputData signupOutputData);
}
