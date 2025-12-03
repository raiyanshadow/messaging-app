package use_case.add_chat_channel;

/**
 * The input data for the add chat channel use case.
 */
public class AddChatChannelInputData {
    private String username;
    private Integer senderID;
    private Integer receiverID;
    private String chatName;

    public AddChatChannelInputData(String username, String chatName,
                                   Integer senderID, Integer receiverID) {
        this.username = username;
        this.chatName = chatName;
        this.senderID = senderID;
        this.receiverID = receiverID;
    }

    public String getUsername() {
        return username;
    }

    public String getChatName() {
        return chatName;
    }

    public Integer getSenderID() {
        return senderID;
    }

    public Integer getReceiverID() {
        return receiverID;
    }
}
