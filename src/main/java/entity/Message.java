package entity;

import java.sql.Timestamp;

public abstract class Message<T> {
    protected final Long messageID;
    protected Long parentMessageID = null;
    protected String channelURL;
    protected Integer senderId;
    protected Integer receiverId;
    protected String status;
    protected final Timestamp timestamp;

    protected Message(Long messageID, Long parentMessageID, String channelURL, Integer senderId, Integer receiverId,
                      String status, Timestamp timestamp) {
        this.messageID = messageID;
        this.parentMessageID = parentMessageID;
        this.channelURL = channelURL;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.status = status;
        this.timestamp = timestamp;
    }

    protected Message(Long messageID, String channelURL, Integer senderId, Integer receiverId,
                      String status, Timestamp timestamp) {
        this.messageID = messageID;
        this.channelURL = channelURL;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.status = status;
        this.timestamp = timestamp;
    }

    public Long getMessageID() {
        return messageID;
    }

    public Long getParentMessageID() { return parentMessageID; }

    public String getChannelURL() {
        return channelURL;
    }

    public Integer getSenderId() {
        return senderId;
    }

    public Integer getReceiverId() {
        return receiverId;
    }

    public String getStatus() {
        return status;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public abstract T getContent();
    public abstract void setContent(T content);

    public abstract String getType();

}
