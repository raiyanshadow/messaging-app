package use_case.search_contact;

import java.sql.SQLException;

public interface SearchContactInputBoundary {
    void execute(SearchContactInputData inputData) throws SQLException;
}
