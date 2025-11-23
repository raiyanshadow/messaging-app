package use_case.baseUI;

import data_access.ChatChannelDataAccessObject;
import data_access.UserDataAccessObject;
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
    Session sessionManager;

    public BaseUIInteractor(BaseUIOutputBoundary presenter, ChatChannelDataAccessObject chatChannelDataAccess,
                            UserDataAccessObject userDataAccess, Session sessionManager){
        this.presenter = presenter;
        this.chatChannelDataAccess = chatChannelDataAccess;
        this.userDataAccess = userDataAccess;
        this.sessionManager = sessionManager;
    }

    @Override
    public void GetUserChats(BaseUIInputData request) throws SQLException {

        User mainUser = sessionManager.getMainUser();
        List<String> chatURLs = mainUser.getUserChats();
        List<String> chatnames = new ArrayList<>();
        List<DirectChatChannel> chatEntities = new ArrayList<>();
        for (String chatURL : chatURLs){
            DirectChatChannel chat = chatChannelDataAccess.getDirectChatChannelByURL(chatURL);
            chatnames.add(chat.getChatName());
            chatEntities.add(chat);
        }
        BaseUIOutputData response = new BaseUIOutputData(chatnames, chatEntities);
        presenter.DisplayUI(response);
    }

    @Override
    public void displayAddChat(BaseUIInputData request) {
        BaseUIOutputData response = new BaseUIOutputData();
        presenter.DisplayAddChat(response);
    }

    @Override
    public void switchToFriendRequestView() {
        BaseUIOutputData response = new BaseUIOutputData();
        presenter.DisplayFriends(response);
    }
}