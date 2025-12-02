package use_case.login;

import data_access.ChatChannelDataAccessObject;
import entity.DirectChatChannel;
import entity.Message;
import entity.User;
import interface_adapter.base_UI.baseUIState;
import interface_adapter.base_UI.baseUIViewModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class LoginInteractorTest {

    private FakeUserDAO userDAO;
    private FakeChatDAO chatDAO;
    private FakePresenter presenter;
    private baseUIViewModel baseUIViewModel;

    private LoginInteractor interactor;

    @BeforeEach
    void setup() {
        userDAO = new FakeUserDAO();
        chatDAO = new FakeChatDAO();
        presenter = new FakePresenter();
        baseUIViewModel = new baseUIViewModel("base");

        interactor = new LoginInteractor(
                userDAO,
                presenter,
                chatDAO,
                baseUIViewModel
        );
    }

    // ---------------------- SUCCESS ----------------------
    @Test
    void testSuccessfulLogin() throws SQLException {
        User user = new User(1, "john", "123", "EN");
        userDAO.addUser(user);

        // Add chats
        DirectChatChannel chat1 = new DirectChatChannel(
                "Chat A", user, user, "url1", new ArrayList<>()
        );
        DirectChatChannel chat2 = new DirectChatChannel(
                "Chat B", user, user, "url2", new ArrayList<>()
        );

        chatDAO.addChat(user, "url1", chat1);
        chatDAO.addChat(user, "url2", chat2);

        LoginInputData input = new LoginInputData("john", "123");
        interactor.logIn(input);

        assertTrue(presenter.successCalled);
        assertEquals("john", presenter.lastUser.getUsername());

        baseUIState state = baseUIViewModel.getState();
        assertEquals(2, state.getChatnames().size());
        assertEquals(Arrays.asList("Chat A", "Chat B"), state.getChatnames());
    }

    // ---------------------- FAILURE ----------------------
    @Test
    void testInvalidCredentials() {
        User user = new User(1, "john", "123", "EN");
        userDAO.addUser(user);

        LoginInputData input = new LoginInputData("john", "wrong");
        interactor.logIn(input);

        assertTrue(presenter.failCalled);
        assertEquals("Invalid username or password.", presenter.failMessage);
    }

    @Test
    void testValidateCredentialsSQLException() throws SQLException {
        userDAO.throwOnValidate = true;

        LoginInputData input = new LoginInputData("john", "123");
        interactor.logIn(input);

        // Check that failCalled is true
        assertTrue(presenter.failCalled);

        // Check the actual failure message (matches current behavior)
        assertEquals("Invalid username or password.", presenter.failMessage);
    }


    @Test
    void testGetUserFromNameSQLException() throws SQLException {
        User user = new User(1, "john", "123", "EN");
        userDAO.addUser(user);
        userDAO.throwOnGetUser = true;

        LoginInputData input = new LoginInputData("john", "123");
        interactor.logIn(input);

        assertTrue(presenter.failCalled);
        assertEquals("DB read fail", presenter.failMessage);
    }

    @Test
    void testGetChatURLsSQLException() throws SQLException {
        User user = new User(1, "john", "123", "EN");
        userDAO.addUser(user);

        chatDAO.throwOnGetURLs = true;

        LoginInputData input = new LoginInputData("john", "123");
        interactor.logIn(input);

        assertTrue(presenter.failCalled);
        assertEquals("DB read fail", presenter.failMessage);
    }

    @Test
    void testGetChatChannelSQLException() throws SQLException {
        User user = new User(1, "john", "123", "EN");
        userDAO.addUser(user);

        DirectChatChannel chat1 = new DirectChatChannel(
                "Chat A", user, user, "url1", new ArrayList<>()
        );
        chatDAO.addChat(user, "url1", chat1);
        chatDAO.throwOnGetChannel = true;

        LoginInputData input = new LoginInputData("john", "123");
        interactor.logIn(input);

        assertTrue(presenter.failCalled);
        assertEquals("DB read fail", presenter.failMessage);
    }

    @Test
    void testSwitchToSignupView() {
        interactor.switchToSignupView();
        assertTrue(presenter.switchToSignupCalled);
    }

    // ==========================================================
    // FAKE CLASSES FOR TESTING
    // ==========================================================

    private static class FakePresenter implements LoginOutputBoundary {
        boolean successCalled = false;
        boolean failCalled = false;
        boolean switchToSignupCalled = false;

        User lastUser;
        String failMessage;

        @Override
        public void prepareSuccessView(LoginOutputData data) {
            successCalled = true;
            lastUser = data.getUser();
        }

        @Override
        public void prepareFailureView(String message) {
            failCalled = true;
            failMessage = message;
        }

        @Override
        public void switchToSignUpView() {
            switchToSignupCalled = true;
        }

        @Override
        public void switchToHomePageView() {
            // no-op for testing
        }
    }

    private static class FakeUserDAO implements LoginUserDataAccessInterface {
        Map<String, User> users = new HashMap<>();
        boolean throwOnValidate = false;
        boolean throwOnGetUser = false;

        void addUser(User user) {
            users.put(user.getUsername(), user);
        }

        @Override
        public boolean validateCredentials(String username, String password) throws SQLException {
            if (throwOnValidate) throw new SQLException();
            return users.containsKey(username) && users.get(username).getPassword().equals(password);
        }

        @Override
        public User getUserFromName(String username) throws SQLException {
            if (throwOnGetUser) throw new SQLException();
            return users.get(username);
        }
    }

    private static class FakeChatDAO implements ChatChannelDataAccessObject {
        Map<Integer, List<String>> urlsByUser = new HashMap<>();
        Map<String, DirectChatChannel> channels = new HashMap<>();

        boolean throwOnGetURLs = false;
        boolean throwOnGetChannel = false;

        void addChat(User user, String url, DirectChatChannel channel) {
            urlsByUser.computeIfAbsent(user.getUserID(), k -> new ArrayList<>()).add(url);
            channels.put(url, channel);
        }

        @Override
        public List<String> getChatURLsByUserId(int userId) throws SQLException {
            if (throwOnGetURLs) throw new SQLException();
            return urlsByUser.getOrDefault(userId, new ArrayList<>());
        }

        @Override
        public DirectChatChannel getDirectChatChannelByURL(String url) throws SQLException {
            if (throwOnGetChannel) throw new SQLException();
            return channels.get(url);
        }

        @Override
        public DirectChatChannel getDirectChatChannelByID(int id) throws SQLException {
            return null; // no-op
        }

        @Override
        public int addChat(DirectChatChannel channel) throws SQLException {
            // no-op
            return 0;
        }

        @Override
        public Message getLastMessage(String chatUrl) throws SQLException {
            return null; // no-op
        }
    }
}
