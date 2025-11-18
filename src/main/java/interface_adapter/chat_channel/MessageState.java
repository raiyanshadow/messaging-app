package interface_adapter.chat_channel;

import java.sql.Timestamp;

public class MessageState {
    private String content;
    private Timestamp timestamp;
    private String channelURL;
    private int senderID;
    private int receiverID;
    private String senderName;
    private String error;
    private boolean shouldGoHome;

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getChannelURL() {
        return channelURL;
    }
    public void setChannelURL(String channelURL) {
        this.channelURL = channelURL;
    }

    public int getSenderID() {
        return senderID;
    }
    public void setSenderID(int senderID) {
        this.senderID = senderID;
    }

    public int getReceiverID() {
        return receiverID;
    }
    public void setReceiverID(int receiverID) {
        this.receiverID = receiverID;
    }
    public String getSenderName() {
        return senderName;
    }
    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }
    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setShouldGoHome(boolean b) {
        this.shouldGoHome = b;
    }
}
