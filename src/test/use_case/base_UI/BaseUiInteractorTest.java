package use_case.base_UI;

import data.access.UserDataAccessObject;
import entity.Contact;
import entity.DirectChatChannel;
import entity.User;
import org.junit.jupiter.api.Test;
import session.Session;
import use_case.baseUI.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BaseUiInteractorTest {

    @Test
    void testGetUserChats() throws SQLException {
        // Arrange
        MockPresenter presenter = new MockPresenter();
        MockChatDAO chatDAO = new MockChatDAO();
        MockSession session = new MockSession();
        MockContactDAO contactDAO = new MockContactDAO();

        User mainUser = new User(1, "Main", "pass", "Eng");
        session.setMainUser(mainUser);

        // Setup chats
        chatDAO.userChatUrls.add("chat1_url");
        DirectChatChannel channel = new DirectChatChannel("Chat 1", mainUser, new User(2, "Other", "p", "e"), "chat1_url", new ArrayList<>());
        chatDAO.channels.add(channel);

        BaseUiInteractor interactor = new BaseUiInteractor(
                presenter, chatDAO, session, contactDAO
        );

        BaseUiInputData input = new BaseUiInputData();

        // Act
        interactor.getUserChats(input);

        // Assert
        assertNotNull(presenter.lastOutputData);
        assertEquals(1, presenter.lastOutputData.getChatNames().size());
        assertEquals("Chat 1", presenter.lastOutputData.getChatNames().get(0));
        assertEquals(1, mainUser.getUserChats().size());
    }

    @Test
    void testDisplayAddChat() {
        // Arrange
        MockPresenter presenter = new MockPresenter();
        MockChatDAO chatDAO = new MockChatDAO();
        MockUserDAO userDAO = new MockUserDAO();
        MockSession session = new MockSession();
        MockContactDAO contactDAO = new MockContactDAO();

        User mainUser = new User(1, "Main", "pass", "Eng");
        session.setMainUser(mainUser);

        User contactUser = new User(2, "Contact", "pass", "Eng");
        Contact contact = new Contact(mainUser, contactUser);
        contactDAO.contacts.add(contact);

        BaseUiInteractor interactor = new BaseUiInteractor(
                presenter, chatDAO, session, contactDAO
        );
        BaseUiInputData input = new BaseUiInputData();

        // Act
        interactor.displayAddChat(input);

        // Assert
        assertTrue(presenter.displayAddChatCalled);
        assertEquals(1, mainUser.getContacts().size());
        assertEquals("Contact", mainUser.getContacts().get(0).getContact().getUsername());
    }

    @Test
    void testSwitchToFriendRequestView() throws SQLException {
        // Arrange
        MockPresenter presenter = new MockPresenter();
        MockSession session = new MockSession();
        MockContactDAO contactDAO = new MockContactDAO();
        User mainUser = new User(1, "Main", "pass", "Eng");
        session.setMainUser(mainUser);

        BaseUiInteractor interactor = new BaseUiInteractor(
                presenter, new MockChatDAO(), session, contactDAO
        );

        // Act
        interactor.switchToFriendRequestView(new BaseUiInputData());

        // Assert
        assertTrue(presenter.displayFriendsCalled);
        // Ensure contactDAO updated the user's friend requests (mock implementation detail)
        assertNotNull(mainUser.getFriendRequests());
    }

    @Test
    void testSwitchToAddContact() throws SQLException {
        // Arrange
        MockPresenter presenter = new MockPresenter();
        MockSession session = new MockSession();
        MockContactDAO contactDAO = new MockContactDAO();
        User mainUser = new User(1, "Main", "pass", "Eng");
        session.setMainUser(mainUser);

        BaseUiInteractor interactor = new BaseUiInteractor(
                presenter, new MockChatDAO(), session, contactDAO
        );

        // Act
        interactor.switchToAddContact(new BaseUiInputData());

        // Assert
        assertTrue(presenter.displayAddContactCalled);
        assertNotNull(mainUser.getContacts());
    }

    @Test
    void testSwitchToProfileEdit() throws SQLException {
        MockPresenter presenter = new MockPresenter();
        BaseUiInteractor interactor = new BaseUiInteractor(
                presenter, new MockChatDAO(), new MockSession(), new MockContactDAO()
        );

        interactor.switchToProfileEdit(new BaseUiInputData());

        assertTrue(presenter.displayProfileEditViewCalled);
    }

    // --- Mocks ---

    static class MockPresenter implements BaseUiOutputBoundary {
        BaseUiOutputData lastOutputData;
        boolean displayAddChatCalled = false;
        boolean displayFriendsCalled = false;
        boolean displayAddContactCalled = false;
        boolean displayProfileEditViewCalled = false;

        @Override
        public void displayUi(BaseUiOutputData response) {
            this.lastOutputData = response;
        }

        @Override
        public void displayAddChat() {
            displayAddChatCalled = true;
        }

        @Override
        public void displayFriends() {
            displayFriendsCalled = true;
        }

        @Override
        public void displayAddContact() {
            displayAddContactCalled = true;
        }

        @Override
        public void displayProfileEditView() {
            displayProfileEditViewCalled = true;
        }
    }

    static class MockChatDAO implements BaseUiChatChannelDataAccessInterface {
        List<String> userChatUrls = new ArrayList<>();
        List<DirectChatChannel> channels = new ArrayList<>();

        public DirectChatChannel getDirectChatChannelByUrl(String channelURL) {
            return channels.stream().filter(c -> c.getChatUrl().equals(channelURL)).findFirst().orElse(null);
        }

        public List<String> getChatUrlsByUserId(int userId) {
            return userChatUrls;
        }
    }

    static class MockUserDAO implements UserDataAccessObject {
        @Override public boolean existsByName(String username) { return false; }
        @Override public User getUserFromID(int UserID) { return null; }
        @Override public List<User> getAllUsers() { return null; }
        @Override public Integer save(User user) { return 0; }
        @Override public void deleteByUsername(String username) {}
        @Override public User getUserFromName(String username) { return null; }
        @Override public void sendRequest(User sender, String receiverUsername) {}
    }

    static class MockContactDAO implements BaseUiContactDataAccessInterface {
        List<Contact> contacts = new ArrayList<>();
        List<String> friendRequests = new ArrayList<>();

        @Override
        public void updateUserContacts(User user, List<Contact> contacts) {
            // In a real DB, this would fetch from DB and put into 'contacts' list
            // For mock, we'll just clear and add our mock data
            contacts.clear();
            contacts.addAll(this.contacts);
        }

        @Override
        public void updateUserFriendRequests(User user, List<String> friendRequests) {
            friendRequests.clear();
            friendRequests.addAll(this.friendRequests);
        }

        @Override
        public List<Contact> getContacts(User user) {
            return new ArrayList<>(contacts);
        }
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
}