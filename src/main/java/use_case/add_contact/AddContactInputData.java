package use_case.add_contact;

import entity.User;

// to add contact user must input another user
public class AddContactInputData {


    private final User user;
    private final String username;

    public AddContactInputData(User user, String username) {

        this.user = user;
        this.username = username;
    }


    String getUsername() {
        return username;
    }


    User getUser() {
        return user;
    }
}
