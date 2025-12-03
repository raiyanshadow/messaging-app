package interface_adapter.add_chat_channel;

import java.sql.SQLException;

import use_case.add_chat_channel.AddChatChannelInputBoundary;
import use_case.add_chat_channel.AddChatChannelInputData;

/**
 * Controller for adding chat channel use case.
 */
public class AddChatChannelController {
    private final AddChatChannelInputBoundary addChatChannelInteractor;

    public AddChatChannelController(AddChatChannelInputBoundary addChatChannelInteractor) {
        this.addChatChannelInteractor = addChatChannelInteractor;
    }

    /**
     * Calling interactor to create a new chat channel.
     * @param username username of the person to add to the chat channel.
     * @param chatName name of the chat channel.
     * @param senderID id of the sender.
     * @param receiverID id of the receiver.
     * @throws SQLException whenever we can't access the API/database or modify the database
     */
    public void createChannel(String username, String chatName,
                              Integer senderID, Integer receiverID) throws SQLException {

        final AddChatChannelInputData request = new AddChatChannelInputData(username, chatName,
                senderID, receiverID);
        addChatChannelInteractor.createChannel(request);
    }

}
