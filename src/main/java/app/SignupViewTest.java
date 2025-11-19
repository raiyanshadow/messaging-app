package app;

import data_access.DBConnectionFactory;
import data_access.DBUserDataAccessObject;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupViewModel;
import use_case.signup.*;
import view.SignupView;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;

public class SignupViewTest {
    public static void main(String[] args) throws SQLException {

        JFrame frame = new JFrame("Signup Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // DAO
        Connection conn = DBConnectionFactory.createConnection();
        DBUserDataAccessObject dummyDAO = new DBUserDataAccessObject(conn);

        // ViewModel
        SignupViewModel viewModel = new SignupViewModel();

        // Presenter with GUI callback
        SignupOutputBoundary dummyPresenter = new SignupOutputBoundary() {
            @Override
            public void prepareFailView(String message) {
                JOptionPane.showMessageDialog(frame, message, "Signup Failed", JOptionPane.ERROR_MESSAGE);
            }

            @Override
            public void prepareSuccessView(SignupOutputData outputData) {
                JOptionPane.showMessageDialog(frame, "Signup successful for " + outputData.getUsername(), "Success", JOptionPane.INFORMATION_MESSAGE);
                switchToLoginView();
            }

            @Override
            public void switchToLoginView() {
                // Close signup frame and show login view
                frame.dispose();

                // For testing, just show a simple login frame
                JFrame loginFrame = new JFrame("Login View");
                loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                loginFrame.setSize(300, 200);
                loginFrame.add(new JLabel("Login Screen", SwingConstants.CENTER), BorderLayout.CENTER);
                loginFrame.setVisible(true);
            }
        };

        // Factory
        entity.UserFactory factory = new entity.UserFactory();

        // nteractor
        SignupInputBoundary interactor = new SignupInteractor(dummyDAO, dummyPresenter, factory);

        // Controller
        SignupController controller = new SignupController(interactor);

        // View
        SignupView view = new SignupView(viewModel);
        view.setSignupController(controller);

        // Display signup UI
        frame.add(view);
        frame.pack();
        frame.setVisible(true);
    }
}
