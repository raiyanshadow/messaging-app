package use_case.profile_edit;

import entity.User;

import java.sql.SQLException;

public interface ProfileEditUserDataAccessInterface {
    /**
     * Update the username of a user in the data store.
     *
     * @param userId      The unique identifier of the user.
     * @param newUsername The new username to be set for the user.
     */
    boolean updateUsername(int userId, String newUsername) throws SQLException;

    /**
     * Update the password of a user in the data store.
     *
     * @param userId      The unique identifier of the user.
     * @param newPassword The new password to be set for the user.
     */
    void updatePassword(int userId, String newPassword) throws SQLException;

    /**
     * Update the preferred language of a user in the data store.
     *
     * @param userId                The unique identifier of the user.
     * @param newPreferredLanguage  The new preferred language to be set for the user.
     */
    void updatePreferredLanguage(int userId, String newPreferredLanguage) throws SQLException;

    /**
     * Retrieve a User object from the data store using the user's ID.
     *
     * @param userId The unique identifier of the user.
     * @return The User object corresponding to the given userId.
     */
    User getUserFromID(int userId) throws SQLException;
}
