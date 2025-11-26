package app;

import data_access.DBConnectionFactory;
import data_access.DBUserDataAccessObject;

import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupPresenter;
import interface_adapter.signup.SignupViewModel;
import interface_adapter.login.LoginViewModel;
import interface_adapter.ViewManagerModel;

import use_case.signup.*;

import view.SignupView;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;

public class SignupViewTest {

    public static void main(String[] args) throws SQLException {

        // Window
        JFrame frame = new JFrame("Signup Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // DAO
        Connection conn = DBConnectionFactory.createConnection();
        DBUserDataAccessObject userDAO = new DBUserDataAccessObject(conn);

        // View Manager
        ViewManagerModel viewManagerModel = new ViewManagerModel();

        // ViewModels
        SignupViewModel signupViewModel = new SignupViewModel();
        LoginViewModel loginViewModel = new LoginViewModel();

        // REAL PRESENTER
        SignupPresenter signupPresenter =
                new SignupPresenter(viewManagerModel, signupViewModel, loginViewModel);

        // REAL Interactor
        SignupInputBoundary interactor =
                new SignupInteractor(userDAO, signupPresenter);

        // Controller
        SignupController signupController = new SignupController(interactor);

        // View
        SignupView signupView = new SignupView(signupViewModel);
        signupView.setSignupController(signupController);

        // Show signup
        frame.add(signupView, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }
}
