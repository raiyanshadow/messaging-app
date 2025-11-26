package entity;

import java.sql.Timestamp;

public class MessageFactory {
    public static TextMessage createTextMessage(Long messageID, Long parentMessageId, String channelURL, Integer senderId,
                                                Integer receiverId, String status, Timestamp timestamp, String content) {
        if (senderId == null || receiverId == null) {
            throw new IllegalArgumentException("sender or receiver is null");
        }
        if (senderId == receiverId) {
            throw new IllegalArgumentException("User cannot add themselves as receiver");
        }
        if (content == null || content.isEmpty()) {
            throw new IllegalArgumentException("content is null or empty");
        }
        return new TextMessage(messageID, parentMessageId, channelURL, senderId, receiverId,
                status, timestamp, content);
    }

    public static TextMessage createTextMessage(Long messageID, String channelURL, Integer senderId,
                                                Integer receiverId, String status, Timestamp timestamp, String content) {
        return MessageFactory.createTextMessage(messageID, null, channelURL, senderId,
                receiverId, status, timestamp, content);
    }

    public static TextMessage createEmptyMessage() {
        return new TextMessage(-1L, "", 0, 0, "",
                new Timestamp(0), "");
    }
}
