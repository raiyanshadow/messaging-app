package app;

import SendBirdAPI.MessageSender;
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
import interface_adapter.chat_channel.*;
import interface_adapter.friend_request.FriendRequestController;
import interface_adapter.friend_request.FriendRequestPresenter;
import interface_adapter.friend_request.FriendRequestViewModel;
import interface_adapter.update_chat_channel.UpdateChatChannelController;
import interface_adapter.update_chat_channel.UpdateChatChannelPresenter;
import interface_adapter.update_chat_channel.UpdateChatChannelViewModel;
import io.github.cdimascio.dotenv.Dotenv;
import org.sendbird.client.ApiClient;
import org.sendbird.client.Configuration;
import session.SessionManager;
import use_case.add_chat_channel.AddChatChannelInteractor;
import use_case.add_contact.AddContactInteractor;
import use_case.baseUI.BaseUIInteractor;
import use_case.friend_request.FriendRequestInteractor;
import use_case.send_message.SendMessageInteractor;
import use_case.send_message.SendMessageOutputBoundary;
import use_case.update_chat_channel.UpdateChatChannelInteractor;
import use_case.update_chat_channel.UpdateChatChannelOutputBoundary;
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
        //create needed instances
        baseUIViewModel baseUIViewModel = new baseUIViewModel("baseUIView");
        ChatChannelViewModel chatChannelViewModel = new ChatChannelViewModel("chatChannelViewModel");
        AddChatChannelViewModel addChatChannelViewModel = new AddChatChannelViewModel("addChatChannelViewModel");
        AddContactViewModel addContactViewModel = new AddContactViewModel();
        FriendRequestViewModel friendRequestViewModel = new FriendRequestViewModel();
        MessageViewModel messageViewModel = new MessageViewModel();
        final Dotenv dotenv = Dotenv.configure()
                .directory("./assets")
                .filename("env")
                .load();
        ApiClient defaultClient = Configuration.getDefaultApiClient().setBasePath(
                "https://api-" + dotenv.get("MSG_APP_ID") + ".sendbird.com"
        );
        MessageSender messageSender = new MessageSender(defaultClient);
        UpdateChatChannelViewModel updateChatChannelViewModel = new UpdateChatChannelViewModel();
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        Connection conn = new DBConnectionFactory().createConnection();
        DBChatChannelDataAccessObject dbChatChannelDataAccessObject = new DBChatChannelDataAccessObject(conn);
        DBUserDataAccessObject dbUserDataAccessObject = new DBUserDataAccessObject(conn);
        DBContactDataAccessObject dbContactDataAccessObject = new DBContactDataAccessObject(conn);
        DBMessageDataAccessObject messageDataAccessObject = new DBMessageDataAccessObject(conn);
        User user = dbUserDataAccessObject.getUserFromID(2);
        dbContactDataAccessObject.updateUserContacts(user, user.getContacts());
        dbContactDataAccessObject.updateUserFriendRequests(user, user.getFriendRequests());
        SessionManager sessionManager = new SessionManager(user, true);
        //create needed dependencies
        AddContactPresenter addContactPresenter = new AddContactPresenter(addContactViewModel, viewManagerModel);
        FriendRequestPresenter friendRequestPresenter = new FriendRequestPresenter(friendRequestViewModel,
                viewManagerModel, baseUIViewModel);
        AddChatChannelPresenter addChatChannelPresenter = new AddChatChannelPresenter(chatChannelViewModel,
                addChatChannelViewModel, viewManagerModel);

        baseUIPresenter baseUIPresenter = new baseUIPresenter(baseUIViewModel, viewManagerModel, addChatChannelViewModel, friendRequestViewModel, addContactViewModel);
        SendMessageOutputBoundary sendMessagePresenter = new ChatChannelPresenter(messageViewModel);
        UpdateChatChannelOutputBoundary updateChatChannelPresenter = new UpdateChatChannelPresenter(updateChatChannelViewModel,
                sessionManager);


        AddContactInteractor addContactInteractor = new AddContactInteractor(dbUserDataAccessObject, dbContactDataAccessObject, addContactPresenter);
        AddChatChannelInteractor addChatChannelInteractor = new AddChatChannelInteractor(addChatChannelPresenter,
                dbChatChannelDataAccessObject, dbUserDataAccessObject, sessionManager);
        BaseUIInteractor baseUIInteractor = new BaseUIInteractor(baseUIPresenter, dbChatChannelDataAccessObject,
                dbUserDataAccessObject, sessionManager);
        FriendRequestInteractor friendRequestInteractor = new FriendRequestInteractor(dbContactDataAccessObject,
                friendRequestPresenter);
        SendMessageInteractor sendMessageInteractor = new SendMessageInteractor(sendMessagePresenter, dbUserDataAccessObject,
                messageDataAccessObject, sessionManager, messageSender);
        UpdateChatChannelInteractor updateChatChannelInteractor = new UpdateChatChannelInteractor(
                dbChatChannelDataAccessObject, updateChatChannelPresenter);

        AddContactController addContactController = new AddContactController(addContactInteractor);
        AddChatChannelController addChatChannelController = new AddChatChannelController(addChatChannelInteractor);
        baseUIController baseUIController = new baseUIController(baseUIInteractor);
        FriendRequestController friendRequestController = new FriendRequestController(friendRequestInteractor);
        SendMessageController sendMessageController = new SendMessageController(sendMessageInteractor);
        UpdateChatChannelController updateChatChannelController = new UpdateChatChannelController(updateChatChannelInteractor);

        ViewManager viewManager = new ViewManager(viewManagerModel);

        // Create actual base view and register it
        AddContactView addContactView = new AddContactView(addContactViewModel, viewManagerModel, sessionManager, baseUIController);
        addContactView.setAddContactController(addContactController);
        BaseUIView baseUIView = new BaseUIView(baseUIViewModel, baseUIController, updateChatChannelViewModel,
                chatChannelViewModel, viewManagerModel, sessionManager, viewManager, sendMessageController,
                updateChatChannelController);
        CreateChatView addChatChannelView = new CreateChatView(sessionManager, addChatChannelController,
                baseUIViewModel, baseUIController);
        FriendRequestView friendRequestView = new FriendRequestView(friendRequestViewModel, viewManagerModel,
                sessionManager, baseUIController);

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
