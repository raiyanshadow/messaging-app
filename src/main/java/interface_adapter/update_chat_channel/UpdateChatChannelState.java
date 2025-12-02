package interface_adapter.update_chat_channel;

import java.util.List;

import interface_adapter.chat_channel.MessageViewModel;

public class UpdateChatChannelState {
    private String chatChannelName;
    private List<MessageViewModel> messages;
    private Integer user1ID;
    private Integer user2ID;
    private String user1Name;
    private String user2Name;
    private String chatUrl;
    private String error;

    public String getChatChannelName() {
        return chatChannelName;
    }

    public void setChatChannelName(String chatChannelName) {
        this.chatChannelName = chatChannelName;
    }

    public Integer getUser1ID() {
        return user1ID;
    }

    public void setUser1ID(Integer user1ID) {
        this.user1ID = user1ID;
    }

    public Integer getUser2ID() {
        return user2ID;
    }

    public void setUser2ID(Integer user2ID) {
        this.user2ID = user2ID;
    }

    public String getUser1Name() {
        return user1Name;
    }

    public void setUser1Name(String user1Name) {
        this.user1Name = user1Name;
    }

    public String getUser2Name() {
        return user2Name;
    }

    public void setUser2Name(String user2Name) {
        this.user2Name = user2Name;
    }

    public List<MessageViewModel> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageViewModel> messages) {
        this.messages = messages;
    }

    public String getChatUrl() {
        return chatUrl;
    }

    public void setChatUrl(String chatUrl) {
        this.chatUrl = chatUrl;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
