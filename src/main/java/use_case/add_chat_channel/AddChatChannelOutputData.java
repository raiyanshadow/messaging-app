package use_case.add_chat_channel;

import java.util.List;
import java.util.ArrayList;

public class AddChatChannelOutputData {
    private String chatName;
    private Integer chatID;
    private Integer senderID;
    private Integer receiverID;
    private List<Integer> contactIDs = new ArrayList<>();

    public AddChatChannelOutputData(String chatName, Integer chatID, Integer senderID, Integer receiverID,
                                    List<Integer> contactIDs) {
        this.chatName = chatName;
        this.chatID = chatID;
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.contactIDs = new ArrayList<>(contactIDs);
    }

    public String getChatName() { return chatName; }
    public Integer getChatID() { return chatID; }
    public Integer getSenderID() { return senderID; }
    public Integer getReceiverID() { return receiverID; }
    public List<Integer> getContactIDs() { return contactIDs; }

    public void setChatName(String chatName) { this.chatName = chatName; }
    public void setChatID(Integer chatID) { this.chatID = chatID; }
    public void setSenderID(Integer senderID) { this.senderID = senderID; }
    public void setReceiverID(Integer receiverID) { this.receiverID = receiverID; }
    public void setContactIDs(List<Integer> contactIDs) { this.contactIDs = contactIDs; }
}
