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
    private Message lastMessage;
    private String content;

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
        this.lastMessage = messages.get(messages.size() - 1);
        this.content = (String) lastMessage.getContent();
    }

    public List<Message> getMessage() { return messages; }
    public Message lastMessage() {
        return messages.get(messages.size()-1);
    }
    public List<Integer> getMessageID() { return messageIDs; }
    public Integer getSenderID() { return senderID; }
    public Integer getReceiverID() { return receiverID; }
    public String getChannelUrl() { return channelUrl; }
    public String getContent() { return content; }

    public void setContent(String content) {
        this.content = content;
    }
    public void setMessageID(List<Integer> messageIDs) { this.messageIDs = messageIDs; }
    public void setSenderID(Integer senderID) { this.senderID = senderID; }
    public void setReceiverID(Integer receiverID) { this.receiverID = receiverID; }
    public void setChannelUrl(String channelUrl) { this.channelUrl = channelUrl; }

}
