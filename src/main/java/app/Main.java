package app;

import java.sql.SQLException;

import javax.swing.JFrame;

/**
 * Main application.
 */
public class Main {
    /**
     * Main application method.
     * @param args Standard Java arguments.
     * @throws SQLException whenever a database or API read/write fails.
     */
    public static void main(String[] args) throws SQLException {
        final AppBuilder appBuilder = new AppBuilder();
        final JFrame application = appBuilder
                .buildPreLogin()
                .build();

        application.pack();
        application.setLocationRelativeTo(null);
        application.setVisible(true);
    }
}
