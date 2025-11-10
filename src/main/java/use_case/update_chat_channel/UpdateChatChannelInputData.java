package use_case.update_chat_channel;

public class UpdateChatChannelInputData {
    private Integer chatID;

    public UpdateChatChannelInputData(Integer chatID) {
        this.chatID = chatID;
    }
    public Integer getChatID() {
        return chatID;
    }
}
