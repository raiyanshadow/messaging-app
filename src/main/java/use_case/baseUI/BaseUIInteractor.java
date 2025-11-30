package use_case.baseUI;

import data_access.ChatChannelDataAccessObject;
import data_access.ContactDataAccessObject;
import data_access.DBContactDataAccessObject;
import data_access.UserDataAccessObject;
import entity.Contact;
import entity.DirectChatChannel;
import entity.User;
import session.Session;

import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BaseUIInteractor implements BaseUIInputBoundary{
    BaseUIOutputBoundary presenter;
    ChatChannelDataAccessObject chatChannelDataAccess;
    UserDataAccessObject userDataAccess;
    ContactDataAccessObject contactDataAccess;
    Session sessionManager;

    public BaseUIInteractor(BaseUIOutputBoundary presenter, ChatChannelDataAccessObject chatChannelDataAccess,
                            UserDataAccessObject userDataAccess, Session sessionManager, ContactDataAccessObject contactDataAccess) {
        this.presenter = presenter;
        this.chatChannelDataAccess = chatChannelDataAccess;
        this.userDataAccess = userDataAccess;
        this.sessionManager = sessionManager;
        this.contactDataAccess = contactDataAccess;
    }

    @Override
    public void GetUserChats(BaseUIInputData request) throws SQLException {
        List<String> chatURLs = chatChannelDataAccess.getChatURLsByUserId(sessionManager.getMainUser()
                .getUserID());
        List<String> chatnames = new ArrayList<>();
        List<DirectChatChannel> chatEntities = new ArrayList<>();
        for (String chatURL : chatURLs){
            DirectChatChannel chat = chatChannelDataAccess.getDirectChatChannelByURL(chatURL);
            chatnames.add(chat.getChatName());
            chatEntities.add(chat);
        }
        sessionManager.getMainUser().setUserChats(chatnames);
        BaseUIOutputData response = new BaseUIOutputData(chatnames, chatEntities);
        presenter.DisplayUI(response);
    }

    @Override
    public void displayAddChat(BaseUIInputData request) {
        List<Contact> contacts = contactDataAccess.getContacts(sessionManager.getMainUser());
        for (Contact contact : contacts){
            System.out.println(contact.getUser().getUsername() + " with " + contact.getContact().getUsername());
        }
        sessionManager.getMainUser().setContacts(contacts);
        presenter.DisplayAddChat();
    }

    @Override
    public void switchToFriendRequestView(BaseUIInputData request) throws SQLException {
        List<String> friendRequests = new ArrayList<>();
        contactDataAccess.updateUserFriendRequests(sessionManager.getMainUser(), friendRequests);
        sessionManager.getMainUser().setFriendRequests(friendRequests);
        presenter.DisplayFriends();
    }

    @Override
    public void switchToAddContact(BaseUIInputData request) throws SQLException {
        List<Contact> contacts = new ArrayList<>();
        contactDataAccess.updateUserContacts(sessionManager.getMainUser(), contacts);
        sessionManager.getMainUser().setContacts(contacts);
        presenter.DisplayAddContact();
    }

    @Override
    public void switchToProfileEdit(BaseUIInputData request) throws SQLException {
        presenter.DisplayProfileEditView();
    }
}