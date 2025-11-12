package use_case.add_chat_channel;

import SendBirdAPI.ChannelCreator;
import data_access.ChatChannelAccessObject;
import data_access.UserDataAccess;
import entity.DirectChatChannel;
import entity.User;
import session.Session;

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

        //create direct channel entity
        ChannelCreator channelCreator = new ChannelCreator("F0DD09FE-824B-4435-A8D0-8CDD577C4A4F");
        String channelURL = channelCreator.SendbirdChannelCreator("c08ac61bd997aecded9764a446044c2d98a1a45b",
                request.chatName, mainUser, request.contactUser);
        DirectChatChannel newChat = new DirectChatChannel(request.chatName, mainUser,
                request.contactUser, new ArrayList<>(), channelURL);

        //add channel entity to db
        chatChannelDataAccess.addChat(newChat);

        //call presenter to update view
        presenter.PresentChat(response);

    }
}
