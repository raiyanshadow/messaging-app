package app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.sendbird.client.ApiClient;
import org.sendbird.client.Configuration;

import data.access.DbChatChannelDataAccessObject;
import data.access.DbContactDataAccessObject;
import data.access.DbMessageDataAccessObject;
import data.access.DbUserDataAccessObject;
import interface_adapter.ViewManagerModel;
import interface_adapter.add_chat_channel.AddChatChannelController;
import interface_adapter.add_chat_channel.AddChatChannelPresenter;
import interface_adapter.add_chat_channel.AddChatChannelViewModel;
import interface_adapter.add_contact.AddContactController;
import interface_adapter.add_contact.AddContactPresenter;
import interface_adapter.add_contact.AddContactViewModel;
import interface_adapter.base_UI.BaseUiController;
import interface_adapter.base_UI.BaseUiPresenter;
import interface_adapter.base_UI.BaseUiViewModel;
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
import interface_adapter.profile_edit.ProfileEditController;
import interface_adapter.profile_edit.ProfileEditPresenter;
import interface_adapter.profile_edit.ProfileEditViewModel;
import interface_adapter.search_contact.SearchContactController;
import interface_adapter.search_contact.SearchContactPresenter;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupPresenter;
import interface_adapter.signup.SignupViewModel;
import interface_adapter.update_chat_channel.UpdateChatChannelController;
import interface_adapter.update_chat_channel.UpdateChatChannelPresenter;
import interface_adapter.update_chat_channel.UpdateChatChannelViewModel;
import io.github.cdimascio.dotenv.Dotenv;
import sendbirdapi.ChannelCreator;
import sendbirdapi.MessageSender;
import sendbirdapi.SendbirdUserCreator;
import session.SessionManager;
import use_case.add_chat_channel.AddChatChannelInputBoundary;
import use_case.add_chat_channel.AddChatChannelInteractor;
import use_case.add_chat_channel.AddChatChannelOutputBoundary;
import use_case.add_contact.AddContactInputBoundary;
import use_case.add_contact.AddContactInteractor;
import use_case.add_contact.AddContactOutputBoundary;
import use_case.baseUI.BaseUiInteractor;
import use_case.baseUI.BaseUiOutputBoundary;
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
import view.AddContactView;
import view.BaseUiView;
import view.CreateChatView;
import view.FriendRequestView;
import view.LoginView;
import view.ProfileEditView;
import view.SignupView;
import view.ViewManager;

/**
 * Builder for creating the application software.
 */
public class AppBuilder {
    private final ViewManagerModel viewManagerModel = new ViewManagerModel();
    private ViewManager viewManager = new ViewManager(viewManagerModel);

    private final Dotenv dotenv = Dotenv.configure()
            .directory("./assets")
            .filename("env")
            .load();

    private final Connection connection = DriverManager.getConnection(dotenv.get("DB_URL"),
            dotenv.get("DB_USER"), dotenv.get("DB_PASSWORD"));

    private final DbChatChannelDataAccessObject chatChannelDataAccessObject = new
            DbChatChannelDataAccessObject(connection);
    private final DbContactDataAccessObject contactDataAccessObject = new DbContactDataAccessObject(connection);
    private final DbMessageDataAccessObject messageDataAccessObject = new DbMessageDataAccessObject(connection);
    private final DbUserDataAccessObject userDataAccessObject = new DbUserDataAccessObject(connection);

    private SessionManager sessionManager = new SessionManager();
    private SignupView signupView;
    private SignupViewModel signupViewModel;
    private LoginView loginView;
    private LoginViewModel loginViewModel;
    private BaseUiView baseUiView;
    private BaseUiViewModel baseUiViewModel;
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
    private LogoutViewModel logoutViewModel;
    private MessageViewModel messageViewModel;

    private BaseUiOutputBoundary baseUiPresenter;
    private BaseUiController baseUiController;

    private final ApiClient defaultClient = Configuration.getDefaultApiClient().setBasePath(
            "https://api-" + dotenv.get("MSG_APP_ID") + ".sendbird.com"
    );
    private final ChannelCreator channelCreator = new ChannelCreator(defaultClient);
    private final MessageSender messageSender = new MessageSender(defaultClient);

    public AppBuilder() throws SQLException {
        // Needed to throw the SQL exception.
    }

    /**
     * Main method to build the final app.
     * @return A JFrame that contains the application.
     */
    public JFrame build() {
        final JFrame application = new JFrame("Messaging App");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        application.add(viewManager);

        return application;
    }

    /**
     * Builds the pre login views.
     * @return An AppBuilder instance with all pre login views with their respective interactors.
     */
    public AppBuilder buildPreLogin() {
        signupViewModel = new SignupViewModel();
        loginViewModel = new LoginViewModel();
        baseUiViewModel = new BaseUiViewModel("baseUIView");

        final SendbirdUserCreator sendbirdUserCreator = new SendbirdUserCreator(dotenv.get("MSG_APP_ID"));

        final SignupOutputBoundary signupPresenter = new SignupPresenter(viewManagerModel, signupViewModel,
                loginViewModel);
        final LoginOutputBoundary loginPresenter = new LoginPresenter(viewManagerModel, loginViewModel,
                signupViewModel, baseUiViewModel, sessionManager, this);

        final SignupInputBoundary signupInteractor = new SignupInteractor(userDataAccessObject, signupPresenter,
                sendbirdUserCreator);
        final LoginInputBoundary loginInteractor = new LoginInteractor(userDataAccessObject, loginPresenter,
                chatChannelDataAccessObject, baseUiViewModel);

        final SignupController signupController = new SignupController(signupInteractor);
        final LoginController loginController = new LoginController(loginInteractor);

        signupView = new SignupView(signupViewModel);
        loginView = new LoginView(loginViewModel);

        signupView.setSignupController(signupController);
        loginView.setLoginController(loginController);

        viewManager.addView(signupView, signupView.getViewName());
        viewManager.addView(loginView, loginView.getViewName());

        viewManagerModel.setState(signupView.getViewName());
        viewManagerModel.firePropertyChange();

        return this;
    }

