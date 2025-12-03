package use_case.profile_edit;

import java.sql.SQLException;

/**
 * Interface for profile edit use case's interactor.
 */
public interface ProfileEditInputBoundary {
    void execute(ProfileEditInputData data) throws SQLException;
}
