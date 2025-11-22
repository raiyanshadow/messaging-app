package app;

import data_access.DBConnectionFactory;
import data_access.DBUserDataAccessObject;
import data_access.UserDataAccessObject;
import entity.User;
import interface_adapter.ViewManagerModel;
import interface_adapter.add_contact.AddContactController;
import interface_adapter.add_contact.AddContactPresenter;
import interface_adapter.add_contact.AddContactViewModel;
import interface_adapter.base_UI.baseUIViewModel;

import io.github.cdimascio.dotenv.Dotenv;
import io.opencensus.stats.ViewManager;
import session.SessionManager;
import use_case.add_contact.*;
import use_case.signup.SignupOutputBoundary;
import use_case.signup.SignupOutputData;
import view.AddContactView;
import view.BaseUIView;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AddContactViewTest {
    public static void main(String[] args) throws SQLException {
        JFrame frame = new JFrame("add contact testing");

        // DAO
        Connection conn = DBConnectionFactory.createConnection();
        DBUserDataAccessObject dummyDAO = new DBUserDataAccessObject(conn);

        // testing if actually able to send request
        // dummyDAO.sendRequest(dummyDAO.getUserFromName("a"), "Bob");
        User temp = dummyDAO.getUserFromID(1);
        System.out.println(temp.getUsername());

        AddContactViewModel viewModel = new AddContactViewModel();
        ViewManagerModel viewManager = new ViewManagerModel();

        AddContactPresenter presenter = new AddContactPresenter(viewModel, viewManager);
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        baseUIViewModel baseUIViewModel = new baseUIViewModel("baseUIView");

        SessionManager sessionManager = new SessionManager(temp, true);
        AddContactView view = new AddContactView(viewModel, baseUIViewModel, viewManagerModel, sessionManager);

        AddContactInputBoundary interactor = new AddContactInteractor(dummyDAO, presenter);
        AddContactController controller = new AddContactController(interactor);
        view.setAddContactController(controller);

        frame.add(view, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }
}
