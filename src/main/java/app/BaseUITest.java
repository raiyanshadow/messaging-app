package app;

import data_access.DBChatChannelDataAccessObject;
import data_access.DBConnectionFactory;
import data_access.DBUserDataAccessObject;
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
import interface_adapter.update_chat_channel.UpdateChatChannelViewModel;
import session.SessionManager;
import use_case.add_chat_channel.AddChatChannelInteractor;
import use_case.add_contact.AddContactInteractor;
import use_case.baseUI.BaseUIInteractor;
import use_case.friend_request.FriendRequestInteractor;
import use_case.friend_request.FriendRequestUserDataAccessInterface;
import view.*;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;


import data_access.*;
        import entity.User;
import interface_adapter.ViewManagerModel;
import interface_adapter.add_chat_channel.AddChatChannelController;
import interface_adapter.add_chat_channel.AddChatChannelPresenter;
import interface_adapter.add_chat_channel.AddChatChannelViewModel;
import interface_adapter.base_UI.baseUIController;
import interface_adapter.base_UI.baseUIPresenter;
import interface_adapter.base_UI.baseUIViewModel;
import interface_adapter.chat_channel.ChatChannelViewModel;
import session.SessionManager;
import use_case.add_chat_channel.AddChatChannelInteractor;
import use_case.baseUI.BaseUIInteractor;
import view.BaseUIView;
import view.CreateChatView;

import javax.swing.*;
        import java.sql.Connection;
import java.sql.SQLException;

public class BaseUITest {

    public static void main(String[] args) throws SQLException {
        //create fake user for login
        User testuser = new User(24, "Koorosh","paassy","English");
        User testuser2 = new User(233, "salam", "pass", "English");
        testuser.addContact(testuser2);

        SessionManager sessionManager = new SessionManager(testuser, true);

        //create needed instances
        baseUIViewModel baseUIViewModel = new baseUIViewModel("baseUIView");
        UpdateChatChannelViewModel updateChatChannelViewModel = new UpdateChatChannelViewModel();
        FriendRequestViewModel friendRequestViewModel = new FriendRequestViewModel();
        ChatChannelViewModel chatChannelViewModel = new ChatChannelViewModel("chatChannelViewModel");
        AddChatChannelViewModel addChatChannelViewModel = new AddChatChannelViewModel("addChatChannelViewModel");
        AddContactViewModel addContactViewModel = new AddContactViewModel();
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        Connection conn = new DBConnectionFactory().createConnection();
        DBChatChannelDataAccessObject dbChatChannelDataAccessObject = new DBChatChannelDataAccessObject(conn);
        DBUserDataAccessObject dbUserDataAccessObject = new DBUserDataAccessObject(conn);
        DBContactDataAccessObject dbContactDataAccessObject = new DBContactDataAccessObject(conn);

        //create needed dependencies
        AddContactPresenter addContactPresenter = new AddContactPresenter(addContactViewModel, viewManagerModel);
        FriendRequestPresenter friendRequestPresenter = new FriendRequestPresenter(friendRequestViewModel,
                viewManagerModel);
        AddChatChannelPresenter addChatChannelPresenter = new AddChatChannelPresenter(chatChannelViewModel,
                addChatChannelViewModel, viewManagerModel);
        baseUIPresenter baseUIPresenter = new baseUIPresenter(baseUIViewModel, viewManagerModel, addChatChannelViewModel,
                friendRequestViewModel, addContactViewModel);

        AddContactInteractor addContactInteractor = new AddContactInteractor(dbUserDataAccessObject, addContactPresenter);
        AddChatChannelInteractor addChatChannelInteractor = new AddChatChannelInteractor(addChatChannelPresenter,
                dbChatChannelDataAccessObject, dbUserDataAccessObject, sessionManager);
        BaseUIInteractor baseUIInteractor = new BaseUIInteractor(baseUIPresenter, dbChatChannelDataAccessObject,
                dbUserDataAccessObject, sessionManager);
        FriendRequestInteractor friendRequestInteractor = new FriendRequestInteractor(dbContactDataAccessObject,
                friendRequestPresenter);

        AddContactController addContactController = new AddContactController(addContactInteractor);
        AddChatChannelController addChatChannelController = new AddChatChannelController(addChatChannelInteractor);
        baseUIController baseUIController = new baseUIController(baseUIInteractor);
        FriendRequestController friendRequestController = new FriendRequestController(friendRequestInteractor);

        ViewManager viewManager = new ViewManager(viewManagerModel);

        // Create actual base view and register it
        AddContactView addContactView = new AddContactView(addContactViewModel, baseUIViewModel, viewManagerModel);
        addContactView.setAddContactController(addContactController);
        BaseUIView baseUIView = new BaseUIView(baseUIViewModel, baseUIController, updateChatChannelViewModel,
                chatChannelViewModel, viewManagerModel, sessionManager);
        FriendRequestView friendRequestView = new FriendRequestView(friendRequestViewModel, baseUIViewModel,
                viewManagerModel);
        friendRequestView.setFriendRequestController(friendRequestController);
        CreateChatView addChatChannelView = new CreateChatView(sessionManager, addChatChannelController,
                baseUIViewModel, baseUIController);


        viewManager.addView(baseUIView, baseUIViewModel.getViewName());
        viewManager.addView(friendRequestView, friendRequestViewModel.getViewName());
        viewManager.addView(addChatChannelView, addChatChannelViewModel.getViewName());
        viewManager.addView(addContactView, addContactViewModel.getViewName());
        // Example AddChatChannelView (when ready)
        // AddChatChannelView addView = new AddChatChannelView(...);
        // viewManager.addView(addView, addChatChannelViewModel.getViewName());

        // Put ViewManager JPanel inside a simple JFrame to test visually
        JFrame frame = new JFrame("Chat Application Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(viewManager);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Trigger UI
        baseUIController.displayUI();
    }
}
