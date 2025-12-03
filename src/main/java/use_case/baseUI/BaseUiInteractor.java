package use_case.baseUI;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.Contact;
import entity.DirectChatChannel;
import session.Session;

/**
 * Interactor for the base UI use case.
 */
public class BaseUiInteractor implements BaseUiInputBoundary {
    private BaseUiOutputBoundary presenter;
    private BaseUiChatChannelDataAccessInterface chatChannelDataAccess;
    private BaseUiContactDataAccessInterface contactDataAccess;
    private Session sessionManager;

    public BaseUiInteractor(BaseUiOutputBoundary presenter, BaseUiChatChannelDataAccessInterface chatChannelDataAccess,
                            Session sessionManager,
                            BaseUiContactDataAccessInterface contactDataAccess) {
        this.presenter = presenter;
        this.chatChannelDataAccess = chatChannelDataAccess;
        this.sessionManager = sessionManager;
        this.contactDataAccess = contactDataAccess;
    }

    @Override
    public void getUserChats(BaseUiInputData request) throws SQLException {
        final List<String> chatUrls = chatChannelDataAccess.getChatUrlsByUserId(sessionManager.getMainUser()
                .getUserID());
        final List<String> chatnames = new ArrayList<>();
        final List<DirectChatChannel> chatEntities = new ArrayList<>();
        for (String chatUrl : chatUrls) {
            final DirectChatChannel chat = chatChannelDataAccess.getDirectChatChannelByUrl(chatUrl);
            chatnames.add(chat.getChatName());
            chatEntities.add(chat);
        }
        sessionManager.getMainUser().setUserChats(chatnames);
        final BaseUiOutputData response = new BaseUiOutputData(chatnames, chatEntities);
        presenter.displayUi(response);
    }

    @Override
    public void displayAddChat(BaseUiInputData request) {
        final List<Contact> contacts = contactDataAccess.getContacts(sessionManager.getMainUser());
        sessionManager.getMainUser().setContacts(contacts);
        presenter.displayAddChat();
    }

    @Override
    public void switchToFriendRequestView(BaseUiInputData request) throws SQLException {
        final List<String> friendRequests = new ArrayList<>();
        contactDataAccess.updateUserFriendRequests(sessionManager.getMainUser(), friendRequests);
        sessionManager.getMainUser().setFriendRequests(friendRequests);
        presenter.displayFriends();
    }

    @Override
    public void switchToAddContact(BaseUiInputData request) throws SQLException {
        final List<Contact> contacts = new ArrayList<>();
        contactDataAccess.updateUserContacts(sessionManager.getMainUser(), contacts);
        sessionManager.getMainUser().setContacts(contacts);
        presenter.displayAddContact();
    }

    @Override
    public void switchToProfileEdit(BaseUiInputData request) throws SQLException {
        presenter.displayProfileEditView();
    }
}
