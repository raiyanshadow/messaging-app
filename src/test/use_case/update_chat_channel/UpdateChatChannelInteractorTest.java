package use_case.update_chat_channel;

import data.access.InMemoryChatDao;
import entity.*;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class UpdateChatChannelInteractorTest {



    @Test
    void successTest() throws SQLException {
        String url = "sendbird_group_channel_50257357_374e0405b4e79b2ba44e90858baac40e23f3a397";

        InMemoryChatDao mockChatDAO = new InMemoryChatDao();
        UpdateChatChannelInputData inputData = new UpdateChatChannelInputData(url);
        User sender = new User(1, "Alice", "abc", "English");
        User receiver = new User(2, "Bob", "def", "English");
        List<AbstractMessage> messages = new ArrayList<>();
        messages.add(new TextMessage((long) 1, (long) 1, url, 1, 2, "received",
                Timestamp.from(Instant.now()), "hi"));
        List<MessageDto> messageDtos = new ArrayList<>();
        for (AbstractMessage message: messages) {
            MessageDto messageDto = new MessageDto(message.getChannelUrl(), message.getSenderId(),
                    message.getReceiverId(), message.getTimestamp(), (String) message.getContent());
            messageDtos.add(messageDto);
        }
        DirectChatChannel chat = new DirectChatChannel("Example Chat", sender, receiver, url, messages);
        mockChatDAO.addChat(chat);

        UpdateChatChannelOutputBoundary successPresenter = new UpdateChatChannelOutputBoundary() {
            @Override
            public void prepareSuccessView(UpdateChatChannelOutputData chat) {
                assertEquals(url, chat.getChatURL());
                assertEquals(sender.getUsername(), chat.getUser1Username());
                assertEquals(receiver.getUsername(), chat.getUser2Username());
                assertEquals(sender.getUserID(), chat.getUser1ID());
                assertEquals(receiver.getUserID(), chat.getUser2ID());
                assertEquals("Example Chat", chat.getChatName());
                assertEquals(messageDtos.size(), chat.getMessages().size());
                assertEquals(messageDtos.get(0).getChannelUrl(), chat.getMessages().get(0).getChannelUrl());
                assertEquals(messageDtos.get(0).getSenderID(), chat.getMessages().get(0).getSenderID());
                assertEquals(messageDtos.get(0).getReceiverID(), chat.getMessages().get(0).getReceiverID());
                assertEquals(messageDtos.get(0).getTimestamp(), chat.getMessages().get(0).getTimestamp());
                assertEquals(messageDtos.get(0).getContent(), chat.getMessages().get(0).getContent());
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("Use case failure is unexpected");
            }
        };

        UpdateChatChannelInputBoundary interactor = new UpdateChatChannelInteractor(mockChatDAO, successPresenter);
        interactor.execute(inputData);
    }

    @Test
    void failureChatURLNotFoundTest() throws SQLException {
        UpdateChatChannelInputData inputData = new UpdateChatChannelInputData("wrongURL");
        List<AbstractMessage> messages = new ArrayList<>();

        User sender = new User(1, "Alice", "abc", "English");
        User receiver = new User(2, "Bob", "def", "English");
        InMemoryChatDao mockChatDAO = new InMemoryChatDao();
        DirectChatChannel chat = DirectChatChannelFactory.createDirectChatChannel("Failure chat", sender,
                receiver, "correctURL", messages);
        mockChatDAO.addChat(chat);

        UpdateChatChannelOutputBoundary failurePresenter = new UpdateChatChannelOutputBoundary() {
            @Override
            public void prepareSuccessView(UpdateChatChannelOutputData chat) {
                fail("Use case success is unexpected");
            }
            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("Chat not found", errorMessage);
            }
        };

        UpdateChatChannelInputBoundary interactor = new UpdateChatChannelInteractor(mockChatDAO, failurePresenter);
        interactor.execute(inputData);
    }

    @Test
    void failureChatURLEmpty() throws SQLException {
        UpdateChatChannelInputData inputData = new UpdateChatChannelInputData("");
        List<AbstractMessage> messages = new ArrayList<>();

        User sender = new User(1, "Alice", "abc", "English");
        User receiver = new User(2, "Bob", "def", "English");
        InMemoryChatDao mockChatDAO = new InMemoryChatDao();
        DirectChatChannel chat = DirectChatChannelFactory.createDirectChatChannel("Failure chat empty", sender,
                receiver, "", messages);
        mockChatDAO.addChat(chat);

        UpdateChatChannelOutputBoundary failurePresenter = new UpdateChatChannelOutputBoundary() {
            @Override
            public void prepareSuccessView(UpdateChatChannelOutputData chat) {
                fail("Use case success is unexpected");
            }
            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("Chat URL is empty", errorMessage);
            }
        };

        UpdateChatChannelInputBoundary interactor = new UpdateChatChannelInteractor(mockChatDAO, failurePresenter);
        interactor.execute(inputData);
    }

    @Test
    void failureChatURLIsNullTest() throws SQLException {
        UpdateChatChannelInputData inputData = new UpdateChatChannelInputData(null);
        List<AbstractMessage> messages = new ArrayList<>();

        User sender = new User(1, "Alice", "abc", "English");
        User receiver = new User(2, "Bob", "def", "English");
        InMemoryChatDao mockChatDAO = new InMemoryChatDao();
        DirectChatChannel chat = DirectChatChannelFactory.createDirectChatChannel("Failure chat null",
                sender, receiver, "correctURL", messages);
        mockChatDAO.addChat(chat);

        UpdateChatChannelOutputBoundary failurePresenter = new UpdateChatChannelOutputBoundary() {
            @Override
            public void prepareSuccessView(UpdateChatChannelOutputData chat) {
                fail("Use case success is unexpected");
            }
            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("Chat URL is null", errorMessage);
            }
        };

        UpdateChatChannelInputBoundary interactor = new UpdateChatChannelInteractor(mockChatDAO, failurePresenter);
        interactor.execute(inputData);
    }
}
