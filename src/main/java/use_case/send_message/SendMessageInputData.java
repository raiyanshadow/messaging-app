package use_case.send_message;

public class SendMessageInputData {
    private String message;
    private String channelUrl;
    private final Integer receiverID;

    public SendMessageInputData(String message, String channelUrl, Integer receiverID) {
        this.message = message;
        this.channelUrl = channelUrl;
        this.receiverID = receiverID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) { this.message = message; }

    public String getChannelUrl() { return channelUrl; }

    public void setChannelUrl(String channelUrl) { this.channelUrl = channelUrl; }

    public Integer getReceiverID() { return receiverID; }

}
