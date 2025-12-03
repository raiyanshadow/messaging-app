package use_case.update_chat_channel;

/**
 * Input data of the update chat channel use case.
 */
public class UpdateChatChannelInputData {
    private final String chatUrl;

    public UpdateChatChannelInputData(String chatUrl) {
        this.chatUrl = chatUrl;
    }

    public String getChatUrl() {
        return chatUrl;
    }
}
