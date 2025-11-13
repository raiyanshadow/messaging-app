package use_case.add_chat_channel;

import data_access.ChatChannelDataAccessObject;
import data_access.ContactDataAccessObject;
import data_access.UserDataAccessObject;
import entity.Contact;
import entity.DirectChatChannel;
import entity.User;
import session.Session;

import java.sql.SQLException;
import java.util.List;

public class AddChatChannelInteractor implements AddChatChannelInputBoundary {
    AddChatChannelOutputBoundary presenter;
    List<Object> save_chat;
    ChatChannelDataAccessObject chatChannelDataAccess;
    UserDataAccessObject userDataAccess;
    ContactDataAccessObject contactDataAccess;
    Session sessionManager;

    AddChatChannelInteractor(AddChatChannelOutputBoundary presenter,
                             ChatChannelDataAccessObject chatChannelAccessObject,
                             UserDataAccessObject userDataAccess,
                             ContactDataAccessObject contactDataAccess,
                             Session sessionManager) {
        this.presenter = presenter;
        this.chatChannelDataAccess = chatChannelDataAccess;
        this.userDataAccess = userDataAccess;
        this.sessionManager = sessionManager;
    }

    // TODO: Add chat with Sendbird API as well
    @Override
    public void CreateChannel(AddChatChannelInputData request) throws SQLException {
        final User currentUser = sessionManager.getMainUser();
        final User toAdd = userDataAccess.getUserFromID(request.getReceiverID());
        final List<Integer> contactIDs = currentUser.getContactIDs();

        if (!contactIDs.contains(toAdd.getUserID())) {
            contactIDs.add(toAdd.getUserID());
            Contact newContact = new Contact(currentUser, toAdd);
            currentUser.getContacts().add(newContact);
            currentUser.setContacts(currentUser.getContacts());
            sessionManager.setMainUser(currentUser);
            contactDataAccess.updateUserContacts(currentUser, currentUser.getContacts());
        }


        DirectChatChannel newChat = new DirectChatChannel(request.getChatID(), request.getChatName(),
                currentUser, toAdd);

        chatChannelDataAccess.addChat(newChat);

        AddChatChannelOutputData response = new AddChatChannelOutputData(
                request.getChatName(),
                request.getChatID(),
                request.getSenderID(),
                request.getReceiverID(),
                contactIDs);

        presenter.PresentChat(response);

    }
}
