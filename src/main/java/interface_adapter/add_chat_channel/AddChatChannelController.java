package interface_adapter.add_chat_channel;

import use_case.add_chat_channel.AddChatChannelInputBoundary;
import use_case.add_chat_channel.AddChatChannelInteractor;
import use_case.add_chat_channel.CreateChatRequestModel;

import java.util.Hashtable;
import java.util.List;

public class AddChatChannelController {
    private final AddChatChannelInputBoundary addChatChannelInteractor;

    public AddChatChannelController(AddChatChannelInteractor addChatChannelInteractor) {
        this.addChatChannelInteractor = addChatChannelInteractor;
    }

    public void CreateChannel(String username, Integer userID, String chatName, Integer chatID){

        CreateChatRequestModel request = new CreateChatRequestModel(username, userID, chatName, chatID);
        addChatChannelInteractor.CreateChannel(request);

    }

}
