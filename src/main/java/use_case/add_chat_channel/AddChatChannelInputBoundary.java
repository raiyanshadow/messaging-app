package use_case.add_chat_channel;

import java.sql.SQLException;

/**
 * Interface for the interactor of the add chat channel use case.
 */
public interface AddChatChannelInputBoundary {
    /**
     * Creates a chat channel using the SendBird API and saves it to the PostgreSQL database.
     * @param addChatChannelInputData the input data for adding the chat channel.
     * @throws SQLException whenever we can't modify the database or call the API.
     */
    void createChannel(AddChatChannelInputData addChatChannelInputData) throws SQLException;
}
