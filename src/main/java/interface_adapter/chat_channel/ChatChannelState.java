package interface_adapter.chat_channel;

import java.util.ArrayList;
import java.util.List;

/**
 * State for the associated use cases of chat channel.
 */
public class ChatChannelState {
    private List<String> messages = new ArrayList<>();
    private String channelUrl;
    private String error;
    private String chatName;

    public List<String> getMessages() {
        return messages;
    }

    public String getChannelUrl() {
        return channelUrl;
    }

    public String getError() {
        return error;
    }

    public String getChatName() {
        return chatName;
    }

    public void setMessages(List<String> messages) {
        this.messages = new ArrayList<>(messages);
    }

    public void setChannelUrl(String channelUrl) {
        this.channelUrl = channelUrl;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }
}
