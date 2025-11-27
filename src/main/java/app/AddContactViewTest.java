package app;

import SendBirdAPI.MessageSender;
import data_access.*;
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

import interface_adapter.chat_channel.ChatChannelPresenter;
import interface_adapter.chat_channel.ChatChannelViewModel;
import interface_adapter.chat_channel.MessageViewModel;
import interface_adapter.chat_channel.SendMessageController;
import interface_adapter.friend_request.FriendRequestViewModel;
import interface_adapter.login.LoginViewModel;
import interface_adapter.logout.LogoutController;
import interface_adapter.logout.LogoutPresenter;
import interface_adapter.logout.LogoutViewModel;
import interface_adapter.update_chat_channel.UpdateChatChannelController;
import interface_adapter.update_chat_channel.UpdateChatChannelPresenter;
import interface_adapter.update_chat_channel.UpdateChatChannelViewModel;
import io.github.cdimascio.dotenv.Dotenv;
import org.sendbird.client.ApiClient;
import org.sendbird.client.Configuration;
import session.SessionManager;
import use_case.add_chat_channel.AddChatChannelInteractor;
import use_case.add_contact.*;
import use_case.baseUI.BaseUIInteractor;
import use_case.logout.LogoutInteractor;
import use_case.logout.LogoutOutputBoundary;
import use_case.send_message.SendMessageInputBoundary;
import use_case.send_message.SendMessageInteractor;
import use_case.send_message.SendMessageOutputBoundary;
import use_case.signup.SignupOutputBoundary;
import use_case.signup.SignupOutputData;
import use_case.update_chat_channel.UpdateChatChannelInteractor;
import use_case.update_chat_channel.UpdateChatChannelOutputBoundary;
import view.AddContactView;
import view.BaseUIView;
import view.CreateChatView;
import view.ViewManager;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AddContactViewTest {
    public static void main(String[] args) throws SQLException {
        JFrame frame = new JFrame("add contact testing");

        final Dotenv dotenv = Dotenv.configure()
                .directory("./assets")
                .filename("env")
                .load();

        // DAO
        Connection conn = DBConnectionFactory.createConnection();
        DBUserDataAccessObject dummyUserDAO = new DBUserDataAccessObject(conn);
        DBContactDataAccessObject dummyContactDAO = new DBContactDataAccessObject(conn);
        DBChatChannelDataAccessObject dbChatChannelDataAccessObject = new DBChatChannelDataAccessObject(conn);
        DBMessageDataAccessObject messageDataAccessObject = new DBMessageDataAccessObject(conn);


        // testing if actually able to send request
        // dummyDAO.sendRequest(dummyDAO.getUserFromName("a"), "Bob");
        User temp = dummyUserDAO.getUserFromID(1);
        dummyContactDAO.updateUserContacts(temp, temp.getContacts());

        System.out.println(temp.getUsername());

        AddContactViewModel addContactViewModel = new AddContactViewModel();
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        AddContactPresenter presenter = new AddContactPresenter(addContactViewModel, viewManagerModel);
        SessionManager sessionManager = new SessionManager(temp, true);
        ApiClient defaultClient = Configuration.getDefaultApiClient().setBasePath(
                "https://api-" + dotenv.get("MSG_APP_ID") + ".sendbird.com"
        );
        MessageSender messageSender = new MessageSender(defaultClient);

        baseUIViewModel baseUIViewModel = new baseUIViewModel("baseUIView");
        ChatChannelViewModel chatChannelViewModel = new ChatChannelViewModel("chatChannelViewModel");
        AddChatChannelViewModel addChatChannelViewModel = new AddChatChannelViewModel("addChatChannelViewModel");
        MessageViewModel messageViewModel = new MessageViewModel();
        UpdateChatChannelViewModel updateChatChannelViewModel = new UpdateChatChannelViewModel();
        AddChatChannelPresenter addChatChannelPresenter = new AddChatChannelPresenter(chatChannelViewModel, addChatChannelViewModel, viewManagerModel);
        FriendRequestViewModel friendRequestViewModel = new FriendRequestViewModel();
        LogoutViewModel logoutViewModel = new LogoutViewModel();
        LoginViewModel loginViewModel = new LoginViewModel();
        AppBuilder appBuilder = new AppBuilder();
        baseUIPresenter baseUIPresenter = new baseUIPresenter(baseUIViewModel, viewManagerModel, addChatChannelViewModel, friendRequestViewModel, addContactViewModel);
        SendMessageOutputBoundary sendMessagePresenter = new ChatChannelPresenter(messageViewModel);
        UpdateChatChannelOutputBoundary updateChatChannelPresenter = new UpdateChatChannelPresenter(updateChatChannelViewModel,
                sessionManager);
        LogoutOutputBoundary logoutPresenter = new LogoutPresenter(logoutViewModel, viewManagerModel, loginViewModel, sessionManager, appBuilder);

        AddChatChannelInteractor addChatChannelInteractor = new AddChatChannelInteractor(addChatChannelPresenter, dbChatChannelDataAccessObject, dummyUserDAO, sessionManager);
        BaseUIInteractor baseUIInteractor = new BaseUIInteractor(baseUIPresenter, dbChatChannelDataAccessObject, dummyUserDAO, sessionManager);
        SendMessageInteractor sendMessageInteractor = new SendMessageInteractor(sendMessagePresenter, dummyUserDAO,
                messageDataAccessObject, sessionManager, messageSender);
        UpdateChatChannelInteractor updateChatChannelInteractor = new UpdateChatChannelInteractor(
                dbChatChannelDataAccessObject, updateChatChannelPresenter);
        LogoutInteractor logoutInteractor = new LogoutInteractor(logoutPresenter);

        AddChatChannelController addChatChannelController = new AddChatChannelController(addChatChannelInteractor);
        baseUIController baseUIController = new baseUIController(baseUIInteractor);
        SendMessageController sendMessageController = new SendMessageController(sendMessageInteractor);
        UpdateChatChannelController updateChatChannelController = new UpdateChatChannelController(updateChatChannelInteractor);
        LogoutController logoutController = new LogoutController(logoutInteractor);
        ViewManager viewManager = new ViewManager(viewManagerModel);


        // Create actual base view and register it
        BaseUIView baseUIView = new BaseUIView(baseUIViewModel, baseUIController, updateChatChannelViewModel,
                chatChannelViewModel, viewManagerModel, sessionManager, viewManager, sendMessageController,
                updateChatChannelController, logoutController);
        CreateChatView addChatChannelView = new CreateChatView(sessionManager, addChatChannelController, baseUIViewModel, baseUIController);
        AddContactView addContactview = new AddContactView(addContactViewModel, viewManagerModel, sessionManager, baseUIController);

        AddContactInputBoundary interactor = new AddContactInteractor(dummyUserDAO, dummyContactDAO, presenter);
        AddContactController controller = new AddContactController(interactor);
        addContactview.setAddContactController(controller);


        viewManager.addView(addContactview, addChatChannelViewModel.getViewName());
        viewManager.addView(baseUIView, baseUIViewModel.getViewName());
        viewManager.addView(addChatChannelView, addChatChannelViewModel.getViewName());

        frame.add(viewManager);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
