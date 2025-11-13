package use_case.add_chat_channel;

public class CreateChatResponeModel {
    public String chatName;
    public boolean newChat;
    CreateChatResponeModel(String chatName) {
        this.chatName = chatName;
        this.newChat = true;
    }

    public void setNewChat(boolean newChat) {
        this.newChat = newChat;
    }
}
