package app;

import data_access.DBConnectionFactory;
import data_access.DBUserDataAccessObject;

import interface_adapter.ViewManagerModel;
import interface_adapter.signup.SignupViewModel;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupPresenter;
import interface_adapter.login.LoginViewModel;

import use_case.signup.SignupInputBoundary;
import use_case.signup.SignupInteractor;

import view.SignupView;
import view.LoginView;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class SignupViewTest {

    public static void main(String[] args) throws Exception {

        JFrame frame = new JFrame("Signup Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        CardLayout cardLayout = new CardLayout();
        JPanel views = new JPanel(cardLayout);

        Connection conn = DBConnectionFactory.createConnection();
        DBUserDataAccessObject userDAO = new DBUserDataAccessObject(conn);

        ViewManagerModel viewManagerModel = new ViewManagerModel();

        SignupViewModel signupViewModel = new SignupViewModel();
        LoginViewModel loginViewModel = new LoginViewModel();

        SignupPresenter presenter =
                new SignupPresenter(viewManagerModel, signupViewModel, loginViewModel);

        SignupInputBoundary interactor =
                new SignupInteractor(userDAO, presenter);

        SignupController signupController = new SignupController(interactor);

        SignupView signupView = new SignupView(signupViewModel);
        signupView.setSignupController(signupController);

        LoginView loginView = new LoginView(loginViewModel);

        views.add(signupView, signupViewModel.getViewName());
        views.add(loginView, loginViewModel.getViewName());

        viewManagerModel.addPropertyChangeListener(evt -> {
            String newView = (String) evt.getNewValue();
            if (newView != null && !newView.isEmpty()) {
                cardLayout.show(views, newView);
            }
        });

        cardLayout.show(views, signupViewModel.getViewName());

        frame.add(views, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }
}
