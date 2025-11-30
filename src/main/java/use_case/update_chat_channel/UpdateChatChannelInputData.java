package use_case.update_chat_channel;

public class UpdateChatChannelInputData {
    private final String chatURL;

    public UpdateChatChannelInputData(String chatURL) {
        this.chatURL = chatURL;
    }
    public String getChatURL() {
        return chatURL;
    }
}
