package use_case.send_message;

import SendBirdAPI.MessageSender;
import data_access.*;
import entity.DirectChatChannel;
import entity.Message;
import entity.User;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.Test;
import org.sendbird.client.ApiClient;
import org.sendbird.client.Configuration;
import session.Session;
import session.SessionManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class SendMessageInteractorTest {
    private final Dotenv dotenv = Dotenv.configure()
            .directory("./assets")
            .filename("env")
            .load();

    @Test
    void successTest() throws SQLException {
        int senderId = 1; // Alice's ID
        int receiverId = 2; // Bob's ID
        int channelId = 0; // Example chat channel
        String url = dotenv.get("DB_URL");
        String user = dotenv.get("DB_USER");
        String password = dotenv.get("DB_PASSWORD");

        Connection connection = DriverManager.getConnection(url, user, password);

        ChatChannelDataAccessObject chatChannelDAO = new DBChatChannelDataAccessObject(connection);
        DirectChatChannel chatChannel = chatChannelDAO.getDirectChatChannelByID(channelId);

        SendMessageInputData inputData = new SendMessageInputData("Test chat", chatChannel.getChatUrl(),
                receiverId);

        SendMessageOutputBoundary successPresenter = new SendMessageOutputBoundary() {

            @Override
            public void prepareSendMessageSuccessView(SendMessageOutputData outputData) {
                assertEquals(senderId, outputData.getSenderID());
                assertEquals(receiverId, outputData.getReceiverID());
                final List<Message<String>> messages = chatChannel.getMessages();
                final String message = messages.get(messages.size() - 1).getContent();
                assertEquals(message, inputData.getMessage());
                assertEquals(outputData.getChannelUrl(), chatChannel.getChatUrl());
            }

            @Override
            public void prepareSendMessageFailView(String error) {
                fail("Use case failed");
            }
        };

        UserDataAccessObject userDAO = new DBUserDataAccessObject(connection);

        User testMainUser = userDAO.getUserFromID(senderId);

        DBMessageDataAccessObject messageDAO = new DBMessageDataAccessObject(connection);
        Session session = new SessionManager(testMainUser, true);

        ApiClient defaultClient = Configuration.getDefaultApiClient().setBasePath(
                "https://api-" + dotenv.get("MSG_APP_ID") + ".sendbird.com"
        );
        MessageSender messageSender = new MessageSender(defaultClient);

        SendMessageInputBoundary interactor = new SendMessageInteractor(successPresenter,
                messageDAO, session, messageSender);
        interactor.execute(inputData);
    }
}