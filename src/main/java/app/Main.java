package app;

import javax.swing.*;
import java.sql.*;

public class Main {
        public static void main(String[] args) throws SQLException {
            AppBuilder appBuilder = new AppBuilder();
            JFrame application = appBuilder
                    .buildPreLogin()
                    .build();

            application.pack();
            application.setLocationRelativeTo(null);
            application.setVisible(true);
        }
}
