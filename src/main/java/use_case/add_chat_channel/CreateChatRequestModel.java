package use_case.add_chat_channel;

import entity.User;

import java.util.Hashtable;
import java.util.List;

public class CreateChatRequestModel {
    String chatName;
    User contactUser;


    public CreateChatRequestModel(String chatName, User contactUser ) {
        this.chatName = chatName;
        this.contactUser = contactUser;
    }
}
