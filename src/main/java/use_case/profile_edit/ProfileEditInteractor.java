package use_case.profile_edit;

public class ProfileEditInteractor implements ProfileEditInputBoundary {
    private final ProfileEditUserDataAccessInterface userDataAccessObject;
    private final ProfileEditOutputBoundary userPresenter;

    public ProfileEditInteractor(ProfileEditUserDataAccessInterface userDataAccessObject,
                                 ProfileEditOutputBoundary userPresenter) {
        this.userDataAccessObject = userDataAccessObject;
        this.userPresenter = userPresenter;
    }

    @Override
    public void execute(ProfileEditInputData inputData) {
        int userId = inputData.getUserId();
        String username = inputData.getUsername();
        String password = inputData.getPassword();
        String preferredLanguage = inputData.getPreferredLanguage();

        if (!username.isEmpty()) {
            userDataAccessObject.updateUsername(userId, username);
        }

        if (!password.isEmpty()) {
            userDataAccessObject.updatePassword(userId, password);
        }

        if (!preferredLanguage.isEmpty()) {
            userDataAccessObject.updatePreferredLanguage(userId, preferredLanguage);
        }

        ProfileEditOutputData outputData = new ProfileEditOutputData(userId);
        userPresenter.prepareSuccessView(outputData);
    }
}
