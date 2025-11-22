package app;

import data_access.DBConnectionFactory;
import data_access.DBContactDataAccessObject;
import data_access.DBUserDataAccessObject;
import entity.Contact;
import entity.User;
import interface_adapter.ViewManagerModel;
import interface_adapter.add_contact.AddContactController;
import interface_adapter.add_contact.AddContactPresenter;
import interface_adapter.add_contact.AddContactViewModel;
import interface_adapter.base_UI.baseUIViewModel;
import interface_adapter.friend_request.FriendRequestController;
import interface_adapter.friend_request.FriendRequestPresenter;
import interface_adapter.friend_request.FriendRequestViewModel;
import io.github.cdimascio.dotenv.Dotenv;
import session.SessionManager;
import use_case.add_contact.AddContactInputBoundary;
import use_case.add_contact.AddContactInteractor;
import use_case.friend_request.FriendRequestInputBoundary;
import use_case.friend_request.FriendRequestInteractor;
import view.AddContactView;
import view.FriendRequestView;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;

public class FriendRequestViewTest {
    public static void main(String[] args) throws SQLException {
        JFrame frame = new JFrame("friend request testing");

        // DAO
        Connection conn = DBConnectionFactory.createConnection();
        DBContactDataAccessObject dummyDAO = new DBContactDataAccessObject(conn);
        DBUserDataAccessObject dummyDAO2 = new DBUserDataAccessObject(conn);


        // testing that the methods in DBContactDAO work
        // dummyDAO.acceptRequest(dummyDAO2.getUserFromName("Alice"), "ss");

        // testing if it's possible to get all of userid#1's contacts
        User temp = dummyDAO2.getUserFromID(1);
        dummyDAO.updateUserContacts(temp, temp.getContacts());
        dummyDAO.updateUserFriendRequests(temp, temp.getFriendRequests());
        System.out.println(temp.getUsername());
        for (Contact contact: temp.getContacts()) {
            System.out.println(contact.getContact().getUserID());
        }


        FriendRequestViewModel viewModel = new FriendRequestViewModel();
        ViewManagerModel viewManager = new ViewManagerModel();

        FriendRequestPresenter presenter = new FriendRequestPresenter(viewModel, viewManager);
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        baseUIViewModel baseUIViewModel = new baseUIViewModel("baseUIView");

        SessionManager sessionManager = new SessionManager(temp, true);
        FriendRequestView view = new FriendRequestView(viewModel, baseUIViewModel, viewManagerModel, sessionManager);

        FriendRequestInputBoundary interactor = new FriendRequestInteractor(dummyDAO, presenter);
        FriendRequestController controller = new FriendRequestController(interactor);
        view.setFriendRequestController(controller);

        frame.add(view, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }

}
