package use_case.profile_edit;

import java.sql.SQLException;

/**
 * Interface for profile edit use case's interactor.
 */
public interface ProfileEditInputBoundary {
    /**
     * Executes the profile edit interactor.
     * @param data data to be edited for the current user.
     * @throws SQLException whenever we can't modify the database.
     */
    void execute(ProfileEditInputData data) throws SQLException;
}
