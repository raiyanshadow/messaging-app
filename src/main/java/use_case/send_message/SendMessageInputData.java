package use_case.send_message;

public class SendMessageInputData {
    private String message;
    private String channelUrl;
    private Integer senderID;
    private Integer receiverID;

    public SendMessageInputData(String message, String channelUrl, Integer senderID, Integer receiverID) {
        this.message = message;
        this.channelUrl = channelUrl;
        this.senderID = senderID;
        this.receiverID = receiverID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) { this.message = message; }

    public String getChannelUrl() { return channelUrl; }

    public void setChannelUrl(String channelUrl) { this.channelUrl = channelUrl; }

    public Integer getSenderID() { return senderID; }

    public void setSenderID(Integer senderID) { this.senderID = senderID; }

    public Integer getReceiverID() { return receiverID; }

    public void setReceiverID(Integer receiverID) { this.receiverID = receiverID; }
}
