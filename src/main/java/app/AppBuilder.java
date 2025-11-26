package app;

import SendBirdAPI.MessageDeleter;
import SendBirdAPI.MessageEditor;
import SendBirdAPI.MessageSender;
import data_access.*;
import interface_adapter.update_chat_channel.UpdateChatChannelController;
import interface_adapter.update_chat_channel.UpdateChatChannelPresenter;
import org.sendbird.client.ApiClient;
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
import interface_adapter.friend_request.FriendRequestController;
import interface_adapter.friend_request.FriendRequestPresenter;
import interface_adapter.friend_request.FriendRequestViewModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginPresenter;
import interface_adapter.login.LoginViewModel;
import interface_adapter.logout.LogoutController;
import interface_adapter.logout.LogoutPresenter;
import interface_adapter.logout.LogoutViewModel;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupPresenter;
import interface_adapter.signup.SignupViewModel;
import interface_adapter.update_chat_channel.UpdateChatChannelViewModel;
import io.github.cdimascio.dotenv.Dotenv;
import org.sendbird.client.ApiClient;
import org.sendbird.client.Configuration;
import session.SessionManager;
import use_case.add_chat_channel.AddChatChannelInputBoundary;
import use_case.add_chat_channel.AddChatChannelInteractor;
import use_case.add_chat_channel.AddChatChannelOutputBoundary;
import use_case.add_contact.AddContactInputBoundary;
import use_case.add_contact.AddContactInteractor;
import use_case.add_contact.AddContactOutputBoundary;
import use_case.baseUI.BaseUIInteractor;
import use_case.baseUI.BaseUIOutputBoundary;
import use_case.friend_request.FriendRequestInputBoundary;
import use_case.friend_request.FriendRequestInteractor;
import use_case.friend_request.FriendRequestOutputBoundary;
import use_case.login.LoginInputBoundary;
import use_case.login.LoginInteractor;
import use_case.login.LoginOutputBoundary;
import use_case.logout.LogoutInputBoundary;
import use_case.logout.LogoutInteractor;
import use_case.logout.LogoutOutputBoundary;
import use_case.send_message.SendMessageInputBoundary;
import use_case.send_message.SendMessageInteractor;
import use_case.send_message.SendMessageOutputBoundary;
import use_case.signup.SignupInputBoundary;
import use_case.signup.SignupInteractor;
import use_case.signup.SignupOutputBoundary;
import use_case.update_chat_channel.UpdateChatChannelInputBoundary;
import use_case.update_chat_channel.UpdateChatChannelInteractor;
import use_case.update_chat_channel.UpdateChatChannelOutputBoundary;
import view.*;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AppBuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    final ViewManagerModel viewManagerModel = new ViewManagerModel();
    private ViewManager viewManager = new ViewManager(viewManagerModel);

    private final Dotenv dotenv = Dotenv.configure()
            .directory("./assets")
            .filename("env")
            .load();

    Connection connection = DriverManager.getConnection(dotenv.get("DB_URL"),
            dotenv.get("DB_USER"), dotenv.get("DB_PASSWORD"));

    final DBChatChannelDataAccessObject chatChannelDataAccessObject = new DBChatChannelDataAccessObject(connection);
    final DBContactDataAccessObject contactDataAccessObject = new DBContactDataAccessObject(connection);
    final DBMessageDataAccessObject messageDataAccessObject = new DBMessageDataAccessObject(connection);
    final DBUserDataAccessObject userDataAccessObject = new DBUserDataAccessObject(connection);
    final InMemoryChatDAO inMemoryChatDAO = new InMemoryChatDAO();

    private SessionManager sessionManager = new SessionManager();
    private SignupView signupView;
    private SignupViewModel signupViewModel;
    private LoginView loginView;
    private LoginViewModel loginViewModel;
    private BaseUIView baseUIView;
    private baseUIViewModel baseUIViewModel;
    private ChatChannelView chatChannelView;
    private ChatChannelViewModel chatChannelViewModel;
    private FriendRequestView friendRequestView;
    private FriendRequestViewModel friendRequestViewModel;
    private ProfileEditView profileEditView;
    private UpdateChatChannelViewModel updateChatChannelViewModel;
    private CreateChatView createChatView;
    private AddChatChannelViewModel addChatChannelViewModel;
    private AddContactView addContactView;
    private AddContactViewModel addContactViewModel;
    private LogoutView logoutView;
    private LogoutViewModel logoutViewModel;
    private MessageViewModel messageViewModel;

    private BaseUIOutputBoundary baseUIPresenter;
    private BaseUIInteractor baseUIInteractor;
    private baseUIController baseUIController;

    private AddChatChannelOutputBoundary addChatChannelPresenter;
    private AddChatChannelInteractor addChatChannelInteractor;
    private AddChatChannelController addChatChannelController;

    ApiClient defaultClient = Configuration.getDefaultApiClient().setBasePath(
            "https://api-" + dotenv.get("MSG_APP_ID") + ".sendbird.com"
    );
    private MessageSender messageSender = new MessageSender(defaultClient);
    private MessageEditor messageEditor;
    private MessageDeleter messageDeleter;

    public AppBuilder() throws SQLException {
        cardPanel.setLayout(cardLayout);
    }

    public JFrame build() {
        final JFrame application = new JFrame("Messaging App");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        application.add(cardPanel);

        viewManagerModel.setState(signupView.getViewName());
        viewManagerModel.firePropertyChange();

        return application;
    }

    public AppBuilder buildPreLogin() {
        signupViewModel = new SignupViewModel();
        loginViewModel = new LoginViewModel();

        SignupOutputBoundary signupPresenter = new SignupPresenter(viewManagerModel, signupViewModel, loginViewModel);
        LoginOutputBoundary loginPresenter = new LoginPresenter(viewManagerModel, loginViewModel, baseUIViewModel,
                signupViewModel, sessionManager, this);

        SignupInputBoundary signupInteractor = new SignupInteractor(userDataAccessObject, signupPresenter);
        LoginInputBoundary loginInteractor = new LoginInteractor(userDataAccessObject, loginPresenter);

        SignupController signupController = new  SignupController(signupInteractor);
        LoginController loginController = new LoginController(loginInteractor);

        signupView = new SignupView(signupViewModel);
        loginView = new LoginView(loginViewModel);

        signupView.setSignupController(signupController);
        loginView.setLoginController(loginController);

        cardPanel.add(signupView);
        cardPanel.add(loginView);

        return this;
    }

    public AppBuilder buildPostLogin() {
        addChatChannelViewModel = new AddChatChannelViewModel("addChatChannelView");
        addContactViewModel = new AddContactViewModel();
        baseUIViewModel = new baseUIViewModel("baseUIView");
        chatChannelViewModel = new ChatChannelViewModel("Chat");
        updateChatChannelViewModel = new UpdateChatChannelViewModel();
        messageViewModel = new MessageViewModel();
        friendRequestViewModel = new FriendRequestViewModel();
        addChatChannelViewModel = new AddChatChannelViewModel("addChatChannel");
        logoutViewModel = new LogoutViewModel();

        AddChatChannelOutputBoundary addChatChannelPresenter = new AddChatChannelPresenter(chatChannelViewModel,
                addChatChannelViewModel, viewManagerModel);
        AddContactOutputBoundary addContactPresenter = new AddContactPresenter(addContactViewModel, viewManagerModel);
        baseUIPresenter = new baseUIPresenter(baseUIViewModel, viewManagerModel, addChatChannelViewModel,
                friendRequestViewModel, addContactViewModel);
        FriendRequestOutputBoundary friendRequestPresenter = new FriendRequestPresenter(friendRequestViewModel,
                viewManagerModel, baseUIViewModel);
        LogoutOutputBoundary logoutPresenter = new LogoutPresenter(logoutViewModel, viewManagerModel, loginViewModel,
                sessionManager, this);
        UpdateChatChannelOutputBoundary updatePresenter = new UpdateChatChannelPresenter(updateChatChannelViewModel);
        SendMessageOutputBoundary sendMessagePresenter = new ChatChannelPresenter(messageViewModel);

        AddChatChannelInputBoundary addChatChannelInteractor = new AddChatChannelInteractor(
                addChatChannelPresenter, chatChannelDataAccessObject, userDataAccessObject, sessionManager
        );
        AddContactInputBoundary addContactInteractor = new AddContactInteractor(
                userDataAccessObject, contactDataAccessObject, addContactPresenter
        );
        FriendRequestInputBoundary friendRequestInteractor = new FriendRequestInteractor(
                contactDataAccessObject, friendRequestPresenter
        );
        LogoutInputBoundary logoutInteractor = new LogoutInteractor(logoutPresenter);
        UpdateChatChannelInputBoundary updateInteractor = new UpdateChatChannelInteractor(chatChannelDataAccessObject,
                updatePresenter);
        SendMessageInputBoundary sendInteractor = new SendMessageInteractor(sendMessagePresenter,
                userDataAccessObject, messageDataAccessObject, sessionManager, messageSender);
        BaseUIInteractor baseUIInteractor = new BaseUIInteractor(baseUIPresenter, chatChannelDataAccessObject,
                userDataAccessObject, sessionManager);

        AddChatChannelController addChatChannelController = new AddChatChannelController(addChatChannelInteractor);
        AddContactController addContactController = new AddContactController(addContactInteractor);
        FriendRequestController friendRequestController = new FriendRequestController(friendRequestInteractor);
        LogoutController logoutController = new LogoutController(logoutInteractor);
        UpdateChatChannelController updateChatChannelController = new UpdateChatChannelController(updateInteractor);
        SendMessageController sendMessageController = new SendMessageController(sendInteractor);
        baseUIController = new baseUIController(baseUIInteractor);

        createChatView = new CreateChatView(sessionManager, addChatChannelController,
                baseUIViewModel, baseUIController);
        addContactView = new AddContactView(addContactViewModel, viewManagerModel, sessionManager, baseUIController);
        try {
            baseUIView = new BaseUIView(baseUIViewModel, baseUIController, updateChatChannelViewModel,
                    chatChannelViewModel, viewManagerModel, sessionManager, viewManager,
                    sendMessageController, updateChatChannelController);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        friendRequestView = new FriendRequestView(friendRequestViewModel, viewManagerModel, sessionManager,
                baseUIController);
        logoutView = new LogoutView();

        createChatView.setAddChatChannelController(addChatChannelController);
        addContactView.setAddContactController(addContactController);
        friendRequestView.setFriendRequestController(friendRequestController);

        cardPanel.add(createChatView);
        cardPanel.add(addContactView);
        cardPanel.add(friendRequestView);
        cardPanel.add(baseUIView);

        return this;
    }

    public void destroyPostLogin() {
        cardPanel.remove(baseUIView);
        cardPanel.remove(friendRequestView);
        cardPanel.remove(addContactView);
        cardPanel.remove(createChatView);

        baseUIView = null;
        chatChannelView = null;
        friendRequestView = null;
        addContactView = null;
        createChatView = null;

        baseUIViewModel = null;
        chatChannelViewModel = null;
        friendRequestViewModel = null;
        addContactViewModel = null;
        addChatChannelViewModel = null;
        updateChatChannelViewModel = null;

        cardPanel.revalidate();
        cardPanel.repaint();
    }
}
