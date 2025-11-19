package data_access;

import entity.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDataAccessObject {
    boolean existsByName(String username) throws SQLException;
    User getUserFromID(int UserID) throws SQLException;
    List<User> getAllUsers() throws SQLException;
    public void save(User user) throws SQLException;
}
