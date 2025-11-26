package use_case.send_message;

import SendBirdAPI.MessageSender;
import data_access.*;
import entity.DirectChatChannel;
import entity.Message;
import entity.User;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.Test;
import session.Session;
import session.SessionManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class SendMessageInteractorTest {
    private Dotenv dotenv = Dotenv.configure()
            .directory("./assets")
            .filename("env")
            .load();

    @Test
    void successTest() throws SQLException {
        Integer senderId = 1; // Alice's ID
        Integer receiverId = 2; // Bob's ID
        Integer channelId = 0; // Example chat channel
        String url = dotenv.get("DB_URL");
        String user = dotenv.get("DB_USER");
        String password = dotenv.get("DB_PASSWORD");

        Connection connection = DriverManager.getConnection(url, user, password);

        ChatChannelDataAccessObject chatChannelDAO = new DBChatChannelDataAccessObject(connection);
        DirectChatChannel chatChannel = chatChannelDAO.getDirectChatChannelByID(channelId);

        SendMessageInputData inputData = new SendMessageInputData("Test chat", chatChannel.getChatURL(),
                senderId, receiverId);

        SendMessageOutputBoundary successPresenter = new SendMessageOutputBoundary() {

            @Override
            public void prepareSendMessageSuccessView(SendMessageOutputData outputData) {
                assertEquals(outputData.getSenderID(), senderId);
                assertEquals(outputData.getReceiverID(), receiverId);
                final List<Message> messages = chatChannel.getMessages();
                final String message = messages.get(messages.size() - 1).getContent().toString();
                assertEquals(message, inputData.getMessage());
                assertEquals(outputData.getChannelUrl(), chatChannel.getChatURL());
            }

            @Override
            public void prepareSendMessageFailView(String error) {
                fail("Use case failed");
            }
        };

        UserDataAccessObject userDAO = new DBUserDataAccessObject(connection);

        User testMainUser = userDAO.getUserFromID(senderId);

        MessageDataAccessObject messageDAO = new DBMessageDataAccessObject(connection);
        Session session = new SessionManager(testMainUser, true);

        MessageSender messageSender = new MessageSender(dotenv.get("MSG_APP_ID"));

        SendMessageInputBoundary interactor = new SendMessageInteractor(successPresenter, userDAO,
                messageDAO, session, messageSender);
        interactor.execute(inputData);
    }
}