package use_case.add_chat_channel;

import java.util.Hashtable;
import java.util.List;

public class AddChatChannelInteractor implements AddChatChannelInputBoundary {
    AddChatChannelOutputBoundary presenter;
    List<Object> save_chat;

    AddChatChannelInteractor(AddChatChannelOutputBoundary presenter) {
        this.presenter = presenter;
    }

    @Override
    public void CreateChannel(CreateChatRequestModel request) {
        CreateChatResponeModel response = new CreateChatResponeModel(request.chatName, request.chatID);
//        TRYING TO FIND HOW TO STORE CHANNEL INFORMATION SO I NEED A DATABASE OR SOMETHING

        presenter.PresentChat(response);

    }
}
