package entity;

import java.util.ArrayList;
import java.util.List;

/**
 * A user entity for our application.
 */
public class User {

    private int userID;
    private String preferredLanguage;
    private String username;
    private String password;
    private List<Contact> contacts;
    private List<String> userChats;
    private List<String> friendRequests;

    public User(int userID, String username, String password, String preferredLanguage) {
        this.userChats = new ArrayList<>();
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.preferredLanguage = preferredLanguage;
        this.contacts = new ArrayList<>();
        this.friendRequests = new ArrayList<>();
    }

    /**
     * Adds the inputed User to the contacts of the current User.
     * @param user The user to establish a contact with.
     */
    public void addContact(User user) {
        final Contact toAdd = new Contact(this, user);
        if (!contacts.contains(toAdd)) {
            contacts.add(toAdd);
        }
    }

    /**
     * Removes the inputed User form the contacs of the current user.
     * @param user the user to remove an established contact with.
     */
    public void removeContact(User user) {
        final Contact toRemove = new Contact(this, user);
        contacts.remove(toRemove);
    }

    /**
     * Returns a List of ids that represent the contacts of the user.
     * @return contactIds
     */
    public List<Integer> getContactIds() {
        final List<Integer> contactIds = new ArrayList<>();
        for (Contact contact : contacts) {
            contactIds.add(contact.getContact().getUserID());
        }
        return contactIds;
    }

    /**
     * Removes Url from User chats.
     * @param url the chats to remove for a user given the url of the chat channel.
     */
    public void removeChat(String url) {
        userChats.remove(url);
    }

    /**
     * Adds Url to User chats.
     * @param url adds a chat url to the list of chat urls for this user.
     */
    public void addChat(String url) {
        userChats.add(url);
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

    public void setUserChats(List<String> userChats) {
        this.userChats = userChats;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public List<String> getUserChats() {
        return userChats;
    }

    public List<String> getFriendRequests() {
        return this.friendRequests;
    }

    public void setFriendRequests(List<String> friendRequests) {
        this.friendRequests = friendRequests;
    }
}
