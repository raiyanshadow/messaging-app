package data_access;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnectionFactory {

    /**
     * Establishes and returns a new connection to the PostgreSQL database.
     * @return A {@code java.sql.Connection} object representing the active database connection.
     * @throws SQLException If a database access error occurs, or the URL is null,
     */
    public static Connection createConnection() throws SQLException {
        final String url = "jdbc:postgresql://db.egwwgffidqtyqxqiuocm.supabase.co:5432/postgres";
        final String username = "postgres";
        final String password = "C69tt4we30tE";
        return DriverManager.getConnection(url, username, password);
    }
}

