package use_case.edit_message;

public class EditMessageInputData {
    private final String newMessage;
    private String channelUrl;
    private Long messageId;

    public EditMessageInputData(String newMessage, String channelUrl, Long messageId) {
        this.newMessage = newMessage;
        this.channelUrl = channelUrl;
        this.messageId = messageId;
    }

    public String getNewMessage() {
        return newMessage;
    }
    public String getChannelUrl() {
        return channelUrl;
    }
    public void setChannelUrl(String channelUrl) {
        this.channelUrl = channelUrl;
    }
    public Long getMessageId() {
        return messageId;
    }
    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }
}
