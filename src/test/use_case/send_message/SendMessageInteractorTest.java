package use_case.send_message;

import sendbirdapi.MessageSender;
import data_access.*;
import entity.*;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.Test;
import org.sendbird.client.ApiClient;
import session.SessionManager;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SendMessageInteractorTest {

    @Test
    void successTest() throws SQLException {
        String url = "sendbird_group_channel_50257357_374e0405b4e79b2ba44e90858baac40e23f3a397";
        User sender = new User(1, "Alice", "abc", "English");
        User receiver = new User(2, "Bob", "def", "English");

        List<Message> messages = new ArrayList<>();
        messages.add(new TextMessage(
                1L, 1L, url,
                1, 2, "received",
                Timestamp.from(Instant.now()),
                "Test chat"
        ));

        InMemoryMessageDAO messageDAO = new InMemoryMessageDAO();
        for (Message m : messages) messageDAO.addMessage(m);

        InMemoryChatDAO chatDAO = new InMemoryChatDAO();
        DirectChatChannel chatChannel =
                DirectChatChannelFactory.createDirectChatChannel("Example Chat", sender, receiver, url, messages);
        chatDAO.addChat(chatChannel);

        SendMessageInputData inputData =
                new SendMessageInputData("Test chat", url, receiver.getUserID());

        SessionManager sessionManager = new SessionManager();
        sessionManager.setMainUser(sender);
        sessionManager.setLoggedIn(true);

        MessageSender mockSender = new MessageSender(new ApiClient()) {
            @Override
            public Long sendMessage(String message, String apiToken, String channelUrl, Integer senderId) {
                return 999L;
            }
        };

        SendMessageOutputBoundary presenter = new SendMessageOutputBoundary() {
            @Override
            public void prepareSendMessageSuccessView(SendMessageOutputData outputData) {
                assertEquals(sender.getUserID(), outputData.getSenderID());
                assertEquals(receiver.getUserID(), outputData.getReceiverID());

                // The interactor MUST have appended a message
                List<Message> msgs = chatChannel.getMessages();
                Message last = msgs.get(msgs.size() - 1);

                assertEquals("Test chat", last.getContent());
                assertEquals(outputData.getChannelUrl(), chatChannel.getChatUrl());
            }

            @Override
            public void prepareSendMessageFailView(String error) {
                fail("Should not fail");
            }
        };

        SendMessageInteractor interactor =
                new SendMessageInteractor(presenter, messageDAO, sessionManager, mockSender);

        interactor.execute(inputData);
    }

    @Test
    void failureTest() throws SQLException {
        String url = "sendbird_group_channel_50257357_374e0405b4e79b2ba44e90858baac40e23f3a397";
        User sender = new User(1, "Alice", "abc", "English");
        User receiver = new User(2, "Bob", "def", "English");

        List<Message> messages = new ArrayList<>();
        messages.add(new TextMessage(
                1L, 1L, url,
                1, 2,
                "received",
                Timestamp.from(Instant.now()),
                "Initial message"
        ));

        InMemoryMessageDAO messageDAO = new InMemoryMessageDAO();
        messageDAO.addMessage(messages.get(0));

        InMemoryChatDAO mockChatDAO = new InMemoryChatDAO();
        DirectChatChannel chatChannel = DirectChatChannelFactory.createDirectChatChannel(
                "Example Chat", sender, receiver, url, messages
        );
        mockChatDAO.addChat(chatChannel);

        SendMessageInputData inputData = new SendMessageInputData(
                "Test chat",
                url,
                receiver.getUserID()
        );

        SessionManager sessionManager = new SessionManager();
        sessionManager.setMainUser(sender);
        sessionManager.setLoggedIn(true);

        MessageSender failingSender = new MessageSender(new ApiClient()) {
            @Override
            public Long sendMessage(String message, String apiToken, String channelUrl, Integer senderId) {
                return null;   // simulate failure
            }
        };

        final boolean[] failCalled = {false};

        SendMessageOutputBoundary failPresenter = new SendMessageOutputBoundary() {
            @Override
            public void prepareSendMessageSuccessView(SendMessageOutputData outputData) {
                fail("Should NOT succeed");
            }

            @Override
            public void prepareSendMessageFailView(String error) {
                failCalled[0] = true;
                assertEquals("sendbird write fail", error);
            }
        };

        SendMessageInteractor interactor = new SendMessageInteractor(
                failPresenter, messageDAO, sessionManager, failingSender
        );

        interactor.execute(inputData);

        assertTrue(failCalled[0]);
        assertEquals(1, chatChannel.getMessages().size());  // no new messages
    }

    @Test
    void dbWriteFailureTest() {
        String url = "sendbird_group_channel_50257357_374e0405b4e79b2ba44e90858baac40e23f3a397";
        User sender = new User(1, "Alice", "abc", "English");
        User receiver = new User(2, "Bob", "def", "English");

        // session
        SessionManager sessionManager = new SessionManager();
        sessionManager.setMainUser(sender);
        sessionManager.setLoggedIn(true);

        // Throwing DAO to trigger the catch block
        InMemoryMessageDAO throwingDAO = new InMemoryMessageDAO() {
            @Override
            public <T> Long addMessage(Message<T> message) throws SQLException {
                throw new SQLException("Simulated DB write failure");
            }
        };

        // Mock message sender that *succeeds* (so failure MUST be DB write)
        MessageSender mockSender = new MessageSender(new ApiClient()) {
            @Override
            public Long sendMessage(String message, String apiToken, String channelUrl, Integer senderId) {
                return 999L;  // pretend SendBird succeeded
            }
        };

        SendMessageInputData inputData =
                new SendMessageInputData("Hello world", url, receiver.getUserID());

        final boolean[] failCalled = {false};

        SendMessageOutputBoundary presenter = new SendMessageOutputBoundary() {
            @Override
            public void prepareSendMessageSuccessView(SendMessageOutputData outputData) {
                fail("Should NOT succeed");
            }

            @Override
            public void prepareSendMessageFailView(String error) {
                failCalled[0] = true;
                assertEquals("DB write fail", error);
            }
        };

        SendMessageInteractor interactor =
                new SendMessageInteractor(presenter, throwingDAO, sessionManager, mockSender);

        interactor.execute(inputData);

        // Check that fail branch was triggered
        assertTrue(failCalled[0]);
    }
}