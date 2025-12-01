package use_case.send_message;

import SendBirdAPI.MessageSender;
import data_access.*;
import entity.*;
import io.github.cdimascio.dotenv.Dotenv;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.openapitools.client.model.SendbirdMessageResponse;
import org.openapitools.client.model.SendbirdUser;
import org.sendbird.client.ApiClient;
import org.sendbird.client.Configuration;
import session.Session;
import session.SessionManager;
import use_case.update_chat_channel.MessageDTO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
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

        assertEquals(true, failCalled[0]);
        assertEquals(1, chatChannel.getMessages().size());  // no new messages
    }
}