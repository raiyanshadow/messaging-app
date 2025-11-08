package app;

import io.github.cdimascio.dotenv.Dotenv;
import java.sql.*;

public class Main {
        public static void main(String[] args) {
            Dotenv dotenv = Dotenv.configure()
                    .directory("./assets")
                    .filename("env")
                    .load();

            String url = dotenv.get("DB_URL");
            String user = dotenv.get("DB_USER");
            String password = dotenv.get("DB_PASSWORD");

            try (Connection conn = DriverManager.getConnection(url, user, password)) {
                System.out.println("Success");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
}
