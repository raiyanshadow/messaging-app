package app;

/**
 * This is just to test the view. will delete after
 */

import SendBirdAPI.MessageSender;
import data_access.DBChatChannelDataAccessObject;
import data_access.DBMessageDataAccessObject;
import data_access.DBUserDataAccessObject;
import data_access.UserDataAccessObject;
import entity.DirectChatChannel;
import entity.User;
import interface_adapter.ViewManagerModel;
import interface_adapter.add_chat_channel.AddChatChannelViewModel;
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
import interface_adapter.update_chat_channel.UpdateChatChannelState;
import interface_adapter.update_chat_channel.UpdateChatChannelViewModel;
import io.github.cdimascio.dotenv.Dotenv;
import org.sendbird.client.ApiClient;
import org.sendbird.client.Configuration;
import session.Session;
import session.SessionManager;
import use_case.baseUI.BaseUIInteractor;
import use_case.logout.LogoutInputBoundary;
import use_case.logout.LogoutInteractor;
import use_case.logout.LogoutOutputBoundary;
import use_case.send_message.SendMessageInputBoundary;
import use_case.send_message.SendMessageInteractor;
import use_case.update_chat_channel.UpdateChatChannelInputBoundary;
import use_case.update_chat_channel.UpdateChatChannelInteractor;
import view.BaseUIView;
import view.ChatChannelView;
import view.ViewManager;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ChatChannelViewTestReceiver {
    public static <DBessageDataAccessObject> void main(String[] args) throws SQLException {
        // ============================ SETUP ============================
        Dotenv dotenv = Dotenv.configure()
                .directory("./assets")
                .filename("env")
                .load();

        String url = dotenv.get("DB_URL");
        String dbUser = dotenv.get("DB_USER");
        String dbPassword = dotenv.get("DB_PASSWORD");

        Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);

        // Sendbird setup
        String appId = dotenv.get("MSG_APP_ID");
        String apiToken = dotenv.get("MSG_TOKEN");

        User user1 = new User(2, "Bob", "def", "English");
        User user2 = new User(1, "Alice", "abc", "English");

        // Insert users if they don't exist
        String insertUserSQL = "INSERT INTO \"user\" (id, username, created_at, preferred_language, password) " +
                "VALUES (?, ?, NOW(), ?, ?) " +
                "ON CONFLICT (id) DO UPDATE SET password = EXCLUDED.password, preferred_language = EXCLUDED.preferred_language";

        try (PreparedStatement ps = connection.prepareStatement(insertUserSQL)) {
            ps.setInt(1, user1.getUserID());
            ps.setString(2, user1.getUsername());
            ps.setString(3, user1.getPreferredLanguage());
            ps.setString(4, user1.getPassword());
            ps.executeUpdate();

            ps.setInt(1, user2.getUserID());
            ps.setString(2, user2.getUsername());
            ps.setString(3, user2.getPreferredLanguage());
            ps.setString(4, user2.getPassword());
            ps.executeUpdate();
        }

        // ============================ CREATE CHAT CHANNEL ============================
//        ChannelCreator channelCreator = new ChannelCreator(appId);
        String channelUrl = "sendbird_group_channel_50257357_374e0405b4e79b2ba44e90858baac40e23f3a397";
//        String channelUrl = channelCreator.SendbirdChannelCreator(apiToken, "example", user1, user2);
        System.out.println("Generated Channel URL: " + channelUrl);

        // Insert chat channel
        String insertChatSQL = "INSERT INTO chat_channel (user1_id, user2_id, channel_url, name) " +
                "VALUES (?, ?, ?, ?) " +
                "ON CONFLICT (channel_url) DO NOTHING";

        try (PreparedStatement ps = connection.prepareStatement(insertChatSQL)) {
            ps.setInt(1, user1.getUserID());
            ps.setInt(2, user2.getUserID());
            ps.setString(3, channelUrl);
            ps.setString(4, "Example Chat");
            ps.executeUpdate();
        }

        System.out.println("Chat Channel inserted successfully!");

        // ============================ INSERT A MESSAGE ============================
//        String insertMessageSQL = "INSERT INTO text_message (channel_url, sender_id, receiver_id, content, time_sent, status) " +
//                "VALUES (?, ?, ?, ?, ?, ?) " +
//                "ON CONFLICT DO NOTHING";
//
//        try (PreparedStatement ps = connection.prepareStatement(insertMessageSQL)) {
//            ps.setString(1, channelUrl);
//            ps.setInt(2, user1.getUserID()); // sender
//            ps.setInt(3, user2.getUserID()); // receiver
//            ps.setString(4, "Hello, from Alice!");
//            ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
//            ps.setString(6, "sent");
//            ps.executeUpdate();
//        }
//
//        System.out.println("Message inserted successfully!");

        // ============================ USE DAO TO FETCH CHAT ============================
        DBChatChannelDataAccessObject chatDAO = new DBChatChannelDataAccessObject(connection);
        DirectChatChannel chat = chatDAO.getDirectChatChannelByURL(channelUrl);

        // Verify
        System.out.println("Fetched Chat Channel: " + chat.getChatName());
        System.out.println("User1: " + chat.getUser1().getUsername() + ", User2: " + chat.getUser2().getUsername());
        System.out.println("url: " + chat.getChatURL());


        // 1. ViewModel
        UpdateChatChannelViewModel vm = new UpdateChatChannelViewModel();
        MessageViewModel messageViewModel = new MessageViewModel();
        baseUIViewModel baseUIViewModel = new baseUIViewModel("baseUIView"); // TODO: should this have a string as an argument?
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        FriendRequestViewModel friendRequestViewModel = new FriendRequestViewModel();
        AddChatChannelViewModel addChatChannelViewModel = new AddChatChannelViewModel("Add Chat Channel"); // TODO: Should this have a string as an argument?
        AddContactViewModel addContactViewModel = new AddContactViewModel();
        ViewManager viewManager = new ViewManager(viewManagerModel);
        LogoutViewModel logoutViewModel = new LogoutViewModel();
        LoginViewModel loginViewModel = new LoginViewModel();
        AppBuilder appBuilder = new AppBuilder();

        // 2. Presenter
        SessionManager sessionManager = new SessionManager();
        sessionManager.setMainUser(user1);
        sessionManager.setLoggedin(true);
        UpdateChatChannelPresenter presenter = new UpdateChatChannelPresenter(vm, sessionManager);
        ChatChannelPresenter presenter2 = new ChatChannelPresenter(messageViewModel);
        baseUIPresenter presenter3 = new baseUIPresenter(baseUIViewModel, viewManagerModel, addChatChannelViewModel, friendRequestViewModel, addContactViewModel);
        LogoutPresenter presenter4 = new LogoutPresenter(logoutViewModel, viewManagerModel, loginViewModel, sessionManager, appBuilder);

        // 2. DAOs and other variables
//        DBChatChannelDataAccessObject chatDAO = new DBChatChannelDataAccessObject(connection);
        UserDataAccessObject userDAO = new DBUserDataAccessObject(connection);
        DBMessageDataAccessObject messageDAO = new DBMessageDataAccessObject(connection);
        ApiClient defaultClient = Configuration.getDefaultApiClient().setBasePath(
                "https://api-" + dotenv.get("MSG_APP_ID") + ".sendbird.com"
        );
        MessageSender messageSender = new MessageSender(defaultClient);

        // 4. Interactor
        UpdateChatChannelInputBoundary interactor = new UpdateChatChannelInteractor(chatDAO, presenter);
        SendMessageInputBoundary messageInteractor = new SendMessageInteractor(presenter2, userDAO, messageDAO, sessionManager, messageSender);
        BaseUIInteractor baseUIInteractor = new BaseUIInteractor(presenter3, chatDAO, userDAO, sessionManager);
        LogoutInputBoundary logoutInteractor = new LogoutInteractor(presenter4);


        // 5. Controller
        UpdateChatChannelController controller = new UpdateChatChannelController(interactor);
        SendMessageController sendMessageController = new SendMessageController(messageInteractor);
        baseUIController baseUIController = new baseUIController(baseUIInteractor); // TODO: Fix naming
        LogoutController logoutController = new LogoutController(logoutInteractor);

        // 6. View
//        UpdateChatChannelViewModel updateChatChannelViewModel = new  UpdateChatChannelViewModel();
        UpdateChatChannelState updateChatChannelState = vm.getState();
        updateChatChannelState.setUser1ID(2);
        updateChatChannelState.setUser2ID(1);
        updateChatChannelState.setChatURL(channelUrl);
        updateChatChannelState.setUser1Name("Bob");
        updateChatChannelState.setUser2Name("Alice");
        vm.setState(updateChatChannelState);
        ChatChannelViewModel chatChannelViewModel = new ChatChannelViewModel("Chat");
        BaseUIView baseUIView = new BaseUIView(baseUIViewModel, baseUIController, vm,
                chatChannelViewModel, viewManagerModel, (SessionManager) sessionManager, viewManager,
                sendMessageController, controller, logoutController);
        ChatChannelView view = new ChatChannelView(vm, controller, sendMessageController);
        view.setUpdateChatChannelController(controller);
        view.setSendMessageController(sendMessageController);
        view.setBaseUIController(baseUIController);

        // View Manager model
        viewManager.addView(view, "update chat channel");
        viewManager.addView(baseUIView, "baseUIView");

        // 7. Execute
        controller.execute(channelUrl);

        // 8. Create Window
        JFrame frame = new JFrame("TEST CHAT VIEW");
//        frame.setContentPane(view);
        frame.setContentPane(viewManager);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
