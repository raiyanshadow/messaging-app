package use_case.baseUI;

import entity.DirectChatChannel;

import java.util.List;

public class BaseUIOutputData  {
    private List<String> chatNames;
    private List<DirectChatChannel> directChatChannels;

    public BaseUIOutputData(List<String> chatNames, List<DirectChatChannel> directChatChannels) {
        this.chatNames = chatNames;
        this.directChatChannels = directChatChannels;
    }

    public List<String> getChatNames() {
        return chatNames;
    }
    public void setChatNames(List<String> chatNames) {
        this.chatNames = chatNames;
    }
    public List<DirectChatChannel> getDirectChatChannels() {
        return directChatChannels;
    }
    public void setDirectChatChannels(List<DirectChatChannel> directChatChannels) {
        this.directChatChannels = directChatChannels;
    }
}
