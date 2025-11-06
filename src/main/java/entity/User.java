package entity;

import java.util.*;

public class User {

    private List<User> contacts;
    private String preferredLanguage;
    private String username;
    private String password;
//    private String loginToken;
//    private String encryptionKey;

    public User(String username, String password, String preferredLanguage) {
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
    }

    public void addContact(User user) {
        this.contacts.add(user);
    }
    public void removeContact(User user) {
        this.contacts.remove(user);
    }
    public void getPreferredLanguage() {
        this.preferredLanguage = preferredLanguage;
    }
    public void setPreferredLanguage(String preferredLanguage) {
        this.preferredLanguage = preferredLanguage;
    }
    public void getUsername() {
        this.username = username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void getPassword() {
        this.password = password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
