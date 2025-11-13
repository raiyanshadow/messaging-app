package use_case.add_chat_channel;

import entity.User;

import java.util.Hashtable;
import java.util.List;

public class AddChatChannelInputData {
    private String username;
    private Integer senderID;
    private Integer receiverID;
    private String chatName;
    private Integer chatID;

    public AddChatChannelInputData(String username, String chatName,
                                   Integer senderID, Integer receiverID, Integer chatID) {
        this.username = username;
        this.chatName = chatName;
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.chatID = chatID;
    }

    public String getUsername() { return username; }
    public String getChatName() { return chatName; }
    public Integer getSenderID() { return senderID; }
    public Integer getReceiverID() { return receiverID; }
    public Integer getChatID() { return chatID; }
}
