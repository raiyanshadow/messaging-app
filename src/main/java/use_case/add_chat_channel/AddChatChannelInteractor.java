package use_case.add_chat_channel;

import SendBirdAPI.ChannelCreator;
import data_access.ChatChannelAccessObject;
import data_access.DBConnectionFactory;
import data_access.UserDataAccess;
import entity.DirectChatChannel;
import entity.User;
import session.Session;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddChatChannelInteractor implements AddChatChannelInputBoundary {
    AddChatChannelOutputBoundary presenter;
    List<Object> save_chat;
    ChatChannelAccessObject chatChannelDataAccess;
    UserDataAccess userDataAccess;
    Session sessionManager;
    User mainUser;

    AddChatChannelInteractor(AddChatChannelOutputBoundary presenter, ChatChannelAccessObject chatChannelAccessObject,
                             UserDataAccess userDataAccess, Session sessionManager) {
        this.presenter = presenter;
        this.userDataAccess = userDataAccess;
        this.sessionManager = sessionManager;
        this.mainUser = sessionManager.getMainUser();
        this.chatChannelDataAccess = chatChannelAccessObject;
    }

    @Override
    public void CreateChannel(CreateChatRequestModel request) throws SQLException {
        //create response model for any new info needed for view
        CreateChatResponeModel response = new CreateChatResponeModel(request.chatName);
        //check if chat is already made with contact user
        boolean newChat1 = true;
        List<String> chatURLs = mainUser.returnChats();
        String contactname = request.contactUser.getUsername();
        List<String> URLs = chatChannelDataAccess.getChatURLsByUserId(request.contactUser.getUserID());
        for (String url : chatURLs) {
            for (String chatURL : URLs) {
                if (url.equals(chatURL)) {
                    newChat1 = false;
                }
            }
        }

        if (newChat1) {
            //create direct channel entity
            response.setNewChat(true);
            ChannelCreator channelCreator = new ChannelCreator("F0DD09FE-824B-4435-A8D0-8CDD577C4A4F");
            String channelURL = channelCreator.SendbirdChannelCreator("c08ac61bd997aecded9764a446044c2d98a1a45b",
                    request.chatName, mainUser, request.contactUser);

            DirectChatChannel newChat = new DirectChatChannel(request.chatName, mainUser,
                    request.contactUser, new ArrayList<>());

            newChat.setChatURL(channelURL);
            mainUser.addChat(channelURL);
            //add channel entity to db
            chatChannelDataAccess.addChat(newChat);

        }
        else {
            response.setNewChat(false);
        }

        //call presenter to update view
        presenter.PresentChat(response);

    }
}
