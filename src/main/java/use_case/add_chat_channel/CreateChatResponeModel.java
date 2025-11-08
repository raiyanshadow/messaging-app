package use_case.add_chat_channel;

public class CreateChatResponeModel {
    String chatName;
    Integer chatID;

    CreateChatResponeModel(String chatName, Integer chatID) {
        this.chatName = chatName;
        this.chatID = chatID;
    }
}
