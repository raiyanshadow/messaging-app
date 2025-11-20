package entity;

import java.sql.Timestamp;

public class TextMessage extends Message<String> {
    private String content;

    public TextMessage(Long messageID, Long parentMessageId, String channelURL,
                       User sender, User receiver, String status, Timestamp timestamp, String content) {
        super(messageID, parentMessageId, channelURL, sender, receiver, status, timestamp);
        this.content = content;
    }

    public TextMessage(Long messageID, String channelURL,
                       User sender, User receiver, String status, Timestamp timestamp, String content) {
        super(messageID, channelURL, sender, receiver, status, timestamp);
        this.content = content;
    }

    @Override
    public String getContent() {
        return this.content;
    }

    @Override
    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String getType() {
        return "text";
    }
}
