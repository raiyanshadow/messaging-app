package use_case.profile_edit;

public class ProfileEditOutputData {
    private final int userId;

    public ProfileEditOutputData(int userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return String.valueOf(userId);
    }
}
