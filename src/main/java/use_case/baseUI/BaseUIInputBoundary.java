package use_case.baseUI;

import java.sql.SQLException;

public interface BaseUIInputBoundary {
    void GetUserChats(BaseUIInputData request) throws SQLException;
    void displayAddChat(BaseUIInputData request);
    void switchToFriendRequestView(BaseUIInputData request) throws SQLException;
    void switchToAddContact(BaseUIInputData request) throws SQLException;
}
