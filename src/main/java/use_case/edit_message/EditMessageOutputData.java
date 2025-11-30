package use_case.edit_message;

import java.sql.Timestamp;

public class EditMessageOutputData {
    private Long messageId;
    private String channelUrl;
    private String content;
    private final Integer senderId;
    private final Integer receiverId;
    private final Timestamp oldTimestamp;
    private final Timestamp newTimestamp;

    public EditMessageOutputData(Long messageId,
                                 String channelUrl,
                                 String content,
                                 Integer senderId,
                                 Integer receiverId,
                                 Timestamp oldTimestamp,
                                 Timestamp newTimestamp) {
        this.messageId = messageId;
        this.channelUrl = channelUrl;
        this.content = content;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.oldTimestamp = oldTimestamp;
        this.newTimestamp = newTimestamp;
    }

    public Long getMessageId() {
        return messageId;
    }
    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }
    public String getChannelUrl() {
        return channelUrl;
    }
    public void setChannelUrl(String channelUrl) {
        this.channelUrl = channelUrl;
    }
    public String getNewContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public Integer getSenderId() {
        return senderId;
    }
    public Integer getReceiverId() {
        return receiverId;
    }
    public Timestamp getOldTimestamp() {
        return oldTimestamp;
    }
    public Timestamp getNewTimestamp() {
        return newTimestamp;
    }
}
