package app;

import data_access.DBChatChannelDataAccessObject;
import data_access.DBConnectionFactory;
import data_access.DBContactDataAccessObject;
import data_access.DBUserDataAccessObject;
import entity.Contact;
import entity.User;
import interface_adapter.ViewManagerModel;
import interface_adapter.add_chat_channel.AddChatChannelController;
import interface_adapter.add_chat_channel.AddChatChannelPresenter;
import interface_adapter.add_chat_channel.AddChatChannelViewModel;
import interface_adapter.add_contact.AddContactController;
import interface_adapter.add_contact.AddContactPresenter;
import interface_adapter.add_contact.AddContactViewModel;
import interface_adapter.base_UI.baseUIController;
import interface_adapter.base_UI.baseUIPresenter;
import interface_adapter.base_UI.baseUIViewModel;
import interface_adapter.chat_channel.ChatChannelViewModel;
import interface_adapter.friend_request.FriendRequestController;
import interface_adapter.friend_request.FriendRequestPresenter;
import interface_adapter.friend_request.FriendRequestViewModel;
import io.github.cdimascio.dotenv.Dotenv;
import session.SessionManager;
import use_case.add_chat_channel.AddChatChannelInteractor;
import use_case.add_contact.AddContactInputBoundary;
import use_case.add_contact.AddContactInteractor;
import use_case.baseUI.BaseUIInteractor;
import use_case.friend_request.FriendRequestInputBoundary;
import use_case.friend_request.FriendRequestInteractor;
import view.*;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;

public class FriendRequestViewTest {
    public static void main(String[] args) throws SQLException {
        JFrame frame = new JFrame("friend request testing");

        // DAO
        Connection conn = DBConnectionFactory.createConnection();
        DBContactDataAccessObject dummyContactDAO = new DBContactDataAccessObject(conn);
        DBUserDataAccessObject dummyUserDAO = new DBUserDataAccessObject(conn);
        DBChatChannelDataAccessObject dbChatChannelDataAccessObject = new DBChatChannelDataAccessObject(conn);


        // testing that the methods in DBContactDAO work
        // dummyDAO.acceptRequest(dummyDAO2.getUserFromName("Alice"), "ss");

        // testing if it's possible to get all of userid#1's contacts
        User temp = dummyUserDAO.getUserFromID(1);
        dummyContactDAO.updateUserContacts(temp, temp.getContacts());
        dummyContactDAO.updateUserFriendRequests(temp, temp.getFriendRequests());
        System.out.println(temp.getUsername());
        SessionManager sessionManager = new SessionManager(temp, true);

        FriendRequestViewModel friendRequestViewModel = new FriendRequestViewModel();
        ViewManagerModel viewManagerModel = new ViewManagerModel();


        baseUIViewModel baseUIViewModel = new baseUIViewModel("baseUIView");
        ChatChannelViewModel chatChannelViewModel = new ChatChannelViewModel("chatChannelViewModel");
        AddChatChannelViewModel addChatChannelViewModel = new AddChatChannelViewModel("addChatChannelViewModel");
        AddChatChannelPresenter addChatChannelPresenter = new AddChatChannelPresenter(chatChannelViewModel,
                addChatChannelViewModel, viewManagerModel);
        baseUIPresenter baseUIPresenter = new baseUIPresenter(baseUIViewModel, viewManagerModel, addChatChannelViewModel, friendRequestViewModel);

        AddChatChannelInteractor addChatChannelInteractor = new AddChatChannelInteractor(addChatChannelPresenter, dbChatChannelDataAccessObject, dummyUserDAO, sessionManager);
        BaseUIInteractor baseUIInteractor = new BaseUIInteractor(baseUIPresenter, dbChatChannelDataAccessObject, dummyUserDAO, sessionManager);

        AddChatChannelController addChatChannelController = new AddChatChannelController(addChatChannelInteractor);
        baseUIController baseUIController = new baseUIController(baseUIInteractor);
        ViewManager viewManager = new ViewManager(viewManagerModel);



        FriendRequestPresenter presenter = new FriendRequestPresenter(friendRequestViewModel, viewManagerModel, baseUIViewModel);
        FriendRequestView friendRequestView = new FriendRequestView(friendRequestViewModel, viewManagerModel, sessionManager, baseUIController);

        FriendRequestInputBoundary interactor = new FriendRequestInteractor(dummyContactDAO, presenter);
        FriendRequestController controller = new FriendRequestController(interactor);
        friendRequestView.setFriendRequestController(controller);

        BaseUIView baseUIView = new BaseUIView(baseUIViewModel, baseUIController);
        CreateChatView addChatChannelView = new CreateChatView(sessionManager, addChatChannelController, baseUIViewModel, baseUIController);

        viewManager.addView(friendRequestView, friendRequestViewModel.getViewName());
        viewManager.addView(baseUIView, baseUIViewModel.getViewName());
        viewManager.addView(addChatChannelView, addChatChannelViewModel.getViewName());


        frame.add(viewManager);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
