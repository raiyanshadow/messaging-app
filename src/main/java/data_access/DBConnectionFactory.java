package data_access;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


// Use this function to create a connection to the DB in order to use DBAccess Objects
//Simply: Connection conn = DBConnectionFactory.createConnection()
// (make sure to try, except catch in case sql fetch fails)
public class DBConnectionFactory {
    public static Connection createConnection() throws SQLException {
        String url = "jdbc:postgresql://db.egwwgffidqtyqxqiuocm.supabase.co:5432/postgres";
        String username = "postgres";
        String password = "C69tt4we30tE";
        return DriverManager.getConnection(url, username, password);
    }
}

