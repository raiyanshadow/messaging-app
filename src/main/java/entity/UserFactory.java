package entity;

public class UserFactory {

    private int nextUserID = 1;

    public User create(String username, String password, String preferredLanguage) {
        if (preferredLanguage == null || preferredLanguage.isEmpty()) {
            preferredLanguage = "English"; // default language
        }
        User user = new User(nextUserID, username, password, preferredLanguage);
        nextUserID++;
        return user;
    }

}
