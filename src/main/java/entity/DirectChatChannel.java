package entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import data_access.ChatChannelAccessObject;
import data_access.DBChatChannelDataAccessObject;
import data_access.DBConnectionFactory;

import java.nio.channels.Channel;
import java.sql.Connection;
import java.sql.SQLException;

public class DirectChatChannel {
    private Integer chatID;
    private String chatName;
    private User user1;
    private User user2;
    private String chatURL;

    public DirectChatChannel(Integer chatID, String chatName, String chatURL, User user1, User user2) {
        this.chatID = chatID;
        this.chatName = chatName;
        this.chatURL = chatURL;
        this.user1 = user1;
        this.user2 = user2;
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

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public User getUser2() {
        return user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }

    public String getChatURL() {
        return chatURL;
    }

    public Channel CreateChannelURLBetween(User a, User b) throws SQLException {
        // call Sendbird REST API:
        String url = "jdbc:postgresql://db.egwwgffidqtyqxqiuocm.supabase.co:5432/postgres" + "/v3/group_channels";
        // build JSON payload with userIds = [a.getUserID(), b.getUserID()]
        // send POST, parse JSON, get channel_url
        
    }

    public void setChatURL(String chatURL) {
        this.chatURL = chatURL;
    }


}
