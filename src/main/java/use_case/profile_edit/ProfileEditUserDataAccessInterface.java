package use_case.profile_edit;

import java.sql.SQLException;

import entity.User;

/**
 * The user data access interface containing methods used by the profile edit use case's interactor.
 */
public interface ProfileEditUserDataAccessInterface {
    /**
     * Update the username of a user in the data store.
     *
     * @param userId      The unique identifier of the user.
     * @param newUsername The new username to be set for the user.
     * @return returns whether or not we have successfully updated the username
     * @throws SQLException whenever it fails to modify the database.
     */
    boolean updateUsername(int userId, String newUsername) throws SQLException;

    /**
     * Update the password of a user in the data store.
     *
     * @param userId      The unique identifier of the user.
     * @param newPassword The new password to be set for the user.
     * @throws SQLException whenever it fails to modify the database.
     */
    void updatePassword(int userId, String newPassword) throws SQLException;

    /**
     * Update the preferred language of a user in the data store.
     *
     * @param userId                The unique identifier of the user.
     * @param newPreferredLanguage  The new preferred language to be set for the user.
     * @throws SQLException whenever it fails to modify the database.
     */
    void updatePreferredLanguage(int userId, String newPreferredLanguage) throws SQLException;

    /**
     * Retrieve a User object from the data store using the user's ID.
     *
     * @param userId The unique identifier of the user.
     * @return The User object corresponding to the given userId.
     * @throws SQLException whenever it fails to modify the database.
     */
    User getUserFromId(int userId) throws SQLException;
}
