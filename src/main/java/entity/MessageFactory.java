package entity;

import java.sql.Timestamp;

/**
 * A factory for creating types of messages.
 */
public class MessageFactory {

    /**
     * Returns a new Text AbstractMessage Entity.
     * @param messageId id of the message
     * @param parentMessageId id of the parent message in case of reply
     * @param channelUrl the url of the chat channel the message is in
     * @param senderId id of the sending user
     * @param receiverId id of the receiving user
     * @param status status of the message delivery
     * @param timestamp timestamp of when the message was sent
     * @param content the text content of the message
     * @return the Text AbstractMessage Entity
     * @throws IllegalArgumentException if any input is unusable/invalid
     */
    public static TextMessage createTextMessage(Long messageId, Long parentMessageId,
                                                String channelUrl, Integer senderId,
                                                Integer receiverId, String status,
                                                Timestamp timestamp, String content) {
        if (senderId == null || receiverId == null) {
            throw new IllegalArgumentException("sender or receiver is null");
        }
        if (senderId.equals(receiverId)) {
            throw new IllegalArgumentException("User cannot add themselves as receiver");
        }
        if (content == null || content.isEmpty()) {
            throw new IllegalArgumentException("content is null or empty");
        }
        return new TextMessage(messageId, parentMessageId, channelUrl, senderId, receiverId,
                status, timestamp, content);
    }

    /**
     * Returns a new Text AbstractMessage Entity using AbstractMessage Factory.
     * @param messageId id of the message
     * @param channelUrl the url of the chat channel the message is in
     * @param senderId id of the sending user
     * @param receiverId id of the receiving user
     * @param status status of the message delivery
     * @param timestamp timestamp of when the message was sent
     * @param content the text content of the message
     * @return the Text AbstractMessage Entity
     */
    public static TextMessage createTextMessage(Long messageId, String channelUrl, Integer senderId,
                                                Integer receiverId, String status,
                                                Timestamp timestamp, String content) {
        return MessageFactory.createTextMessage(messageId, null, channelUrl, senderId,
                receiverId, status, timestamp, content);
    }

    /**
     * Returns a new Text AbstractMessage Entity that is empty.
     * @return the Text AbstractMessage Entity
     */
    public static TextMessage createEmptyMessage() {
        return new TextMessage(-1L, "", 0, 0, "",
                new Timestamp(0), "");
    }
}
