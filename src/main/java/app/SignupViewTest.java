package app;

import SendBirdAPI.SendbirdUserCreator;
import data_access.DBConnectionFactory;
import data_access.DBUserDataAccessObject;
import entity.User;
import interface_adapter.ViewManagerModel;
import interface_adapter.signup.SignupViewModel;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupPresenter;
import interface_adapter.login.LoginViewModel;
import use_case.signup.SignupInputBoundary;
import use_case.signup.SignupInteractor;
import view.SignupView;
import view.LoginView;
import io.github.cdimascio.dotenv.Dotenv;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class SignupViewTest {

    public static void main(String[] args) throws Exception {

        // Load environment variables from .env
        Dotenv dotenv = Dotenv.configure()
                .directory("./assets")
                .filename("env")
                .load();

        String sendbirdAppId = dotenv.get("MSG_APP_ID");
        String sendbirdToken = dotenv.get("MSG_TOKEN");
        System.out.println("Sendbird token: " + sendbirdToken);

        // JFrame setup
        JFrame frame = new JFrame("Signup Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        CardLayout cardLayout = new CardLayout();
        JPanel views = new JPanel(cardLayout);

        // Database connection
        Connection conn = DBConnectionFactory.createConnection();
        DBUserDataAccessObject userDAO = new DBUserDataAccessObject(conn);

        // Sendbird API creator
        SendbirdUserCreator sendbirdUserCreator = new SendbirdUserCreator(sendbirdAppId);

        // View manager
        ViewManagerModel viewManagerModel = new ViewManagerModel();

        // ViewModels
        SignupViewModel signupViewModel = new SignupViewModel();
        LoginViewModel loginViewModel = new LoginViewModel();

        // Presenter
        SignupPresenter presenter =
                new SignupPresenter(viewManagerModel, signupViewModel, loginViewModel);

        // Interactor
        SignupInputBoundary interactor =
                new SignupInteractor(userDAO, presenter, sendbirdUserCreator);

        // Controller
        SignupController signupController = new SignupController(interactor);

        // Views
        SignupView signupView = new SignupView(signupViewModel);
        signupView.setSignupController(signupController);

        LoginView loginView = new LoginView(loginViewModel);

        // Add views to CardLayout
        views.add(signupView, signupViewModel.getViewName());
        views.add(loginView, loginViewModel.getViewName());

        // Listen for view changes
        viewManagerModel.addPropertyChangeListener(evt -> {
            String newView = (String) evt.getNewValue();
            if (newView != null && !newView.isEmpty()) {
                cardLayout.show(views, newView);
            }
        });

        // Show signup first
        cardLayout.show(views, signupViewModel.getViewName());

        frame.add(views, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }
}
