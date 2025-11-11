package use_case.add_chat_channel;

import data_access.ChatChannelAccessObject;
import data_access.UserDataAccess;
import entity.DirectChatChannel;
import entity.User;
import io.github.cdimascio.dotenv.Dotenv;
import session.Session;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.List;

public class AddChatChannelInteractor implements AddChatChannelInputBoundary {
    AddChatChannelOutputBoundary presenter;
    List<Object> save_chat;
    ChatChannelAccessObject chatChannelDataAccess;
    UserDataAccess userDataAccess;
    Session sessionManager;

    AddChatChannelInteractor(AddChatChannelOutputBoundary presenter, ChatChannelAccessObject chatChannelAccessObject,
                             UserDataAccess userDataAccess, Session sessionManager) {
        this.presenter = presenter;
        this.chatChannelDataAccess = chatChannelDataAccess;
        this.userDataAccess = userDataAccess;
        this.sessionManager = sessionManager;
    }

    @Override
    public void CreateChannel(CreateChatRequestModel request) {
        CreateChatResponeModel response = new CreateChatResponeModel(request.chatName, request.chatID);
        DirectChatChannel newChat = new DirectChatChannel(request.chatID, request.chatName, charurl, sessionManager.)

        presenter.PresentChat(response);

    }
}
