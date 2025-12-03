package entity;

import java.sql.Timestamp;

/**
 * An abstract class that can represent a generic type of message.
 * @param <T> The type of message represented by a generic type.
 */
public abstract class AbstractMessage<T> {
    private final Long messageID;
    private Long parentMessageID;
    private String channelUrl;
    private Integer senderId;
    private Integer receiverId;
    private String status;
    private final Timestamp timestamp;

    protected AbstractMessage(Long messageID, Long parentMessageID, String channelUrl, Integer senderId,
                              Integer receiverId, String status, Timestamp timestamp) {
        this.messageID = messageID;
        this.parentMessageID = parentMessageID;
        this.channelUrl = channelUrl;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.status = status;
        this.timestamp = timestamp;
    }

    protected AbstractMessage(Long messageID, String channelUrl, Integer senderId, Integer receiverId,
                              String status, Timestamp timestamp) {
        this.messageID = messageID;
        this.parentMessageID = null;
        this.channelUrl = channelUrl;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.status = status;
        this.timestamp = timestamp;
    }

    public Long getMessageID() {
        return messageID;
    }

    public Long getParentMessageID() {

        return parentMessageID;
    }

    public String getChannelUrl() {
        return channelUrl;
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

    /**
     * Get content of the message.
     * @return content of the message.
     */
    public abstract T getContent();

    /**
     * Setter for the content of the message.
     * @param content the content to set the content field of this message to.
     */
    public abstract void setContent(T content);

    /**
     * Returns what the generic type is.
     * @return Returns the generic type of the message.
     */
    public abstract String getType();

}
