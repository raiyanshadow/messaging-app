package use_case.add_contact;

import entity.User;

// after pressing button to add contact show that <user> has been added as a contact
public class AddContactOutputData {
    private final User user;

    public AddContactOutputData(User user) {
        this.user = user;
    }

    User getUser() {
        return user;
    }

}
