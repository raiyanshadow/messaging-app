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
import interface_adapter.update_chat_channel.UpdateChatChannelController;
import interface_adapter.update_chat_channel.UpdateChatChannelPresenter;
import interface_adapter.update_chat_channel.UpdateChatChannelViewModel;
import io.github.cdimascio.dotenv.Dotenv;
import org.sendbird.client.ApiClient;
import org.sendbird.client.Configuration;
import session.Session;
import session.SessionManager;
import use_case.baseUI.BaseUIInteractor;
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

//        // 1. Create ViewModel
//        UpdateChatChannelViewModel vm = new UpdateChatChannelViewModel();
//
//// 2. Create Presenter
//        UpdateChatChannelPresenter presenter = new UpdateChatChannelPresenter(vm);
//
//// 3. Create mock interactor
//        // 3. Create mock interactor
//        UpdateChatChannelInputBoundary mockInteractor = chatURL -> {
//            User alice = new User(1, "Alice", "abc", "English");
//            User bob = new User(2, "Bob", "def", "English");
//
//            List<Message> messages = new ArrayList<>();
//            messages.add(MessageFactory.createTextMessage(1, "chat123", alice, bob, "sent", Timestamp.valueOf(LocalDateTime.now()), "Hello world!"));
//            messages.add(MessageFactory.createTextMessage(2, "chat123", bob, alice, "sent", Timestamp.valueOf(LocalDateTime.now()), "Hi Alice!"));
//            messages.add(MessageFactory.createTextMessage(1, "chat123", alice, bob, "sent", Timestamp.valueOf(LocalDateTime.now()), "are you taking csc207"));
//            messages.add(MessageFactory.createTextMessage(2, "chat123", bob, alice, "sent", Timestamp.valueOf(LocalDateTime.now()), "yes"));
//            messages.add(MessageFactory.createTextMessage(1, "chat123", alice, bob, "sent", Timestamp.valueOf(LocalDateTime.now()), "when is your class"));
//            messages.add(MessageFactory.createTextMessage(2, "chat123", bob, alice, "sent", Timestamp.valueOf(LocalDateTime.now()), "today"));
//
//            presenter.prepareSuccessView(new UpdateChatChannelOutputData("Example Chat", "chat123", alice, bob, messages));
//        };
//
//// Controller
//        UpdateChatChannelController controller = new UpdateChatChannelController(mockInteractor);
//        // Mock or real send message controller
////        SendMessageController mockSendMessageController = new SendMessageController(mockSendMessageInteractor);
//
//// 5. View
//        ChatChannelView view = new ChatChannelView(vm);
//        view.setUpdateChatChannelController(controller);
////        view.setSendMessageController(mockSendMessageController);
//
//// 6. Window
//        JFrame frame = new JFrame("Chat View Test");
//        frame.setContentPane(view);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.pack();
//        frame.setVisible(true);
//
//// 7. Trigger mock load
//        controller.execute("chat123");

        // ============================ACTUAL DATABASE =============================


