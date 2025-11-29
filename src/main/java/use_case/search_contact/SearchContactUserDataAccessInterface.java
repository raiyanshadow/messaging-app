package use_case.search_contact;

import entity.User;

import java.sql.SQLException;
import java.util.List;

public interface SearchContactUserDataAccessInterface {
    List<User> searchUsers(String keyword) throws SQLException;
}
