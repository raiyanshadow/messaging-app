package entity;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class MessageFactory {
    public static TextMessage createTextMessage(int messageID, String channelURL, User sender,
                                                User receiver, String status, Timestamp timestamp, String content) {
        if (sender == null || receiver == null) {
            throw new IllegalArgumentException("sender or receiver is null");
        }
        if (sender.getUserID() == receiver.getUserID()) {
            throw new IllegalArgumentException("User cannot add themselves as receiver");
        }
        if (content == null || content.isEmpty()) {
            throw new IllegalArgumentException("content is null or empty");
        }
        return new TextMessage(messageID, channelURL, sender, receiver, "pending", timestamp, content);
    }

    public static TextMessage createEmptyMessage() {
        return new TextMessage(-1, "", new User(-1, "", "", ""), new User(-1, "", "", ""), "", new Timestamp(0), "");
    }
}
