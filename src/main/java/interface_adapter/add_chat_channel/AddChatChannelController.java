package interface_adapter.add_chat_channel;

import session.SessionManager;
import use_case.add_chat_channel.AddChatChannelInputBoundary;
import use_case.add_chat_channel.AddChatChannelInputData;
import use_case.add_chat_channel.AddChatChannelInteractor;

import java.sql.SQLException;

public class AddChatChannelController {
    private final AddChatChannelInputBoundary addChatChannelInteractor;

    public AddChatChannelController(AddChatChannelInteractor addChatChannelInteractor) {
        this.addChatChannelInteractor = addChatChannelInteractor;
    }

    public void CreateChannel(String username, String chatName,
                              Integer senderID, Integer receiverID, Integer chatID) throws SQLException {

        AddChatChannelInputData request = new AddChatChannelInputData(username, chatName, senderID, receiverID, chatID);
        addChatChannelInteractor.CreateChannel(request);

    }

}
