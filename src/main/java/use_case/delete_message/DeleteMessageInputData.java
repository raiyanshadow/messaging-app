package use_case.delete_message;

/**
 * Input data of the delete message use case.
 */
public class DeleteMessageInputData {
    private Long messageId;
    private String channelUrl;

    public DeleteMessageInputData(Long messageId, String channelUrl) {
        this.messageId = messageId;
        this.channelUrl = channelUrl;
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
}
