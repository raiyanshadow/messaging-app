package interface_adapter.chat_channel;

import entity.Message;
import entity.User;
import java.util.ArrayList;
import java.util.List;

public class ChatChannelState {
    private List<Message> messages = new ArrayList<>();
    private List<Integer> messageIDs = new ArrayList<>();
    private String channelUrl;
    private User sender;
    private User receiver;
    private String error;
    private String chatName;
    private boolean shouldGoHome = false;

    public List<Message> getMessages() { return messages; }
    public List<Integer> getMessageIDs() { return messageIDs; }
    public String getChannelUrl() { return channelUrl; }
    public User getSender() { return sender; }
    public User getReceiver() { return receiver; }
    public String getError() { return error; }
    public boolean getShouldGoHome() { return shouldGoHome; }
    public String  getChatName() { return chatName; }

    public void setMessages(List<Message> messages) { this.messages = new ArrayList<>(messages); }
    public void setMessageIDs(List<Integer> messageIDs) { this.messageIDs = new ArrayList<>(messageIDs); }
    public void setChannelUrl(String channelUrl) { this.channelUrl = channelUrl; }
    public void setSender(User sender) { this.sender = sender; }
    public void setReceiver(User receiver) { this.receiver = receiver; }
    public void setError(String error) { this.error = error; }
    public void setShouldGoHome(boolean shouldGoHome) { this.shouldGoHome = shouldGoHome; }
    public void setChatName(String chatName) { this.chatName = chatName; }

}
