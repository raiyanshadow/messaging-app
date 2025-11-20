package app;

import data_access.DBConnectionFactory;
import data_access.DBUserDataAccessObject;
import data_access.UserDataAccessObject;
import interface_adapter.ViewManagerModel;
import interface_adapter.add_contact.AddContactController;
import interface_adapter.add_contact.AddContactPresenter;
import interface_adapter.add_contact.AddContactViewModel;
import io.github.cdimascio.dotenv.Dotenv;
import io.opencensus.stats.ViewManager;
import use_case.add_contact.*;
import use_case.signup.SignupOutputBoundary;
import use_case.signup.SignupOutputData;
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

        // DAO
        Connection conn = DBConnectionFactory.createConnection();
        DBUserDataAccessObject dummyDAO = new DBUserDataAccessObject(conn);

        AddContactOutputBoundary dummyPresenter = new AddContactOutputBoundary() {
            @Override
            public void prepareFailView(String message) {
                JOptionPane.showMessageDialog(frame, message, "add contact Failed", JOptionPane.ERROR_MESSAGE);
            }


            @Override
            public void prepareSuccessView(AddContactOutputData outputData) {
                JOptionPane.showMessageDialog(frame, "add contact successful");
            }

            @Override
            public void switchToContactsView() {
                // Close signup frame and show login view
                frame.dispose();

                // For testing, just show a simple login frame
                JFrame loginFrame = new JFrame("Contacts View");
                loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                loginFrame.setSize(300, 200);
                loginFrame.add(new JLabel("Contacts Screen", SwingConstants.CENTER), BorderLayout.CENTER);
                loginFrame.setVisible(true);
            }
        };

        AddContactView view = new AddContactView(viewModel);

        AddContactInputBoundary interactor = new AddContactInteractor(dummyDAO, dummyPresenter);
        AddContactController controller = new AddContactController(interactor);
        view.setAddContactController(controller);

        frame.add(view, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }
}
