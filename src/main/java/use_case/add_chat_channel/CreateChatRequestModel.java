package use_case.add_chat_channel;

import entity.User;

import java.util.Hashtable;
import java.util.List;

public class CreateChatRequestModel {
    String username;
    Integer userID;;
    String chatName;
    Integer chatID;

    public CreateChatRequestModel(String username, Integer userID, String chatName, Integer chatID) {
        this.username = username;
        this.userID = userID;
        this.chatName = chatName;
        this.chatID = chatID;
    }
}
