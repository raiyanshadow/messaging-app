package interface_adapter.add_chat_channel;

import entity.User;

import java.util.List;

public class ChatChannelState {
    private String chatName;

    public ChatChannelState(String chatName) {
        this.chatName = chatName;
    }

    public ChatChannelState(){};

    public String getChatName() {
        return chatName;
    }
    public void setChatName(String chatName) {}
}
