package app;

import data_access.DBUserDataAccessObject;
import interface_adapter.ViewManagerModel;
import interface_adapter.add_contact.AddContactController;
import interface_adapter.add_contact.AddContactPresenter;
import interface_adapter.add_contact.AddContactViewModel;
import io.github.cdimascio.dotenv.Dotenv;
import io.opencensus.stats.ViewManager;
import use_case.add_contact.AddContactInputBoundary;
import use_case.add_contact.AddContactInteractor;
import use_case.add_contact.AddContactUserDataAccessInterface;
import view.AddContactView;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AddContactViewTest {
    public static void main(String[] args) throws SQLException {
        JFrame frame = new JFrame("add contact testing");


        AddContactViewModel viewModel = new AddContactViewModel();
        ViewManagerModel viewManager = new ViewManagerModel();

        AddContactPresenter presenter = new AddContactPresenter(viewModel, viewManager);

        Dotenv dotenv = Dotenv.configure()
                .directory("./assets")
                .filename("env")
                .load();

        String url = dotenv.get("DB_URL");
        String user = dotenv.get("DB_USER");
        String password = dotenv.get("DB_PASSWORD");

        // Connection connection = DriverManager.getConnection(url, user, password);

        // DBUserDataAccessObject gateway = new DBUserDataAccessObject(connection);
        // AddContactInputBoundary interactor = new AddContactInteractor((AddContactUserDataAccessInterface) gateway, presenter);

        // AddContactController controller = new AddContactController(interactor);

        AddContactView view = new AddContactView(viewModel);

        frame.add(view, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }
}
