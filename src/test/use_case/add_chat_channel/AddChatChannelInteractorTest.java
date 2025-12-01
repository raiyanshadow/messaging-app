package use_case.add_chat_channel;

import sendbirdapi.ChannelCreator;
import data_access.ChatChannelDataAccessObject;
import data_access.UserDataAccessObject;
import entity.DirectChatChannel;
import entity.Message;
import entity.User;
import org.junit.jupiter.api.Test;
import session.Session;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AddChatChannelInteractorTest {

    @Test
    void testCreateChannelNewChat() throws SQLException {
        // Arrange
        MockPresenter presenter = new MockPresenter();
        MockChatDAO chatDAO = new MockChatDAO();
        MockUserDAO userDAO = new MockUserDAO();
        MockSession session = new MockSession();
        MockChannelCreator channelCreator = new MockChannelCreator();

        // Set up users
        User currentUser = new User(1, "User1", "pass", "Eng");
        User receiver = new User(2, "User2", "pass", "Eng");

        session.setMainUser(currentUser);
        userDAO.addUser(receiver);

        AddChatChannelInteractor interactor = new AddChatChannelInteractor(
                presenter, chatDAO, userDAO, session, channelCreator
        );

        AddChatChannelInputData inputData = new AddChatChannelInputData(
                "User1", "New Chat", 1, 2
        );

        // Act
        interactor.CreateChannel(inputData);

        // Assert
        assertTrue(presenter.response.isNewChat());
        assertEquals("mock_url", presenter.response.getChatUrl());
        assertEquals("New Chat", presenter.response.getChatName());
        assertEquals(1, chatDAO.chats.size()); // Chat added to DAO
        assertEquals("mock_url", chatDAO.chats.get(0).getChatUrl());
    }

    @Test
    void testCreateChannelExistingChat() throws SQLException {
        // Arrange
        MockPresenter presenter = new MockPresenter();
        MockChatDAO chatDAO = new MockChatDAO();
        MockUserDAO userDAO = new MockUserDAO();
        MockSession session = new MockSession();
        MockChannelCreator channelCreator = new MockChannelCreator();

        User currentUser = new User(1, "User1", "pass", "Eng");
        User receiver = new User(2, "User2", "pass", "Eng");

        session.setMainUser(currentUser);
        userDAO.addUser(receiver);

        // Simulate existing chat
        String commonUrl = "existing_url";
        chatDAO.addChatUrl(1, commonUrl);
        chatDAO.addChatUrl(2, commonUrl);

        AddChatChannelInteractor interactor = new AddChatChannelInteractor(
                presenter, chatDAO, userDAO, session, channelCreator
        );

        AddChatChannelInputData inputData = new AddChatChannelInputData(
                "User1", "Duplicate Chat", 1, 2
        );

        // Act
        interactor.CreateChannel(inputData);

        // Assert
        assertFalse(presenter.response.isNewChat());
        assertEquals(0, chatDAO.chats.size()); // No new chat added
    }

    // --- Mocks ---

    static class MockPresenter implements AddChatChannelOutputBoundary {
        AddChatChannelOutputData response;
        @Override
        public void PresentChat(AddChatChannelOutputData response) {
            this.response = response;
        }
    }

    static class MockChatDAO implements ChatChannelDataAccessObject {
        List<DirectChatChannel> chats = new ArrayList<>();
        // Map user ID to list of chat URLs for testing overlap
        java.util.Map<Integer, List<String>> userChatUrls = new java.util.HashMap<>();

        void addChatUrl(int userId, String url) {
            userChatUrls.computeIfAbsent(userId, k -> new ArrayList<>()).add(url);
        }

        @Override
        public DirectChatChannel getDirectChatChannelByURL(String channelURL) { return null; }
        @Override
        public DirectChatChannel getDirectChatChannelByID(int channelID) { return null; }
        @Override
        public int addChat(DirectChatChannel chat) {
            chats.add(chat);
            return 0;
        }
        @Override
        public List<String> getChatURLsByUserId(int userId) {
            return userChatUrls.getOrDefault(userId, new ArrayList<>());
        }
        @Override
        public Message getLastMessage(String channelUrl) { return null; }
    }

    static class MockUserDAO implements UserDataAccessObject {
        List<User> users = new ArrayList<>();

        void addUser(User user) { users.add(user); }

        @Override public boolean existsByName(String username) { return false; }
        @Override
        public User getUserFromID(int UserID) {
            return users.stream().filter(u -> u.getUserID() == UserID).findFirst().orElse(null);
        }
        @Override public List<User> getAllUsers() { return users; }
        @Override public Integer save(User user) { return 0; }
        @Override public void deleteByUsername(String username) {}
        @Override public User getUserFromName(String username) { return null; }
        @Override public void sendRequest(User sender, String receiverUsername) {}
    }

    static class MockSession implements Session {
        User mainUser;
        boolean loggedIn;
        @Override public User getMainUser() { return mainUser; }
        @Override public void setMainUser(User mainUser) { this.mainUser = mainUser; }

        @Override
        public void setLoggedIn(boolean loggedIn) {
            this.loggedIn = loggedIn;
        }
    }

    static class MockChannelCreator extends ChannelCreator {
        public MockChannelCreator() { super(); }

        @Override
        public String SendbirdChannelCreator(String apiToken, String channelName, Integer senderId, Integer receiverId) {
            return "mock_url";
        }
    }
}