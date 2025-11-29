package entity;

import java.sql.Timestamp;

public class MessageFactory {

    /**
     * Returns a new Text Message Entity.
     * @return the Text Message Entity
     * @throws IllegalArgumentException if any input is unusuable/invalid
     */
    public static TextMessage createTextMessage(Long messageID, Long parentMessageId,
                                                String channelURL, Integer senderId,
                                                Integer receiverId, String status,
                                                Timestamp timestamp, String content) {
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

    /**
     * Returns a new Text Message Entity using Message Factory.
     * @return the Text Message Entity
     */
    public static TextMessage createTextMessage(Long messageID, String channelUrl, Integer senderId,
                                                Integer receiverId, String status,
                                                Timestamp timestamp, String content) {
        return MessageFactory.createTextMessage(messageID, null, channelUrl, senderId,
                receiverId, status, timestamp, content);
    }

    /**
     * Returns a new Text Message Entity that is empty.
     * @return the Text Message Entity
     */
    public static TextMessage createEmptyMessage() {
        return new TextMessage(-1L, "", 0, 0, "",
                new Timestamp(0), "");
    }
}
