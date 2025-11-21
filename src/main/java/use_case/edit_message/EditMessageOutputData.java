package use_case.edit_message;

import data_access.UserDataAccessObject;

import java.sql.Timestamp;

public class EditMessageOutputData {
    private Long messageId;
    private String channelUrl;
    private String content;
    private Integer senderId;
    private Integer receiverId;
    private Timestamp oldTimestamp;
    private Timestamp newTimestamp;

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
    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
    }
    public Integer getReceiverId() {
        return receiverId;
    }
    public void setReceiverId(Integer receiverId) {
        this.receiverId = receiverId;
    }
    public Timestamp getOldTimestamp() {
        return oldTimestamp;
    }
    public void setOldTimestamp(Timestamp oldTimestamp) {
        this.oldTimestamp = oldTimestamp;
    }
    public Timestamp getNewTimestamp() {
        return newTimestamp;
    }
    public void setNewTimestamp(Timestamp newTimestamp) {
        this.newTimestamp = newTimestamp;
    }
}
