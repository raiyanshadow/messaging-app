package use_case.baseUI;

import java.sql.SQLException;

public interface BaseUIInputBoundary {
    void GetUserChats(BaseUIInputData request) throws SQLException;
}
