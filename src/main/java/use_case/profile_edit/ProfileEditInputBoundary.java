package use_case.profile_edit;

import java.sql.SQLException;

public interface ProfileEditInputBoundary {
    void execute(ProfileEditInputData data) throws SQLException;
}
