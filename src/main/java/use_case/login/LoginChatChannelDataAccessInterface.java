package use_case.login;

import java.sql.SQLException;
import java.util.List;

import entity.DirectChatChannel;

/**
 * The chat channel data access interface containing methods used by the login use case's interactor.
 */
public interface LoginChatChannelDataAccessInterface {
    /**
     * Get all chat urls associated to the user id.
     * @param userId the user id to search by.
     * @return all chat urls that contain the user id.
     * @throws SQLException whenever it fails to read the database.
     */
    List<String> getChatUrlsByUserId(int userId) throws SQLException;

    /**
     * Retrieves a DirectChatChannel entity from the database based on its unique channel URL.
     * @param channelUrl The unique URL identifying the chat channel.
     * @return The populated DirectChatChannel entity, or an empty DirectChatChannel
     * @throws SQLException If a database access error occurs.
     */
    DirectChatChannel getDirectChatChannelByUrl(String channelUrl) throws SQLException;
}
