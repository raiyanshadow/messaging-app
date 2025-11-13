package use_case.add_chat_channel;

import java.util.List;
import java.util.ArrayList;

public class AddChatChannelOutputData {
    private String chatName;
    private String chatUrl;
    private Integer senderID;
    private Integer receiverID;
    private List<Integer> contactIDs = new ArrayList<>();
    private boolean newChat;

    public AddChatChannelOutputData(String chatName, String chatUrl, Integer senderID, Integer receiverID,
                                    List<Integer> contactIDs) {
        this.chatName = chatName;
        this.chatUrl = chatUrl;
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.contactIDs = new ArrayList<>(contactIDs);
        this.newChat = true;
    }

    public String getChatName() { return chatName; }
    public String getChatUrl() { return chatUrl; }
    public Integer getSenderID() { return senderID; }
    public Integer getReceiverID() { return receiverID; }
    public List<Integer> getContactIDs() { return contactIDs; }

    public void setChatName(String chatName) { this.chatName = chatName; }
    public void setChatUrl(String chatUrl) { this.chatUrl = chatUrl; }
    public void setSenderID(Integer senderID) { this.senderID = senderID; }
    public void setReceiverID(Integer receiverID) { this.receiverID = receiverID; }
    public void setContactIDs(List<Integer> contactIDs) { this.contactIDs = contactIDs; }
    public void setNewChat(boolean newChat) { this.newChat = newChat; }
}
