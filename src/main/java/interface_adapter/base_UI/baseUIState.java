package interface_adapter.base_UI;

import entity.DirectChatChannel;

import java.util.ArrayList;
import java.util.List;

public class baseUIState {
    List<DirectChatChannel> chatEntities = new ArrayList<>();
    private List<String> chatnames = new ArrayList<>();
    private String errorMessage = null;
    public baseUIState() {
    };

    public void setChatnames(List<String> chatnames) {
        this.chatnames = chatnames;
    }

    public List<String> getChatnames() {
        return chatnames;
    }

    public void setChatEntities(List<DirectChatChannel> chatEntities) {
        this.chatEntities = chatEntities    ;
    }

    public List<DirectChatChannel> getChatEntities() {
        return chatEntities;
    }

    public void setErrorMessage(String s) {
        this.errorMessage = s;
    }
    public String getErrorMessage(){
        return this.errorMessage;
    }
}