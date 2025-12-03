package use_case.reply_message;

import java.sql.Timestamp;

/**
 * Output data for reply message use case.
 */
public class ReplyMessageOutputData {
    private Long parentMessageId;
    private Long childMessageId;
    private String content;
    private String channelUrl;
    private final Integer senderId;
    private final Integer receiverId;
    private Timestamp timestamp;

    public ReplyMessageOutputData(Long parentMessageId,
                                  Long childMessageId,
                                  String content, String channelUrl,
                                  Integer senderId, Integer receiverId,
                                  Timestamp timestamp) {
        this.content = content;
        this.channelUrl = channelUrl;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.timestamp = timestamp;
    }

    public Long getParentMessageId() {
        return parentMessageId;
    }

    public void setParentMessageId(Long parentMessageId) {
        this.parentMessageId = parentMessageId;
    }

    public Long getChildMessageId() {
        return childMessageId;
    }

    public void setChildMessageId(Long childMessageId) {
        this.childMessageId = childMessageId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
