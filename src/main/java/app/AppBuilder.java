package app;

import SendBirdAPI.*;
import data_access.*;
import interface_adapter.profile_edit.ProfileEditController;
import interface_adapter.profile_edit.ProfileEditPresenter;
import interface_adapter.profile_edit.ProfileEditViewModel;
import interface_adapter.search_contact.SearchContactController;
import interface_adapter.search_contact.SearchContactPresenter;
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
import use_case.profile_edit.ProfileEditInputBoundary;
import use_case.profile_edit.ProfileEditInteractor;
import use_case.profile_edit.ProfileEditOutputBoundary;
import use_case.search_contact.SearchContactInputBoundary;
import use_case.search_contact.SearchContactInteractor;
import use_case.search_contact.SearchContactOutputBoundary;
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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AppBuilder {
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
    private ChatChannelViewModel chatChannelViewModel;
    private FriendRequestView friendRequestView;
    private FriendRequestViewModel friendRequestViewModel;
    private ProfileEditView profileEditView;
    private ProfileEditViewModel profileEditViewModel;
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
    private ChannelCreator channelCreator = new ChannelCreator(defaultClient);
    private MessageSender messageSender = new MessageSender(defaultClient);
    private MessageEditor messageEditor;
    private MessageDeleter messageDeleter;

    public AppBuilder() throws SQLException {
    }

    public JFrame build() {
        final JFrame application = new JFrame("Messaging App");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        application.add(viewManager);

        return application;
    }

    public AppBuilder buildPreLogin() {
        signupViewModel = new SignupViewModel();
        loginViewModel = new LoginViewModel();
        baseUIViewModel = new baseUIViewModel("baseUIView");

        SendbirdUserCreator sendbirdUserCreator = new SendbirdUserCreator(dotenv.get("MSG_APP_ID"));

        SignupOutputBoundary signupPresenter = new SignupPresenter(viewManagerModel, signupViewModel, loginViewModel);
        LoginOutputBoundary loginPresenter = new LoginPresenter(viewManagerModel, loginViewModel,
                signupViewModel, baseUIViewModel, sessionManager, this);

        SignupInputBoundary signupInteractor = new SignupInteractor(userDataAccessObject, signupPresenter,
                sendbirdUserCreator);
        LoginInputBoundary loginInteractor = new LoginInteractor(userDataAccessObject, loginPresenter,
                chatChannelDataAccessObject, baseUIViewModel);

        SignupController signupController = new SignupController(signupInteractor);
        LoginController loginController = new LoginController(loginInteractor);

        signupView = new SignupView(signupViewModel);
        loginView = new LoginView(loginViewModel);

        signupView.setSignupController(signupController);
        loginView.setLoginController(loginController);

        viewManager.addView(signupView, signupView.getViewName());
        viewManager.addView(loginView,  loginView.getViewName());

        viewManagerModel.setState(signupView.getViewName());
        viewManagerModel.firePropertyChange();

        return this;
    }

    public AppBuilder buildPostLogin() {
        System.out.println("SESSION USER = " + sessionManager.getMainUser().getUserID());
        addChatChannelViewModel = new AddChatChannelViewModel("addChatChannelView");
        addContactViewModel = new AddContactViewModel();
        chatChannelViewModel = new ChatChannelViewModel("Chat");
        updateChatChannelViewModel = new UpdateChatChannelViewModel();
        messageViewModel = new MessageViewModel();
        friendRequestViewModel = new FriendRequestViewModel();
        logoutViewModel = new LogoutViewModel();
        profileEditViewModel = new ProfileEditViewModel();
        AddChatChannelOutputBoundary addChatChannelPresenter = new AddChatChannelPresenter(chatChannelViewModel,
                addChatChannelViewModel, viewManagerModel);
        AddContactOutputBoundary addContactPresenter = new AddContactPresenter(addContactViewModel, viewManagerModel);
        baseUIPresenter = new baseUIPresenter(baseUIViewModel, viewManagerModel, addChatChannelViewModel,
                friendRequestViewModel, addContactViewModel, profileEditViewModel);
        FriendRequestOutputBoundary friendRequestPresenter = new FriendRequestPresenter(friendRequestViewModel,
                viewManagerModel, baseUIViewModel, sessionManager);
        LogoutOutputBoundary logoutPresenter = new LogoutPresenter(logoutViewModel, viewManagerModel, loginViewModel,
                sessionManager, this);
        UpdateChatChannelOutputBoundary updatePresenter = new UpdateChatChannelPresenter(updateChatChannelViewModel,
                sessionManager);
        SendMessageOutputBoundary sendMessagePresenter = new ChatChannelPresenter(messageViewModel);
        ProfileEditOutputBoundary profileEditPresenter = new ProfileEditPresenter(profileEditViewModel);
        SearchContactOutputBoundary searchContactPresenter = new SearchContactPresenter(addContactViewModel, viewManagerModel);

        AddChatChannelInputBoundary addChatChannelInteractor = new AddChatChannelInteractor(
                addChatChannelPresenter, chatChannelDataAccessObject, userDataAccessObject, sessionManager,
                channelCreator
        );
        AddContactInputBoundary addContactInteractor = new AddContactInteractor(
                userDataAccessObject, contactDataAccessObject, addContactPresenter, sessionManager
        );
        FriendRequestInputBoundary friendRequestInteractor = new FriendRequestInteractor(
                contactDataAccessObject, friendRequestPresenter, sessionManager
        );
        LogoutInputBoundary logoutInteractor = new LogoutInteractor(logoutPresenter);
        UpdateChatChannelInputBoundary updateInteractor = new UpdateChatChannelInteractor(chatChannelDataAccessObject,
                updatePresenter);
        SendMessageInputBoundary sendInteractor = new SendMessageInteractor(sendMessagePresenter,
                messageDataAccessObject, sessionManager, messageSender);
        ProfileEditInputBoundary profileEditInteractor = new ProfileEditInteractor(userDataAccessObject,
                profileEditPresenter, sessionManager);
        BaseUIInteractor baseUIInteractor = new BaseUIInteractor(baseUIPresenter, chatChannelDataAccessObject,
                userDataAccessObject, sessionManager, contactDataAccessObject);
        SearchContactInputBoundary searchContactInteractor = new SearchContactInteractor(userDataAccessObject, searchContactPresenter );

        AddChatChannelController addChatChannelController = new AddChatChannelController(addChatChannelInteractor);
        AddContactController addContactController = new AddContactController(addContactInteractor);
        FriendRequestController friendRequestController = new FriendRequestController(friendRequestInteractor);
        LogoutController logoutController = new LogoutController(logoutInteractor);
        UpdateChatChannelController updateChatChannelController = new UpdateChatChannelController(updateInteractor);
        ProfileEditController profileEditController = new ProfileEditController(profileEditInteractor);
        SendMessageController sendMessageController = new SendMessageController(sendInteractor);
        SearchContactController searchContactController = new SearchContactController(searchContactInteractor);
        baseUIController = new baseUIController(baseUIInteractor);

        createChatView = new CreateChatView(sessionManager, addChatChannelController,
                baseUIViewModel, baseUIController, addChatChannelViewModel);
        addContactView = new AddContactView(addContactViewModel, baseUIController);
        profileEditView = new ProfileEditView(profileEditViewModel, baseUIController, sessionManager);

        try {
            baseUIView = new BaseUIView(baseUIViewModel, baseUIController, updateChatChannelViewModel,
                    chatChannelViewModel, viewManagerModel, sessionManager, viewManager,
                    sendMessageController, updateChatChannelController, logoutController);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        friendRequestView = new FriendRequestView(friendRequestViewModel, sessionManager,
                baseUIController);
        logoutView = new LogoutView();

        createChatView.setAddChatChannelController(addChatChannelController);
        addContactView.setAddContactController(addContactController);
        addContactView.setSearchContactController(searchContactController);
        friendRequestView.setFriendRequestController(friendRequestController);
        profileEditView.setProfileEditController(profileEditController);

        viewManager.addView(createChatView, addChatChannelViewModel.getViewName());
        viewManager.addView(addContactView, addContactViewModel.getViewName());
        viewManager.addView(friendRequestView, friendRequestViewModel.getViewName());
        viewManager.addView(profileEditView, profileEditViewModel.getViewName());
        viewManager.addView(baseUIView, baseUIViewModel.getViewName());

        // run displayUI off-EDT or in SwingWorker; simple immediate call (acceptable if light)
        try {
            baseUIController.displayUI(); // triggers BaseUIInteractor -> presenter -> model.firePropertyChange()
            viewManagerModel.setState(baseUIViewModel.getViewName());
            viewManagerModel.firePropertyChange();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return this;
    }

    public void destroyPostLogin() {
        viewManager.remove(baseUIView);
        viewManager.remove(friendRequestView);
        viewManager.remove(addContactView);
        viewManager.remove(createChatView);
        viewManager.remove(profileEditView);

        friendRequestView = null;
        addContactView = null;
        createChatView = null;
        profileEditView = null;

        chatChannelViewModel = null;
        friendRequestViewModel = null;
        addContactViewModel = null;
        addChatChannelViewModel = null;
        updateChatChannelViewModel = null;
        profileEditViewModel = null;
    }
}
