package use_case.reply_message;

/**
 * Input data for the reply message use case.
 */
public class ReplyMessageInputData {
    private final Long parentMessageId;
    private String message;
    private String channelUrl;
    private final Integer senderId;
    private final Integer receiverId;

    public ReplyMessageInputData(Long parentMessageId, String message, String channelUrl,
                                 Integer senderId, Integer receiverId) {
        this.parentMessageId = parentMessageId;
        this.message = message;
        this.channelUrl = channelUrl;
        this.senderId = senderId;
        this.receiverId = receiverId;
    }

    public Long getParentMessageId() {
        return parentMessageId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getChannelUrl() {
        return channelUrl;
    }

    public void setChannelUrl(String channelUrl) {
        this.channelUrl = channelUrl;
    }

    public Integer getSenderId() {
        return senderId;
    }

    public Integer getReceiverId() {
        return receiverId;
    }
}
