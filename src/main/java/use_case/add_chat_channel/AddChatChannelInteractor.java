package use_case.add_chat_channel;

import SendBirdAPI.ChannelCreator;
import data_access.ChatChannelDataAccessObject;
import data_access.UserDataAccessObject;
import entity.DirectChatChannel;
import entity.User;
import io.github.cdimascio.dotenv.Dotenv;
import session.Session;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddChatChannelInteractor implements AddChatChannelInputBoundary {
    AddChatChannelOutputBoundary presenter;
    ChatChannelDataAccessObject chatChannelDataAccess;
    UserDataAccessObject userDataAccess;
    Session sessionManager;
    ChannelCreator channelCreator;
    private Dotenv dotenv = Dotenv.configure()
            .directory("./assets")
            .filename("env")
            .load();

    public AddChatChannelInteractor(AddChatChannelOutputBoundary presenter,
                                    ChatChannelDataAccessObject chatChannelDataAccess,
                                    UserDataAccessObject userDataAccess,
                                    Session sessionManager, ChannelCreator channelCreator) {
        this.presenter = presenter;
        this.chatChannelDataAccess = chatChannelDataAccess;
        this.userDataAccess = userDataAccess;
        this.sessionManager = sessionManager;
        this.channelCreator = channelCreator;
    }

    // TODO: Add chat with Sendbird API as well
    @Override
    public void CreateChannel(AddChatChannelInputData request) throws SQLException {
        final User currentUser = sessionManager.getMainUser();
        final User toAdd = userDataAccess.getUserFromID(request.getReceiverID());
        final List<Integer> contactIDs = currentUser.getContactIds();

        System.out.println("Test");

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
            String chatUrl = channelCreator.SendbirdChannelCreator(dotenv.get("MSG_TOKEN"), request.getChatName(),
                    request.getSenderID(), request.getReceiverID());
            System.out.println("chatUrl: " + chatUrl);
            DirectChatChannel newChannel = new DirectChatChannel(request.getChatName(),
                    currentUser, toAdd, chatUrl, new ArrayList<>());

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
