package use_case.add_contact;

import entity.User;

// to add contact user must input another user
public class AddContactInputData {

    // private final String username;
    private final User user;

    public AddContactInputData(User user) {
        // this.username = username;
        this.user = user;
    }

    /*
    String getUsername() {
        return username;
    }
     */

    User getUser() {
        return user;
    }
}
