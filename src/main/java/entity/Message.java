package entity;

import java.sql.Timestamp;

public abstract class Message<T> {
    protected final int messageID;
    protected final String channelURL;
    protected final User sender;
    protected final User receiver;
    protected String status;
    protected final Timestamp timestamp;

    protected Message(int messageID, String channelURL, User sender, User receiver, String status, Timestamp timestamp) {
        this.messageID = messageID;
        this.channelURL = channelURL;
        this.sender = sender;
        this.receiver = receiver;
        this.status = status;
        this.timestamp = timestamp;
    }

    public int getMessageID() {
        return messageID;
    }

    public String getChannelURL() {
        return channelURL;
    }

    public User getSender() {
        return sender;
    }

    public User getReceiver() {
        return receiver;
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
