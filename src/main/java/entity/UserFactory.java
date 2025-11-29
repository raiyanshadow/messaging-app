package entity;

import java.security.MessageDigest;

public class UserFactory {

    /**
     * Returns a User entity based on inputed valued.
     * @return User
     */
    public User create(String username, String password, String preferredLanguage) {
        boolean preferred = true;
        final String defaultpreferred = "English";

        if (preferredLanguage == null || preferredLanguage.isEmpty()) {
            preferred = false;
        }

        // Hash password
        final String hashedPassword = hashPassword(password);

        // userID = 0 temporarily; database will assign the real ID
        if (preferred) {
            return new User(0, username, hashedPassword, preferredLanguage);
        }

        else {
            return new User(0, username, hashedPassword, defaultpreferred);
        }

    }

    /**
     * Hashes the input password string using the SHA-256 algorithm.
     * @return The 64-character hexadecimal string representation of the SHA-256 hash.
     * @throws RuntimeException If an error occurs during the hashing process (e.g.,
     */
    private String hashPassword(String password) {
        try {
            final MessageDigest digest = MessageDigest.getInstance("SHA-256");
            final byte[] hashBytes = digest.digest(password.getBytes("UTF-8"));
            final StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
}
