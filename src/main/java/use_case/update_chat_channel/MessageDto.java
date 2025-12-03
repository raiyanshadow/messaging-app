package use_case.update_chat_channel;

import java.sql.Timestamp;

/**
 * Message data transfer object used for testing purposes.
 */
public class MessageDto {
    private final String channelUrl;
    private final Integer senderID;
    private final Integer receiverID;
    private final Timestamp timestamp;
    private final String content;

    public MessageDto(String channelUrl, Integer senderID, Integer receiverID,
                      Timestamp timestamp, String content) {
        this.channelUrl = channelUrl;
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.timestamp = timestamp;
        this.content = content;
    }

    public String getChannelUrl() {
        return channelUrl;
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
