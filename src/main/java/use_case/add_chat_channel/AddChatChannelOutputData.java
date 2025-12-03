package use_case.add_chat_channel;

import java.util.ArrayList;
import java.util.List;

/**
 * The output data of the chat channel use case.
 */
public class AddChatChannelOutputData {
    private String chatName;
    private String chatUrl;
    private Integer senderID;
    private Integer receiverID;
    private List<Integer> contactIds = new ArrayList<>();
    private boolean newChat;

    public AddChatChannelOutputData(String chatName, String chatUrl, Integer senderID, Integer receiverID,
                                    List<Integer> contactIds) {
        this.chatName = chatName;
        this.chatUrl = chatUrl;
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.contactIds = new ArrayList<>(contactIds);
        this.newChat = true;
    }

    public String getChatName() {
        return chatName;
    }

    public String getChatUrl() {
        return chatUrl;
    }

    public Integer getSenderID() {
        return senderID;
    }

    public Integer getReceiverID() {
        return receiverID;
    }

    public List<Integer> getContactIds() {
        return contactIds;
    }

    public boolean isNewChat() {
        return newChat;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public void setChatUrl(String chatUrl) {
        this.chatUrl = chatUrl;
    }

    public void setSenderID(Integer senderID) {
        this.senderID = senderID;
    }

    public void setReceiverID(Integer receiverID) {
        this.receiverID = receiverID;
    }

    public void setContactIds(List<Integer> contactIds) {
        this.contactIds = contactIds;
    }

    public void setNewChat(boolean newChat) {
        this.newChat = newChat;
    }
}
