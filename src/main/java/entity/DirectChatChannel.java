package entity;


public class DirectChatChannel {
    private Integer chatID;
    private String chatName;
    private Integer userID1;
    private Integer userID2;
    private String chatURL;

    public DirectChatChannel(Integer chatID, String chatName, String chatURL, Integer userID1, Integer userID2) {
        this.chatID = chatID;
        this.chatName = chatName;
        this.chatURL = chatURL;
        this.userID1 = userID1;
        this.userID2 = userID2;
    }

    public Integer getChatID() {
        return chatID;
    }
    public void setChatID(Integer chatID) {
        this.chatID = chatID;
    }
    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public Integer getUserID1() {
        return userID1;
    }

    public void setUserID1(Integer userID1) {
        this.userID1 = userID1;
    }

    public Integer getUserID2() {
        return userID2;
    }

    public void setUserID2(Integer userID2) {
        this.userID2 = userID2;
    }

    public String getChatURL() {
        return chatURL;
    }

    public void setChatURL(String chatURL) {
        this.chatURL = chatURL;
    }


}
