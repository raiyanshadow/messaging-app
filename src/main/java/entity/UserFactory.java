package entity;

import java.security.MessageDigest;

public class UserFactory {

    public User create(String username, String password, String preferredLanguage) {
        if (preferredLanguage == null || preferredLanguage.isEmpty()) {
            preferredLanguage = "English"; // default language
        }

        // Hash password
        String hashedPassword = hashPassword(password);

        // userID = 0 temporarily; database will assign the real ID
        return new User(0, username, hashedPassword, preferredLanguage);
    }

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(password.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
}
