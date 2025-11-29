package app;

import data_access.ChatChannelDataAccessObject;
import data_access.ContactDataAccessObject;
import data_access.UserDataAccessObject;
import entity.Contact;
import entity.DirectChatChannel;
import entity.Message;
import entity.User;
import interface_adapter.ViewManagerModel;
import interface_adapter.add_chat_channel.AddChatChannelViewModel;
import interface_adapter.add_contact.AddContactController;
import interface_adapter.add_contact.AddContactPresenter;
import interface_adapter.add_contact.AddContactViewModel;
import interface_adapter.base_UI.baseUIController;
import interface_adapter.base_UI.baseUIPresenter;
import interface_adapter.base_UI.baseUIViewModel;
import interface_adapter.friend_request.FriendRequestViewModel;
import interface_adapter.search_contact.SearchContactController;
import interface_adapter.search_contact.SearchContactPresenter;
import interface_adapter.search_contact.SearchContactViewModel;
import session.Session;
import session.SessionManager;
import use_case.add_contact.AddContactInteractor;
import use_case.baseUI.BaseUIInteractor;
import use_case.search_contact.SearchContactInteractor;
import use_case.search_contact.SearchContactUserDataAccessInterface;
import view.ProfileEditView;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SearchContactTest {
        public static void main(String[] args) throws SQLException {
                // 1. Setup Mock Data Access
                MockDataAccess dataAccess = new MockDataAccess();

                // 2. Setup Session (Mock a logged-in user)
                User currentUser = new User(1, "testUser", "password", "English");
                Session session = new SessionManager(currentUser, true);

                // 3. Setup ViewManagerModel
                ViewManagerModel viewManagerModel = new ViewManagerModel();

                // 4. Setup Search Contact Feature
                SearchContactViewModel searchContactViewModel = new SearchContactViewModel();
                SearchContactPresenter searchContactPresenter = new SearchContactPresenter(searchContactViewModel,
                                viewManagerModel);
                SearchContactInteractor searchContactInteractor = new SearchContactInteractor(dataAccess,
                                searchContactPresenter);
                SearchContactController searchContactController = new SearchContactController(searchContactInteractor);

                // 5. Setup Add Contact Feature
                AddContactViewModel addContactViewModel = new AddContactViewModel();
                AddContactPresenter addContactPresenter = new AddContactPresenter(addContactViewModel,
                                viewManagerModel);
                AddContactInteractor addContactInteractor = new AddContactInteractor(dataAccess, dataAccess,
                                addContactPresenter);
                AddContactController addContactController = new AddContactController(addContactInteractor);

                // 6. Setup BaseUI Controller
                baseUIViewModel baseUIViewModel = new baseUIViewModel("baseUIView");
                AddChatChannelViewModel addChatChannelViewModel = new AddChatChannelViewModel("add chat channel");
                FriendRequestViewModel friendRequestViewModel = new FriendRequestViewModel();

                baseUIPresenter baseUIPresenter = new baseUIPresenter(baseUIViewModel, viewManagerModel,
                                addChatChannelViewModel, friendRequestViewModel, addContactViewModel);
                BaseUIInteractor baseUIInteractor = new BaseUIInteractor(baseUIPresenter, dataAccess, dataAccess,
                                session);
                baseUIController baseUIController = new baseUIController(baseUIInteractor);

                // 7. Create the View
                ProfileEditView searchView = new ProfileEditView(
                                searchContactViewModel,
                                searchContactController,
                                addContactController,
                                baseUIController,
                                session);

                // 8. Create JFrame to display
                JFrame frame = new JFrame("Search Contact Test (Mock Data)");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(500, 600);
                frame.add(searchView);
                frame.setVisible(true);

                System.out.println("Search Contact Test Window Opened (Using Mock Data).");
                System.out.println("Try searching for 'Alice' or 'Bob'.");
        }

        // Mock Data Access Class
        static class MockDataAccess implements UserDataAccessObject, ContactDataAccessObject,
                        SearchContactUserDataAccessInterface, ChatChannelDataAccessObject {
                private List<User> users = new ArrayList<>();

                public MockDataAccess() {
                        users.add(new User(1, "testUser", "password", "English"));
                        users.add(new User(2, "Alice", "password", "English"));
                        users.add(new User(3, "Bob", "password", "French"));
                }

                @Override
                public List<User> searchUsers(String keyword) {
                        List<User> results = new ArrayList<>();
                        for (User user : users) {
                                if (user.getUsername().toLowerCase().contains(keyword.toLowerCase())) {
                                        results.add(user);
                                }
                        }
                        return results;
                }

                @Override
                public boolean existsByName(String username) {
                        for (User user : users) {
                                if (user.getUsername().equals(username))
                                        return true;
                        }
                        return false;
                }

                @Override
                public User getUserFromID(int UserID) {
                        return null;
                }

                @Override
                public List<User> getAllUsers() {
                        return users;
                }

                @Override
                public void save(User user) {
                        users.add(user);
                }

                @Override
                public User getUserFromName(String username) {
                        for (User user : users) {
                                if (user.getUsername().equals(username))
                                        return user;
                        }
                        return null;
                }

                @Override
                public void sendRequest(User sender, String receiverUsername) {
                        System.out.println("Mock: Friend request sent from " + sender.getUsername() + " to "
                                        + receiverUsername);
                }

                @Override
                public void updateUserContacts(User user, List<Contact> contacts) {
                        // Do nothing
                }

                @Override
                public void updateUserFriendRequests(User user, List<String> friendRequests) {
                        // Do nothing
                }

                @Override
                public DirectChatChannel getDirectChatChannelByURL(String channelURL) {
                        return null;
                }

                @Override
                public DirectChatChannel getDirectChatChannelByID(int channelID) {
                        return null;
                }

                @Override
                public int addChat(DirectChatChannel chat) {
                        return 0;
                }

                @Override
                public List<String> getChatURLsByUserId(int userId) {
                        return new ArrayList<>();
                }

                @Override
                public Message getLastMessage(String channelUrl) {
                        return null;
                }
        }
}
