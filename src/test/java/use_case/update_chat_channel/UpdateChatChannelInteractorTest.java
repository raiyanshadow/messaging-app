package use_case.update_chat_channel;

import data_access.InMemoryChatDAO;
import entity.DirectChatChannel;
import entity.DirectChatChannelFactory;
import entity.Message;
import entity.User;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class UpdateChatChannelInteractorTest {



    @Test
    void successTest() throws SQLException {
        String url = "sendbird_group_channel_50257357_374e0405b4e79b2ba44e90858baac40e23f3a397";

        InMemoryChatDAO mockChatDAO = new InMemoryChatDAO();
        UpdateChatChannelInputData inputData = new UpdateChatChannelInputData(url);
        User sender = new User(1, "Alice", "abc", "English");
        User receiver = new User(2, "Bob", "def", "English");
        List<Message> messages = new ArrayList<>();
        DirectChatChannel chat = new DirectChatChannel("Example Chat", sender, receiver, url, messages);
        mockChatDAO.addChat(chat);

        UpdateChatChannelOutputBoundary successPresenter = new UpdateChatChannelOutputBoundary() {
            @Override
            public void prepareSuccessView(UpdateChatChannelOutputData chat) {
                assertEquals(url, chat.getChatURL());
                assertEquals(sender, chat.getUser1());
                assertEquals(receiver, chat.getUser2());
                assertEquals("Example Chat", chat.getChatName());
                assertEquals(messages, chat.getMessages());
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
        List<Message> messages = new ArrayList<>();

        User sender = new User(1, "Alice", "abc", "English");
        User receiver = new User(2, "Bob", "def", "English");
        InMemoryChatDAO mockChatDAO = new InMemoryChatDAO();
        DirectChatChannelFactory factory = new DirectChatChannelFactory();
        DirectChatChannel chat = factory.createDirectChatChannel("Failure chat", sender, receiver, "correctURL", messages);
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
    void failureChatURLIsNullTest() throws SQLException {
        UpdateChatChannelInputData inputData = new UpdateChatChannelInputData(null);
        List<Message> messages = new ArrayList<>();

        User sender = new User(1, "Alice", "abc", "English");
        User receiver = new User(2, "Bob", "def", "English");
        InMemoryChatDAO mockChatDAO = new InMemoryChatDAO();
        DirectChatChannelFactory factory = new DirectChatChannelFactory();
        DirectChatChannel chat = factory.createDirectChatChannel("Failure chat null", sender, receiver, "correctURL", messages);
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