    /**
     * Builds all views after user has logged in.
     * @return An AppBuilder instance with all views after user has logged in, with their respective interactors.
     */
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
        final AddChatChannelOutputBoundary addChatChannelPresenter = new AddChatChannelPresenter(
                addChatChannelViewModel);
        final AddContactOutputBoundary addContactPresenter = new AddContactPresenter(addContactViewModel);
        baseUiPresenter = new BaseUiPresenter(baseUiViewModel, viewManagerModel, addChatChannelViewModel,
                friendRequestViewModel, addContactViewModel, profileEditViewModel);
        final FriendRequestOutputBoundary friendRequestPresenter = new FriendRequestPresenter(friendRequestViewModel,
                baseUiViewModel, sessionManager);
        final LogoutOutputBoundary logoutPresenter = new LogoutPresenter(logoutViewModel, viewManagerModel,
                loginViewModel, sessionManager, this);
        final UpdateChatChannelOutputBoundary updatePresenter = new UpdateChatChannelPresenter(
                updateChatChannelViewModel, sessionManager);
        final SendMessageOutputBoundary sendMessagePresenter = new ChatChannelPresenter(messageViewModel);
        final ProfileEditOutputBoundary profileEditPresenter = new ProfileEditPresenter(profileEditViewModel);
        final SearchContactOutputBoundary searchContactPresenter = new SearchContactPresenter(addContactViewModel,
                viewManagerModel);

        final AddChatChannelInputBoundary addChatChannelInteractor = new AddChatChannelInteractor(
                addChatChannelPresenter, chatChannelDataAccessObject, userDataAccessObject, sessionManager,
                channelCreator
        );
        final AddContactInputBoundary addContactInteractor = new AddContactInteractor(
                userDataAccessObject, contactDataAccessObject, addContactPresenter, sessionManager
        );
        final FriendRequestInputBoundary friendRequestInteractor = new FriendRequestInteractor(
                contactDataAccessObject, friendRequestPresenter, sessionManager
        );
        final LogoutInputBoundary logoutInteractor = new LogoutInteractor(logoutPresenter);
        final UpdateChatChannelInputBoundary updateInteractor = new UpdateChatChannelInteractor(
                chatChannelDataAccessObject, updatePresenter);
        final SendMessageInputBoundary sendInteractor = new SendMessageInteractor(sendMessagePresenter,
                messageDataAccessObject, sessionManager, messageSender);
        final ProfileEditInputBoundary profileEditInteractor = new ProfileEditInteractor(userDataAccessObject,
                profileEditPresenter, sessionManager);
        final BaseUiInteractor baseUiInteractor = new BaseUiInteractor(baseUiPresenter, chatChannelDataAccessObject,
                sessionManager, contactDataAccessObject);
        final SearchContactInputBoundary searchContactInteractor = new SearchContactInteractor(userDataAccessObject,
                searchContactPresenter);

        final AddChatChannelController addChatChannelController =
                new AddChatChannelController(addChatChannelInteractor);
        final AddContactController addContactController = new AddContactController(addContactInteractor);
        final FriendRequestController friendRequestController = new FriendRequestController(friendRequestInteractor);
        final LogoutController logoutController = new LogoutController(logoutInteractor);
        final UpdateChatChannelController updateChatChannelController =
                new UpdateChatChannelController(updateInteractor);
        final ProfileEditController profileEditController = new ProfileEditController(profileEditInteractor);
        final SendMessageController sendMessageController = new SendMessageController(sendInteractor);
        final SearchContactController searchContactController = new SearchContactController(searchContactInteractor);
        baseUiController = new BaseUiController(baseUiInteractor);

        createChatView = new CreateChatView(sessionManager, addChatChannelController,
                baseUiController, addChatChannelViewModel);
        addContactView = new AddContactView(addContactViewModel, baseUiController);
        profileEditView = new ProfileEditView(profileEditViewModel, baseUiController, sessionManager);

        try {
            baseUiView = new BaseUiView(baseUiViewModel, baseUiController, updateChatChannelViewModel,
                    chatChannelViewModel, viewManagerModel, sessionManager, viewManager,
                    sendMessageController, updateChatChannelController, logoutController);
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        friendRequestView = new FriendRequestView(friendRequestViewModel, sessionManager,
                baseUiController);

        createChatView.setAddChatChannelController(addChatChannelController);
        addContactView.setAddContactController(addContactController);
        addContactView.setSearchContactController(searchContactController);
        friendRequestView.setFriendRequestController(friendRequestController);
        profileEditView.setProfileEditController(profileEditController);

        viewManager.addView(createChatView, addChatChannelViewModel.getViewName());
        viewManager.addView(addContactView, addContactViewModel.getViewName());
        viewManager.addView(friendRequestView, friendRequestViewModel.getViewName());
        viewManager.addView(profileEditView, profileEditViewModel.getViewName());
        viewManager.addView(baseUiView, baseUiViewModel.getViewName());

        // run displayUI off-EDT or in SwingWorker; simple immediate call (acceptable if light)
        try {
            baseUiController.displayUi();
            viewManagerModel.setState(baseUiViewModel.getViewName());
            viewManagerModel.firePropertyChange();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }

        return this;
    }

    /**
     * Destroys all views once user has logged out.
     */
    public void destroyPostLogin() {
        viewManager.remove(baseUiView);
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
