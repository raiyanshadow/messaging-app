package use_case.profile_edit;

public interface ProfileEditUserDataAccessInterface {
    /**
     * Update the username of a user in the data store.
     *
     * @param userId      The unique identifier of the user.
     * @param newUsername The new username to be set for the user.
     */
    void updateUsername(int userId, String newUsername);

    /**
     * Update the password of a user in the data store.
     *
     * @param userId      The unique identifier of the user.
     * @param newPassword The new password to be set for the user.
     */
    void updatePassword(int userId, String newPassword);

    /**
     * Update the preferred language of a user in the data store.
     *
     * @param userId                The unique identifier of the user.
     * @param newPreferredLanguage  The new preferred language to be set for the user.
     */
    void updatePreferredLanguage(int userId, String newPreferredLanguage);
}
