package data.access;

import java.sql.SQLException;
import java.util.List;

import entity.AbstractMessage;

/**
 * Interface for accessing the text_message table in our database.
 */
public interface MessageDataAccessObject {

    /**
     * Retrieves all messages belonging to a specific chat channel, typically ordered by time sent.
     * @param channelUrl The unique URL of the channel to query messages from.
     * @return A list of {@code AbstractMessage} entities.
     * @throws SQLException If a database access error occurs.
     */
    List<AbstractMessage> getMessagesFromChannelUrl(String channelUrl) throws SQLException;
}
