package entity;

import java.sql.Timestamp;
import java.util.*;

public class User {

    private int userID;
    private String preferredLanguage;
    private String username;
    private String password;
    private List<Contact> contacts;
    private List<String> userChats;
    private List<String> friendRequests;
    private Timestamp createdAt;
//    private String loginToken;
//    private String encryptionKey;

    public User(int userID, String username, String password, String preferredLanguage) {
        this.userChats = new ArrayList<String>();
        this.userID = userID;
        if ("".equals(username)) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if ("".equals(password)) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        this.username = username;
        this.password = password;
        this.preferredLanguage = preferredLanguage; // preferred language will default to English
        this.contacts = new ArrayList<>();
        this.friendRequests = new ArrayList<>();
    }

    public void addContact(User user) {
        Contact to_add = new Contact(this, user);
        if (!contacts.contains(to_add)) {
            contacts.add(to_add);
        }
    }
    public void removeContact(User user) {
        Contact to_remove = new Contact(this, user);
        contacts.remove(to_remove);
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getPreferredLanguage() {
        return preferredLanguage;
    }
    public void setPreferredLanguage(String preferredLanguage) {
        this.preferredLanguage = preferredLanguage;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setUserChats(List<String> userChats) {this.userChats = userChats;}
    public List<Contact> getContacts() { return contacts; }
    public List<Integer> getContactIDs() {
        List<Integer> contactIDs = new ArrayList<>();
        for (Contact contact : contacts) {
            contactIDs.add(contact.getContact().getUserID());
        }
        return contactIDs;
    }
    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }
    public void addChat(String url){
        userChats.add(url);
    }
    public List<String> getUserChats(){
        return userChats;
    }
    public void removeChat(String url){
        userChats.remove(url);
    }
    public List<String> returnChats(){
        return this.userChats;
    }
    public List<String> getFriendRequests(){ return this.friendRequests; }
    public void setFriendRequests(List<String> friendRequests) { this.friendRequests = friendRequests; }

}
