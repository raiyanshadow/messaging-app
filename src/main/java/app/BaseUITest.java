package app;

import SendBirdAPI.ChannelCreator;
import SendBirdAPI.MessageSender;
import data_access.DBChatChannelDataAccessObject;
import data_access.DbConnectionFactory;
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
import interface_adapter.login.LoginViewModel;
import interface_adapter.logout.LogoutController;
import interface_adapter.logout.LogoutPresenter;
import interface_adapter.logout.LogoutViewModel;
import interface_adapter.profile_edit.ProfileEditPresenter;
import interface_adapter.profile_edit.ProfileEditViewModel;
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
import use_case.logout.LogoutInteractor;
import use_case.logout.LogoutOutputBoundary;
import use_case.send_message.SendMessageInteractor;
import use_case.send_message.SendMessageOutputBoundary;
import use_case.update_chat_channel.UpdateChatChannelInteractor;
import use_case.update_chat_channel.UpdateChatChannelOutputBoundary;
import view.*;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;


import data_access.*;
import interface_adapter.chat_channel.ChatChannelViewModel;
import view.BaseUIView;
import view.CreateChatView;

public class BaseUITest {

    public static void main(String[] args) throws SQLException {
        //create needed instances
        baseUIViewModel baseUIViewModel = new baseUIViewModel("baseUIView");
        ChatChannelViewModel chatChannelViewModel = new ChatChannelViewModel("chatChannelViewModel");
        AddChatChannelViewModel addChatChannelViewModel = new AddChatChannelViewModel("addChatChannelViewModel");
        AddContactViewModel addContactViewModel = new AddContactViewModel();
        FriendRequestViewModel friendRequestViewModel = new FriendRequestViewModel();
        LogoutViewModel logoutViewModel = new LogoutViewModel();
        LoginViewModel loginViewModel = new LoginViewModel();
        ProfileEditViewModel profileEditViewModel = new ProfileEditViewModel();
        AppBuilder appBuilder = new AppBuilder();
        MessageViewModel messageViewModel = new MessageViewModel();
        final Dotenv dotenv = Dotenv.configure()
                .directory("./assets")
                .filename("env")
                .load();
        ApiClient defaultClient = Configuration.getDefaultApiClient().setBasePath(
                "https://api-" + dotenv.get("MSG_APP_ID") + ".sendbird.com"
        );
        MessageSender messageSender = new MessageSender(defaultClient);
        ChannelCreator channelCreator = new ChannelCreator(defaultClient);
        UpdateChatChannelViewModel updateChatChannelViewModel = new UpdateChatChannelViewModel();
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        Connection conn = new DbConnectionFactory().createConnection();
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
                viewManagerModel, baseUIViewModel, sessionManager);
        AddChatChannelPresenter addChatChannelPresenter = new AddChatChannelPresenter(chatChannelViewModel,
                addChatChannelViewModel, viewManagerModel);
        ProfileEditPresenter profileEditPresenter = new ProfileEditPresenter(profileEditViewModel);

        baseUIPresenter baseUIPresenter = new baseUIPresenter(baseUIViewModel, viewManagerModel, addChatChannelViewModel, friendRequestViewModel, addContactViewModel, profileEditViewModel);
        SendMessageOutputBoundary sendMessagePresenter = new ChatChannelPresenter(messageViewModel);
        UpdateChatChannelOutputBoundary updateChatChannelPresenter = new UpdateChatChannelPresenter(updateChatChannelViewModel,
                sessionManager);
        LogoutOutputBoundary logoutPresenter = new LogoutPresenter(logoutViewModel, viewManagerModel, loginViewModel, sessionManager, appBuilder);


        AddContactInteractor addContactInteractor = new AddContactInteractor(dbUserDataAccessObject, dbContactDataAccessObject, addContactPresenter, sessionManager);
        AddChatChannelInteractor addChatChannelInteractor = new AddChatChannelInteractor(addChatChannelPresenter,
                dbChatChannelDataAccessObject, dbUserDataAccessObject, sessionManager, channelCreator);
        BaseUIInteractor baseUIInteractor = new BaseUIInteractor(baseUIPresenter, dbChatChannelDataAccessObject,
                dbUserDataAccessObject, sessionManager, dbContactDataAccessObject);
        FriendRequestInteractor friendRequestInteractor = new FriendRequestInteractor(dbContactDataAccessObject,
                friendRequestPresenter, sessionManager);
        SendMessageInteractor sendMessageInteractor = new SendMessageInteractor(sendMessagePresenter, dbUserDataAccessObject,
                messageDataAccessObject, sessionManager, messageSender);
        UpdateChatChannelInteractor updateChatChannelInteractor = new UpdateChatChannelInteractor(
                dbChatChannelDataAccessObject, updateChatChannelPresenter);
        LogoutInteractor logoutInteractor = new LogoutInteractor(logoutPresenter);

        AddContactController addContactController = new AddContactController(addContactInteractor);
        AddChatChannelController addChatChannelController = new AddChatChannelController(addChatChannelInteractor);
        baseUIController baseUIController = new baseUIController(baseUIInteractor);
        FriendRequestController friendRequestController = new FriendRequestController(friendRequestInteractor);
        SendMessageController sendMessageController = new SendMessageController(sendMessageInteractor);
        UpdateChatChannelController updateChatChannelController = new UpdateChatChannelController(updateChatChannelInteractor);
        LogoutController logoutController = new LogoutController(logoutInteractor);

        ViewManager viewManager = new ViewManager(viewManagerModel);

        // Create actual base view and register it
        AddContactView addContactView = new AddContactView(addContactViewModel, viewManagerModel, sessionManager, baseUIController);
        addContactView.setAddContactController(addContactController);
        BaseUIView baseUIView = new BaseUIView(baseUIViewModel, baseUIController, updateChatChannelViewModel,
                chatChannelViewModel, viewManagerModel, sessionManager, viewManager, sendMessageController,
                updateChatChannelController, logoutController);
        CreateChatView addChatChannelView = new CreateChatView(sessionManager, addChatChannelController,
                baseUIViewModel, baseUIController, addChatChannelViewModel);
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
