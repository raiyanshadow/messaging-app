package use_case.reply_message;

public class ReplyMessageInputData {
    private Long parentMessageId;
    private String message;
    private String channelUrl;
    private Integer senderId;
    private Integer receiverId;

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
    public void setParentMessageId(Long parentMessageId) {
        this.parentMessageId = parentMessageId;
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
    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
    }
    public Integer getReceiverId() {
        return receiverId;
    }
    public void setReceiverId(Integer receiverId) {
        this.receiverId = receiverId;
    }
}
