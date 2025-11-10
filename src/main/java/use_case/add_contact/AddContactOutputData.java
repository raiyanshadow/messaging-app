package use_case.add_contact;

import entity.User;

// after pressing button to add contact show that <user> has been added as a contact
public class AddContactOutputData {
    private final User user;
    private final String username;

    public AddContactOutputData(User user, String username) {
        this.user = user;
        this.username = username;
    }

    User getUser() {
        return user;
    }

    String getUsername() {
        return username;
    }

}
