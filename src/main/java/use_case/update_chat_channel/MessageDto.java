package use_case.update_chat_channel;

import java.sql.Timestamp;

public class MessageDto {
    private final String channelUrl;
    private final Integer senderId;
    private final Integer receiverId;
    private final Timestamp timestamp;
    private final String content;

    public MessageDto(String channelUrl, Integer senderId, Integer receiverID,
                      Timestamp timestamp, String content) {
        this.channelUrl = channelUrl;
        this.senderId = senderId;
        this.receiverId = receiverID;
        this.timestamp = timestamp;
        this.content = content;
    }

    public String getChannelUrl() {
        return channelUrl;
    }

    public Integer getSenderID() {
        return senderId;
    }

    public Integer getReceiverID() {
        return receiverId;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public String getContent() {
        return content;
    }
}
