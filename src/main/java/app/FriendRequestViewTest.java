package app;

import sendbirdapi.ChannelCreator;
import sendbirdapi.MessageSender;
import data_access.*;
import entity.User;
import interface_adapter.ViewManagerModel;
import interface_adapter.add_chat_channel.AddChatChannelController;
import interface_adapter.add_chat_channel.AddChatChannelPresenter;
import interface_adapter.add_chat_channel.AddChatChannelViewModel;
import interface_adapter.add_contact.AddContactViewModel;
import interface_adapter.base_UI.baseUIController;
import interface_adapter.base_UI.baseUIPresenter;
import interface_adapter.base_UI.baseUIViewModel;
import interface_adapter.chat_channel.ChatChannelPresenter;
import interface_adapter.chat_channel.ChatChannelViewModel;
import interface_adapter.chat_channel.MessageViewModel;
import interface_adapter.chat_channel.SendMessageController;
import interface_adapter.friend_request.FriendRequestController;
import interface_adapter.friend_request.FriendRequestPresenter;
import interface_adapter.friend_request.FriendRequestViewModel;
import interface_adapter.login.LoginViewModel;
import interface_adapter.logout.LogoutController;
import interface_adapter.logout.LogoutPresenter;
import interface_adapter.logout.LogoutViewModel;
import interface_adapter.profile_edit.ProfileEditViewModel;
import interface_adapter.update_chat_channel.UpdateChatChannelController;
import interface_adapter.update_chat_channel.UpdateChatChannelPresenter;
import interface_adapter.update_chat_channel.UpdateChatChannelViewModel;
import io.github.cdimascio.dotenv.Dotenv;
import org.sendbird.client.ApiClient;
import org.sendbird.client.Configuration;
import session.SessionManager;
import use_case.add_chat_channel.AddChatChannelInteractor;
import use_case.baseUI.BaseUIInteractor;
import use_case.friend_request.FriendRequestInputBoundary;
import use_case.friend_request.FriendRequestInteractor;
import use_case.logout.LogoutInputBoundary;
import use_case.logout.LogoutInteractor;
import use_case.send_message.SendMessageInteractor;
import use_case.send_message.SendMessageOutputBoundary;
import use_case.update_chat_channel.UpdateChatChannelInteractor;
import use_case.update_chat_channel.UpdateChatChannelOutputBoundary;
import view.*;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;

public class FriendRequestViewTest {
    public static void main(String[] args) throws SQLException {
        JFrame frame = new JFrame("friend request testing");

        // DAO
        Connection conn = DbConnectionFactory.createConnection();
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

        final Dotenv dotenv = Dotenv.configure()
                .directory("./assets")
                .filename("env")
                .load();
        ApiClient defaultClient = Configuration.getDefaultApiClient().setBasePath(
                "https://api-" + dotenv.get("MSG_APP_ID") + ".sendbird.com"
        );
        MessageSender messageSender = new MessageSender(defaultClient);
        ChannelCreator channelCreator = new ChannelCreator(defaultClient);


        baseUIViewModel baseUIViewModel = new baseUIViewModel("baseUIView");
        ChatChannelViewModel chatChannelViewModel = new ChatChannelViewModel("chatChannelViewModel");
        AddChatChannelViewModel addChatChannelViewModel = new AddChatChannelViewModel("addChatChannelViewModel");
        AddContactViewModel addContactViewModel = new AddContactViewModel();
        ProfileEditViewModel profileEditViewModel = new ProfileEditViewModel();
        AddChatChannelPresenter addChatChannelPresenter = new AddChatChannelPresenter(chatChannelViewModel,
                addChatChannelViewModel, viewManagerModel);
        baseUIPresenter baseUIPresenter = new baseUIPresenter(baseUIViewModel, viewManagerModel, addChatChannelViewModel, friendRequestViewModel, addContactViewModel, profileEditViewModel);

        AddChatChannelInteractor addChatChannelInteractor = new AddChatChannelInteractor(addChatChannelPresenter, dbChatChannelDataAccessObject, dummyUserDAO, sessionManager,
                channelCreator);
        BaseUIInteractor baseUIInteractor = new BaseUIInteractor(baseUIPresenter, dbChatChannelDataAccessObject, dummyUserDAO, sessionManager,
                dummyContactDAO);

        AddChatChannelController addChatChannelController = new AddChatChannelController(addChatChannelInteractor);
        baseUIController baseUIController = new baseUIController(baseUIInteractor);
        ViewManager viewManager = new ViewManager(viewManagerModel);

        FriendRequestPresenter presenter = new FriendRequestPresenter(friendRequestViewModel, viewManagerModel, baseUIViewModel, sessionManager);
        FriendRequestView friendRequestView = new FriendRequestView(friendRequestViewModel, sessionManager, baseUIController);

        FriendRequestInputBoundary interactor = new FriendRequestInteractor(dummyContactDAO, presenter, sessionManager);
        FriendRequestController controller = new FriendRequestController(interactor);
        friendRequestView.setFriendRequestController(controller);

        MessageViewModel messageViewModel = new MessageViewModel();
        UpdateChatChannelViewModel updateChatChannelViewModel = new UpdateChatChannelViewModel();

        DBMessageDataAccessObject messageDataAccessObject = new DBMessageDataAccessObject(conn);
        DBUserDataAccessObject userDataAccessObject = new DBUserDataAccessObject(conn);

        SendMessageOutputBoundary sendMessagePresenter = new ChatChannelPresenter(messageViewModel);
        UpdateChatChannelOutputBoundary updateChatChannelPresenter = new UpdateChatChannelPresenter(updateChatChannelViewModel,
                sessionManager);

        LogoutViewModel logoutViewModel = new LogoutViewModel();
        LoginViewModel loginViewModel = new LoginViewModel();
        AppBuilder appBuilder = new AppBuilder();
        LogoutPresenter logoutPresenter = new LogoutPresenter(logoutViewModel, viewManagerModel, loginViewModel, sessionManager, appBuilder);

        SendMessageInteractor sendMessageInteractor = new SendMessageInteractor(sendMessagePresenter,
                messageDataAccessObject, sessionManager, messageSender);
        UpdateChatChannelInteractor updateChatChannelInteractor = new UpdateChatChannelInteractor(
                dbChatChannelDataAccessObject, updateChatChannelPresenter);
        LogoutInputBoundary logoutInteractor = new LogoutInteractor(logoutPresenter);

        SendMessageController sendMessageController = new SendMessageController(sendMessageInteractor);
        UpdateChatChannelController updateChatChannelController = new UpdateChatChannelController(updateChatChannelInteractor);
        LogoutController logoutController = new LogoutController(logoutInteractor);

        BaseUIView baseUIView = new BaseUIView(baseUIViewModel, baseUIController, updateChatChannelViewModel,
                chatChannelViewModel,  viewManagerModel, (SessionManager) sessionManager, viewManager,
                sendMessageController, updateChatChannelController, logoutController);
        CreateChatView addChatChannelView = new CreateChatView(sessionManager, addChatChannelController, baseUIViewModel, baseUIController,
                addChatChannelViewModel);

        viewManager.addView(friendRequestView, friendRequestViewModel.getViewName());
        viewManager.addView(baseUIView, baseUIViewModel.getViewName());
        viewManager.addView(addChatChannelView, addChatChannelViewModel.getViewName());

        frame.add(viewManager);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
