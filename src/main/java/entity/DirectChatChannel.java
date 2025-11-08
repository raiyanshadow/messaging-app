package entity;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.sql.Timestamp;
import java.util.Date;

public class DirectChatChannel {
    String chatID;
    String chatName;
    List<User> users;
    List<Message> messages;
    Hashtable<Message,Timestamp > messagesSent;

    public DirectChatChannel(String chatID, String chatName) {
        this.chatID = chatID;
        this.chatName = chatName;
        this.users = new ArrayList<>();
        this.messages = new ArrayList<>();
        this.messagesSent = new Hashtable<>();
    }


}
