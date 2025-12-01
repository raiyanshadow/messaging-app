package interface_adapter.chat_channel;

import java.sql.Timestamp;

public class MessageState {
    private String content;
    private Timestamp timestamp;
    private Timestamp editedTimestamp;
    private String channelURL;
    private Integer senderID;
    private Integer receiverID;
    private String senderName;
    private String error;
    private boolean replied;
    private boolean edited;

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
    public Timestamp getEditedTimestamp() { return timestamp; }
    public void setEditedTimestamp(Timestamp editedTimestamp) { this.editedTimestamp = editedTimestamp; }
    public String getChannelURL() {
        return channelURL;
    }
    public void setChannelURL(String channelURL) {
        this.channelURL = channelURL;
    }
    public Integer getSenderID() {
        return senderID;
    }
    public void setSenderID(Integer senderID) {
        this.senderID = senderID;
    }
    public Integer getReceiverID() {
        return receiverID;
    }
    public void setReceiverID(Integer receiverID) {
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
    public void setReplied(boolean b) { this.replied = b;}
    public boolean getReplied() { return this.replied; }
    public void setEdited(boolean b) { this.edited = b;}
    public boolean getEdited() { return this.edited; }
}
