package use_case.update_chat_channel;

import java.sql.Timestamp;

public class MessageDTO {
    private final String channelURL;
    private final Integer senderID;
    private final Integer receiverID;
    private final Timestamp timestamp;
    private final String content;

    public MessageDTO(String channelURL, Integer senderID, Integer receiverID,
                      Timestamp timestamp, String content) {
        this.channelURL = channelURL;
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.timestamp = timestamp;
        this.content = content;
    }

    public String getChannelURL() {
        return channelURL;
    }
    public Integer getSenderID() {
        return senderID;
    }
    public Integer getReceiverID() {
        return receiverID;
    }
    public Timestamp getTimestamp() {
        return timestamp;
    }
    public String getContent() {
        return content;
    }
}
