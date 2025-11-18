package use_case.add_chat_channel;

import data_access.ChatChannelDataAccessObject;
import data_access.ContactDataAccessObject;
import data_access.UserDataAccessObject;
import entity.Contact;
import entity.DirectChatChannel;
import entity.Message;
import entity.User;
import io.github.cdimascio.dotenv.Dotenv;
import session.Session;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class AddChatChannelInteractor implements AddChatChannelInputBoundary {
    AddChatChannelOutputBoundary presenter;
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

        //create response model for any new info needed for view
        AddChatChannelOutputData response = new AddChatChannelOutputData(
                request.getChatName(),
                "",
                request.getSenderID(),
                request.getReceiverID(),
                contactIDs);

        final List<String> currentUserChannels = chatChannelDataAccess.getChatURLsByUserId(currentUser.getUserID());
        final List<String> toAddUserChannels = chatChannelDataAccess.getChatURLsByUserId(toAdd.getUserID());
        boolean newChat = true;

        //check if chat is already made with contact user
        for (String currentUserChannel : currentUserChannels) {
            for (String toAddUserChannel : toAddUserChannels) {
                if (currentUserChannel.equals(toAddUserChannel)) {
                    newChat = false;
                }
            }
        }
        if (newChat) {
            //create direct channel entity
            DirectChatChannel newChannel = new DirectChatChannel(request.getChatName(),
                    currentUser, toAdd, "", new ArrayList<>());

            //add channel entity to db
            chatChannelDataAccess.addChat(newChannel);
        }else{
            newChat = false;
        }

        response.setNewChat(newChat);
        //call presenter to update view
        presenter.PresentChat(response);
    }
}
