package app;

/**
 * This is just to test the view. will delete after
 */

import javax.swing.*;

import SendBirdAPI.ChannelCreator;
import data_access.DBChatChannelDataAccessObject;
import entity.Message;
import entity.MessageFactory;
import entity.User;
import interface_adapter.chat_channel.MessageState;
import interface_adapter.chat_channel.SendMessageController;
import interface_adapter.update_chat_channel.UpdateChatChannelController;
import interface_adapter.update_chat_channel.UpdateChatChannelPresenter;
import interface_adapter.update_chat_channel.UpdateChatChannelViewModel;
import interface_adapter.update_chat_channel.UpdateChatChannelState;
import interface_adapter.chat_channel.MessageViewModel;
import io.github.cdimascio.dotenv.Dotenv;
import use_case.send_message.SendMessageInputBoundary;
import use_case.send_message.SendMessageInputData;
import use_case.send_message.SendMessageOutputData;
import use_case.update_chat_channel.UpdateChatChannelInputBoundary;
import use_case.update_chat_channel.UpdateChatChannelInputData;
import use_case.update_chat_channel.UpdateChatChannelInteractor;
import data_access.DBChatChannelDataAccessObject;
import use_case.update_chat_channel.UpdateChatChannelOutputData;
import view.ChatChannelView;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ChatChannelViewTest {
    public static void main(String[] args) throws SQLException {

        // 1. Create ViewModel
        UpdateChatChannelViewModel vm = new UpdateChatChannelViewModel();

// 2. Create Presenter
        UpdateChatChannelPresenter presenter = new UpdateChatChannelPresenter(vm);

// 3. Create mock interactor
        // 3. Create mock interactor
        UpdateChatChannelInputBoundary mockInteractor = chatURL -> {
            User alice = new User(1, "Alice", "abc", "English");
            User bob = new User(2, "Bob", "def", "English");

            List<Message> messages = new ArrayList<>();
            messages.add(MessageFactory.createTextMessage(1, "chat123", alice, bob, "sent", Timestamp.valueOf(LocalDateTime.now()), "Hello world!"));
            messages.add(MessageFactory.createTextMessage(2, "chat123", bob, alice, "sent", Timestamp.valueOf(LocalDateTime.now()), "Hi Alice!"));
            messages.add(MessageFactory.createTextMessage(1, "chat123", alice, bob, "sent", Timestamp.valueOf(LocalDateTime.now()), "are you taking csc207"));
            messages.add(MessageFactory.createTextMessage(2, "chat123", bob, alice, "sent", Timestamp.valueOf(LocalDateTime.now()), "yes"));
            messages.add(MessageFactory.createTextMessage(1, "chat123", alice, bob, "sent", Timestamp.valueOf(LocalDateTime.now()), "when is your class"));
            messages.add(MessageFactory.createTextMessage(2, "chat123", bob, alice, "sent", Timestamp.valueOf(LocalDateTime.now()), "today"));

            presenter.prepareSuccessView(new UpdateChatChannelOutputData("Example Chat", "chat123", alice, bob, messages));
        };

// Controller
        UpdateChatChannelController controller = new UpdateChatChannelController(mockInteractor);
        // Mock or real send message controller
//        SendMessageController mockSendMessageController = new SendMessageController(mockSendMessageInteractor);

// 5. View
        ChatChannelView view = new ChatChannelView(vm);
        view.setUpdateChatChannelController(controller);
//        view.setSendMessageController(mockSendMessageController);

// 6. Window
        JFrame frame = new JFrame("Chat View Test");
        frame.setContentPane(view);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

// 7. Trigger mock load
        controller.execute("chat123");

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
//        statement.executeUpdate("INSERT INTO \"user\" (id, username, created_at, preferred_language, password) " +
//                "VALUES (1, 'Alice', NOW(), 'English', 'abc') ON CONFLICT DO NOTHING");
//        statement.executeUpdate("INSERT INTO \"user\" (id, username, created_at, preferred_language, password) " +
//                "VALUES (2, 'Bob', NOW(), 'English', 'def') ON CONFLICT DO NOTHING");
//
//        // Insert chat channel
//        statement.executeUpdate("INSERT INTO chat_channel (chat_id, user1_id, user2_id, channel_url, name) " +
//                "VALUES (0, 1, 2, 'chat123', 'Example Chat') ON CONFLICT DO NOTHING");
//
//        System.out.println("Connected to SQL successfully!");
//
//        String appId = dotenv.get("MSG_APP_ID");
//        String apiToken = dotenv.get("MSG_TOKEN");
//
//        User user1 = new User(1, "Alice", "abc", "English");
//        User user2 = new User(2, "Bob", "def", "English");
//
//        ChannelCreator channelCreator = new ChannelCreator(appId);
//        String channelUrl = channelCreator.SendbirdChannelCreator(apiToken, "example", user1, user2);
//        System.out.println("Channel URL: " + channelUrl);
//
//        // 1. ViewModel
//        UpdateChatChannelViewModel vm = new UpdateChatChannelViewModel();
//
//        // 2. Presenter
//        UpdateChatChannelPresenter presenter = new UpdateChatChannelPresenter(vm);
//
//        // 2. Make your Data Access Object
//        DBChatChannelDataAccessObject gateway =
//                new DBChatChannelDataAccessObject(connection);
//
//        // 4. Interactor
//        UpdateChatChannelInputBoundary interactor =
//                new UpdateChatChannelInteractor(gateway, presenter);
//
//        // 5. Controller
//        UpdateChatChannelController controller =
//                new UpdateChatChannelController(interactor);
//
//        // 6. View
//        ChatChannelView view = new ChatChannelView(vm);
//        view.setUpdateChatChannelController(controller);   // add a setter in your view for the controller!
//
//        // 7. Create Window
//        JFrame frame = new JFrame("TEST CHAT VIEW");
//        frame.setContentPane(view);   // Your view MUST extend JPanel
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.pack();
//        frame.setVisible(true);
//
//        // 8. Trigger test load
//        controller.execute("chat123"); // Must exist in your DB

    }
}
