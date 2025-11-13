package interface_adapter.add_chat_channel;

import entity.User;
import use_case.add_chat_channel.AddChatChannelInputBoundary;
import use_case.add_chat_channel.AddChatChannelInteractor;
import use_case.add_chat_channel.CreateChatRequestModel;

import java.sql.SQLException;
import java.util.Hashtable;
import java.util.List;

public class AddChatChannelController {
    private final AddChatChannelInputBoundary addChatChannelInteractor;

    public AddChatChannelController(AddChatChannelInteractor addChatChannelInteractor) {
        this.addChatChannelInteractor = addChatChannelInteractor;
    }

    public void CreateChannel(User contactUser, String chatName) throws SQLException {

        CreateChatRequestModel request = new CreateChatRequestModel(chatName, contactUser);
        addChatChannelInteractor.CreateChannel(request);

    }

}
