package use_case.send_message;

import java.sql.Timestamp;

/**
 * Output data of the send message use case.
 */
public class SendMessageOutputData {
    private Long messageId;
    private final Integer senderID;
    private final Integer receiverID;
    private String channelUrl;
    private String content;
    private Timestamp timestamp;

    public SendMessageOutputData(Long messageId,
                                 Integer senderID,
                                 Integer receiverID,
                                 String channelUrl,
                                 String content,
                                 Timestamp timestamp) {
        this.messageId = messageId;
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.channelUrl = channelUrl;
        this.content = content;
        this.timestamp = timestamp;
    }

    public Long getMessageId() {
        return messageId;
    }

    public Integer getSenderID() {
        return senderID;
    }

    public Integer getReceiverID() {
        return receiverID;
    }

    public String getChannelUrl() {
        return channelUrl;
    }

    public String getContent() {
        return content;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setChannelUrl(String channelUrl) {
        this.channelUrl = channelUrl;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
