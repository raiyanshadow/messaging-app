package data_access;

import entity.Message;
import java.sql.SQLException;
import java.util.List;

public interface MessageDataAccessObject {

    /**
     * Retrieves all messages belonging to a specific chat channel, typically ordered by time sent.
     * @param channelUrl The unique URL of the channel to query messages from.
     * @return A list of {@code Message} entities.
     * @throws SQLException If a database access error occurs.
     */
    List<Message<String>> getMessagesFromChannelURL(String channelUrl) throws SQLException;
}
