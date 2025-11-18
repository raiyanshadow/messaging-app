package use_case.send_message;

import entity.Message;

import java.util.ArrayList;
import java.util.List;

public class SendMessageOutputData {
    private final List<Message> messages;
    private List<Integer> messageIDs;
    private Integer senderID;
    private Integer receiverID;
    private String channelUrl;

    public SendMessageOutputData(List<Message> messages,
                                 List<Integer> messageIDs,
                                 Integer senderID,
                                 Integer receiverID,
                                 String channelUrl) {
        this.messages = new ArrayList<>(messages);
        this.messageIDs = messageIDs;
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.channelUrl = channelUrl;
    }

    public List<Message> getMessages() { return messages; }
    public List<Integer> getMessageIDs() { return messageIDs; }
    public Integer getSenderID() { return senderID; }
    public Integer getReceiverID() { return receiverID; }
    public String getChannelUrl() { return channelUrl; }

    public void setMessageID(List<Integer> messageIDs) { this.messageIDs = messageIDs; }
    public void setSenderID(Integer senderID) { this.senderID = senderID; }
    public void setReceiverID(Integer receiverID) { this.receiverID = receiverID; }
    public void setChannelUrl(String channelUrl) { this.channelUrl = channelUrl; }

}