//        Dotenv dotenv = Dotenv.configure()
//                .directory("./assets")
//                .filename("env")
//                .load();
//
//        String url = dotenv.get("DB_URL");
//        String user = dotenv.get("DB_USER");
//        String password = dotenv.get("DB_PASSWORD");
//
//        Connection connection = DriverManager.getConnection(url, user, password);
//        Statement statement = connection.createStatement();
//
//        String appId = dotenv.get("MSG_APP_ID");
//        String apiToken = dotenv.get("MSG_TOKEN");
//
//        User user1 = new User(1, "Alice", "abc", "English");
//        User user2 = new User(2, "Bob", "def", "English");
//
//
//        // Insert users
//        statement.executeUpdate(
//                "INSERT INTO \"user\" (id, username, created_at, preferred_language, password) " +
//                        "VALUES (1, 'Alice', NOW(), 'English', 'abc') " +
//                        "ON CONFLICT (id) DO UPDATE " +
//                        "SET password = EXCLUDED.password, " +
//                        "    preferred_language = EXCLUDED.preferred_language"
//        );
//        statement.executeUpdate(
//                "INSERT INTO \"user\" (id, username, created_at, preferred_language, password) " +
//                        "VALUES (2, 'Bob', NOW(), 'English', 'def') " +
//                        "ON CONFLICT (id) DO UPDATE " +
//                        "SET password = EXCLUDED.password, " +
//                        "    preferred_language = EXCLUDED.preferred_language"
//        );
//
//
////        // Insert chat channel
////        statement.executeUpdate("INSERT INTO chat_channel (chat_id, user1_id, user2_id, channel_url, name) " +
////                "VALUES (0, 1, 2, '\" + channelUrl + \"'\n, 'Example Chat') ON CONFLICT DO NOTHING");
//        ChannelCreator channelCreator = new ChannelCreator(appId);
//        String channelUrl = channelCreator.SendbirdChannelCreator(apiToken, "example", user1, user2);
//        System.out.println("Channel URL: " + channelUrl);
//
//        String sql = "INSERT INTO chat_channel (chat_id, user1_id, user2_id, channel_url, name) " +
//                "VALUES (?, ?, ?, ?, ?) " +
//                "ON CONFLICT DO NOTHING";
//
//        PreparedStatement ps = connection.prepareStatement(sql);
//        ps.setInt(1, 0);         // 0
//        ps.setInt(2, 1);         // 1
//        ps.setInt(3, 2);         // 2
//        ps.setString(4, channelUrl);   // sendbird_channel_...
//        ps.setString(5, "Example Chat");         // "Example Chat"
//
//        ps.executeUpdate();
//
//        System.out.println("Chat Channel Created: " + channelUrl);
//
//        // Insert messages
//        int messageId = 1;
//        int senderId = 1;
//        int receiverId = 2;
//        String content = "Hello, from Alice!";
//        String status = "sent";
//        Timestamp createdAt = Timestamp.valueOf(LocalDateTime.now());
//
//        String msg1 = "INSERT INTO text_message (message_id, channel_url, sender_id, receiver_id, content, time_sent, status) " +
//                "VALUES (" + messageId + ", '" + channelUrl + "', " + senderId + ", " + receiverId + ", '" + content + "', '" + createdAt + "', '" + status + "') " +
//                "ON CONFLICT DO NOTHING";
//
//        statement.executeUpdate(msg1);
//
//        System.out.println("Message inserted!");
//
//        System.out.println("Connected to SQL successfully!");

        // =====================================IMPLEMENT SEND =============================
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

        // 2. Presenter
        SessionManager sessionManager = new SessionManager();
        sessionManager.setMainUser(user1);
        sessionManager.setLoggedin(true);
        UpdateChatChannelPresenter presenter = new UpdateChatChannelPresenter(vm, sessionManager);
        ChatChannelPresenter presenter2 = new ChatChannelPresenter(messageViewModel);
        baseUIPresenter presenter3 = new baseUIPresenter(baseUIViewModel, viewManagerModel, addChatChannelViewModel, friendRequestViewModel, addContactViewModel);

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


        // 5. Controller
        UpdateChatChannelController controller = new UpdateChatChannelController(interactor);
        SendMessageController sendMessageController = new SendMessageController(messageInteractor);
        baseUIController baseUIController = new baseUIController(baseUIInteractor); // TODO: Fix naming

        // 6. View
        ChatChannelView view = new ChatChannelView(vm, user1.getUserID(), user2.getUserID(),
                user1.getUsername(), user2.getUsername(), channelUrl, controller, sendMessageController);
        UpdateChatChannelViewModel updateChatChannelViewModel = new  UpdateChatChannelViewModel();
        ChatChannelViewModel chatChannelViewModel = new ChatChannelViewModel("Chat");
        BaseUIView baseUIView = new BaseUIView(baseUIViewModel, baseUIController, updateChatChannelViewModel,
                chatChannelViewModel, viewManagerModel, (SessionManager) sessionManager, viewManager,
                sendMessageController, controller);
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
