package app;

import data_access.DBChatChannelDataAccessObject;
import data_access.DBConnectionFactory;
import data_access.DBUserDataAccessObject;
import entity.User;
import interface_adapter.ViewManagerModel;
import interface_adapter.add_chat_channel.AddChatChannelController;
import interface_adapter.add_chat_channel.AddChatChannelPresenter;
import interface_adapter.add_chat_channel.AddChatChannelViewModel;
import interface_adapter.base_UI.baseUIController;
import interface_adapter.base_UI.baseUIPresenter;
import interface_adapter.base_UI.baseUIViewModel;
import interface_adapter.chat_channel.ChatChannelViewModel;
import interface_adapter.friend_request.FriendRequestViewModel;
import session.SessionManager;
import use_case.add_chat_channel.AddChatChannelInteractor;
import use_case.baseUI.BaseUIInteractor;
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
        SessionManager sessionManager = new SessionManager(testuser, true);

        //create needed instances
        baseUIViewModel baseUIViewModel = new baseUIViewModel("baseUIView");
        FriendRequestViewModel friendRequestViewModel = new FriendRequestViewModel();
        ChatChannelViewModel chatChannelViewModel = new ChatChannelViewModel("chatChannelViewModel");
        AddChatChannelViewModel addChatChannelViewModel = new AddChatChannelViewModel("addChatChannelViewModel");
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        Connection conn = new DBConnectionFactory().createConnection();
        DBChatChannelDataAccessObject dbChatChannelDataAccessObject = new DBChatChannelDataAccessObject(conn);
        DBUserDataAccessObject dbUserDataAccessObject = new DBUserDataAccessObject(conn);

        //create needed dependencies
        AddChatChannelPresenter addChatChannelPresenter = new AddChatChannelPresenter(chatChannelViewModel,
                addChatChannelViewModel, viewManagerModel);
        baseUIPresenter baseUIPresenter = new baseUIPresenter(baseUIViewModel, viewManagerModel, addChatChannelViewModel,
                friendRequestViewModel);

        AddChatChannelInteractor addChatChannelInteractor = new AddChatChannelInteractor(addChatChannelPresenter,
                dbChatChannelDataAccessObject, dbUserDataAccessObject, sessionManager);
        BaseUIInteractor baseUIInteractor = new BaseUIInteractor(baseUIPresenter, dbChatChannelDataAccessObject,
                dbUserDataAccessObject, sessionManager);

        AddChatChannelController addChatChannelController = new AddChatChannelController(addChatChannelInteractor);
        baseUIController baseUIController = new baseUIController(baseUIInteractor);

        ViewManager viewManager = new ViewManager(viewManagerModel);

        // Create actual base view and register it
        BaseUIView baseUIView = new BaseUIView(baseUIViewModel, baseUIController);
        FriendRequestView friendRequestView = new FriendRequestView(friendRequestViewModel, baseUIViewModel,
                viewManagerModel);
        CreateChatView addChatChannelView = new CreateChatView(sessionManager, addChatChannelController,
                baseUIViewModel, baseUIController);

        viewManager.addView(friendRequestView, friendRequestViewModel.getViewName());
        viewManager.addView(baseUIView, baseUIViewModel.getViewName());
        viewManager.addView(addChatChannelView, addChatChannelViewModel.getViewName());
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
